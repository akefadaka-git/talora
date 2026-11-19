package com.talora.app

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class TaloraDocument(
    val id: String,
    val title: String,
    val scriptType: String,
    val languageTag: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val metadata: Map<String, String> = emptyMap(),
    val revisionIds: List<String> = emptyList(),
    val assetIds: List<String> = emptyList()
)

@Serializable
data class TaloraRevision(
    val id: String,
    val documentId: String,
    val createdAt: Long,
    val title: String,
    val content: String,
    val changeNote: String? = null
)

@Serializable
data class TaloraAssetDescriptor(
    val id: String,
    val fileName: String,
    val mediaType: String,
    val archivePath: String,
    val sha256: String
)

@Serializable
data class TaloraManifest(
    val format: String,
    val schemaVersion: Int,
    val projectId: String,
    val createdAt: Long,
    val updatedAt: Long,
    val currentRevisionId: String?,
    val revisionIds: List<String>,
    val assets: List<TaloraAssetDescriptor>,
    val entrySha256: Map<String, String>
)

data class TaloraAsset(
    val id: String,
    val fileName: String,
    val mediaType: String,
    val bytes: ByteArray
)

data class TaloraPackage(
    val document: TaloraDocument,
    val revisions: List<TaloraRevision> = emptyList(),
    val assets: List<TaloraAsset> = emptyList(),
    val currentRevisionId: String? = null
)

/**
 * Versioned, self-contained .talora ZIP package.
 *
 * The manifest hashes every payload entry. Import is bounded and rejects
 * path traversal, duplicate entries, unknown payloads and integrity failures.
 */
object TaloraArchive {
    const val CURRENT_SCHEMA_VERSION = 1
    const val MAX_ARCHIVE_BYTES = 60 * 1024 * 1024
    const val MAX_TOTAL_UNCOMPRESSED_BYTES = 50 * 1024 * 1024
    const val MAX_ENTRY_BYTES = 20 * 1024 * 1024
    const val MAX_ENTRIES = 256

    private const val MANIFEST_PATH = "manifest.json"
    private const val DOCUMENT_PATH = "document.json"
    private const val FORMAT_NAME = "TALORA"
    private const val MAX_ID_LENGTH = 80
    private val idPattern = Regex("[A-Za-z0-9_-]{1,80}")
    private val shaPattern = Regex("[a-f0-9]{64}")
    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = false
        isLenient = false
        prettyPrint = false
    }

    fun encode(packageData: TaloraPackage): ByteArray {
        validatePackage(packageData)

        val payloads = linkedMapOf<String, ByteArray>()
        payloads[DOCUMENT_PATH] = json.encodeToString(packageData.document).toByteArray(Charsets.UTF_8)

        packageData.revisions.sortedBy { it.id }.forEach { revision ->
            payloads[revisionPath(revision.id)] =
                json.encodeToString(revision).toByteArray(Charsets.UTF_8)
        }

        val descriptors = packageData.assets.sortedBy { it.id }.map { asset ->
            val path = assetPath(asset.id)
            payloads[path] = asset.bytes
            TaloraAssetDescriptor(
                id = asset.id,
                fileName = asset.fileName,
                mediaType = asset.mediaType,
                archivePath = path,
                sha256 = sha256(asset.bytes)
            )
        }

        val totalBytes = payloads.values.sumOf { it.size.toLong() }
        require(totalBytes <= MAX_TOTAL_UNCOMPRESSED_BYTES) { "TALORA package exceeds the uncompressed size limit." }
        require(payloads.size + 1 <= MAX_ENTRIES) { "TALORA package contains too many entries." }

        val manifest = TaloraManifest(
            format = FORMAT_NAME,
            schemaVersion = CURRENT_SCHEMA_VERSION,
            projectId = packageData.document.id,
            createdAt = packageData.document.createdAt,
            updatedAt = packageData.document.updatedAt,
            currentRevisionId = packageData.currentRevisionId,
            revisionIds = packageData.revisions.map { it.id }.sorted(),
            assets = descriptors,
            entrySha256 = payloads.mapValues { (_, bytes) -> sha256(bytes) }
        )

        val output = ByteArrayOutputStream()
        ZipOutputStream(output).use { zip ->
            writeEntry(zip, MANIFEST_PATH, json.encodeToString(manifest).toByteArray(Charsets.UTF_8))
            payloads.forEach { (path, bytes) -> writeEntry(zip, path, bytes) }
        }
        val result = output.toByteArray()
        require(result.size <= MAX_ARCHIVE_BYTES) { "TALORA archive exceeds the compressed size limit." }
        return result
    }

    fun decode(archiveBytes: ByteArray): TaloraPackage {
        require(archiveBytes.isNotEmpty()) { "TALORA archive is empty." }
        require(archiveBytes.size <= MAX_ARCHIVE_BYTES) { "TALORA archive exceeds the compressed size limit." }

        val entries = linkedMapOf<String, ByteArray>()
        var totalBytes = 0L
        ZipInputStream(ByteArrayInputStream(archiveBytes)).use { zip ->
            while (true) {
                val entry = zip.nextEntry ?: break
                require(!entry.isDirectory) { "Directory entries are not supported." }
                require(isSafePath(entry.name)) { "Unsafe archive path." }
                require(entries.size < MAX_ENTRIES) { "TALORA package contains too many entries." }
                require(!entries.containsKey(entry.name)) { "Duplicate archive entry." }

                val output = ByteArrayOutputStream()
                val buffer = ByteArray(8192)
                var entryBytes = 0
                while (true) {
                    val count = zip.read(buffer)
                    if (count < 0) break
                    entryBytes += count
                    totalBytes += count
                    require(entryBytes <= MAX_ENTRY_BYTES) { "TALORA entry exceeds the size limit." }
                    require(totalBytes <= MAX_TOTAL_UNCOMPRESSED_BYTES) { "TALORA package exceeds the uncompressed size limit." }
                    output.write(buffer, 0, count)
                }
                entries[entry.name] = output.toByteArray()
                zip.closeEntry()
            }
        }

        val manifestBytes = entries[MANIFEST_PATH] ?: throw IllegalArgumentException("TALORA manifest is missing.")
        val manifest = decodeJson<TaloraManifest>(manifestBytes)
        require(manifest.format == FORMAT_NAME) { "Unsupported TALORA package format." }
        require(manifest.schemaVersion == CURRENT_SCHEMA_VERSION) { "Unsupported TALORA schema version: ${manifest.schemaVersion}." }
        require(isValidId(manifest.projectId)) { "Invalid TALORA project identifier." }
        require(manifest.entrySha256.keys == entries.keys - MANIFEST_PATH) {
            "TALORA manifest entry list does not match the archive."
        }

        manifest.entrySha256.forEach { (path, expectedHash) ->
            require(isSafePath(path)) { "Unsafe manifest entry path." }
            require(shaPattern.matches(expectedHash)) { "Invalid SHA-256 value in manifest." }
            val bytes = entries[path] ?: throw IllegalArgumentException("TALORA payload is missing: $path")
            require(sha256(bytes) == expectedHash) { "TALORA integrity check failed: $path" }
        }

        val documentBytes = entries[DOCUMENT_PATH] ?: throw IllegalArgumentException("TALORA document is missing.")
        val document = decodeJson<TaloraDocument>(documentBytes)
        require(document.id == manifest.projectId) { "TALORA project identity mismatch." }
        require(document.createdAt == manifest.createdAt && document.updatedAt == manifest.updatedAt) {
            "TALORA document timestamps do not match the manifest."
        }

        require(manifest.revisionIds.distinct().size == manifest.revisionIds.size) { "Duplicate revision identifiers." }
        val revisions = manifest.revisionIds.map { id ->
            require(isValidId(id)) { "Invalid revision identifier." }
            val bytes = entries[revisionPath(id)] ?: throw IllegalArgumentException("TALORA revision is missing: $id")
            decodeJson<TaloraRevision>(bytes).also { revision ->
                require(revision.id == id && revision.documentId == document.id) {
                    "TALORA revision identity mismatch."
                }
            }
        }
        require(document.revisionIds.toSet() == manifest.revisionIds.toSet()) {
            "TALORA document revision references do not match the manifest."
        }
        require(manifest.currentRevisionId == null || manifest.currentRevisionId in manifest.revisionIds) {
            "TALORA current revision reference is invalid."
        }

        require(manifest.assets.map { it.id }.distinct().size == manifest.assets.size) { "Duplicate asset identifiers." }
        val assets = manifest.assets.map { descriptor ->
            require(isValidId(descriptor.id)) { "Invalid asset identifier." }
            require(descriptor.fileName.isNotBlank() && descriptor.fileName.length <= 255) { "Invalid asset filename." }
            require('/' !in descriptor.fileName && '\\' !in descriptor.fileName) { "Invalid asset filename." }
            require(descriptor.archivePath == assetPath(descriptor.id)) { "Invalid asset archive path." }
            require(shaPattern.matches(descriptor.sha256)) { "Invalid asset SHA-256 value." }
            val bytes = entries[descriptor.archivePath] ?: throw IllegalArgumentException("TALORA asset is missing: ${descriptor.id}")
            require(sha256(bytes) == descriptor.sha256) { "TALORA asset integrity check failed: ${descriptor.id}" }
            TaloraAsset(descriptor.id, descriptor.fileName, descriptor.mediaType, bytes)
        }
        require(document.assetIds.toSet() == manifest.assets.map { it.id }.toSet()) {
            "TALORA document asset references do not match the manifest."
        }

        val expectedPaths = buildSet {
            add(DOCUMENT_PATH)
            manifest.revisionIds.forEach { add(revisionPath(it)) }
            manifest.assets.forEach { add(it.archivePath) }
        }
        require(manifest.entrySha256.keys == expectedPaths) { "TALORA package contains unsupported payload entries." }

        return TaloraPackage(document, revisions, assets, manifest.currentRevisionId)
    }

    private fun validatePackage(data: TaloraPackage) {
        require(isValidId(data.document.id)) { "Invalid TALORA project identifier." }
        require(data.document.title.length <= 500) { "TALORA title is too long." }
        require(data.document.scriptType.isNotBlank() && data.document.scriptType.length <= 80) { "Invalid script type." }
        require(data.document.languageTag.isNotBlank() && data.document.languageTag.length <= 64) { "Invalid language tag." }
        require(data.document.metadata.size <= 100) { "Too many metadata fields." }
        require(data.revisions.map { it.id }.distinct().size == data.revisions.size) { "Duplicate revision identifiers." }
        require(data.assets.map { it.id }.distinct().size == data.assets.size) { "Duplicate asset identifiers." }
        data.revisions.forEach {
            require(isValidId(it.id) && it.documentId == data.document.id) { "Invalid revision identity." }
            require(it.title.length <= 500) { "Revision title is too long." }
        }
        require(data.document.revisionIds.toSet() == data.revisions.map { it.id }.toSet()) {
            "Document revision references do not match the supplied revisions."
        }
        require(data.currentRevisionId == null || data.currentRevisionId in data.document.revisionIds) {
            "Current revision reference is invalid."
        }
        data.assets.forEach {
            require(isValidId(it.id)) { "Invalid asset identifier." }
            require(it.fileName.isNotBlank() && it.fileName.length <= 255) { "Invalid asset filename." }
            require('/' !in it.fileName && '\\' !in it.fileName) { "Invalid asset filename." }
            require(it.mediaType.isNotBlank() && it.mediaType.length <= 255) { "Invalid asset media type." }
            require(it.bytes.size <= MAX_ENTRY_BYTES) { "TALORA asset exceeds the entry size limit." }
        }
        require(data.document.assetIds.toSet() == data.assets.map { it.id }.toSet()) {
            "Document asset references do not match the supplied assets."
        }
    }

    private inline fun <reified T> decodeJson(bytes: ByteArray): T =
        try {
            json.decodeFromString(bytes.toString(Charsets.UTF_8))
        } catch (error: SerializationException) {
            throw IllegalArgumentException("Invalid TALORA JSON payload.", error)
        }

    private fun writeEntry(zip: ZipOutputStream, path: String, bytes: ByteArray) {
        zip.putNextEntry(ZipEntry(path))
        zip.write(bytes)
        zip.closeEntry()
    }

    private fun revisionPath(id: String) = "revisions/$id.json"
    private fun assetPath(id: String) = "assets/$id/content.bin"
    private fun isValidId(value: String) = value.length <= MAX_ID_LENGTH && idPattern.matches(value)

    private fun isSafePath(path: String): Boolean {
        if (path.isBlank() || path.startsWith('/') || '\\' in path || '\u0000' in path) return false
        return path.split('/').all { it.isNotBlank() && it != "." && it != ".." }
    }

    private fun sha256(bytes: ByteArray): String =
        MessageDigest.getInstance("SHA-256").digest(bytes).joinToString("") { "%02x".format(it) }
}
