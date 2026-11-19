package com.talora.app

import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class TaloraArchiveTest {
    @Test
    fun roundTripPreservesEthiopicTextMetadataRevisionsAndAssets() {
        val source = samplePackage()
        val decoded = TaloraArchive.decode(TaloraArchive.encode(source))

        assertEquals(source.document, decoded.document)
        assertEquals(source.revisions, decoded.revisions)
        assertEquals(source.currentRevisionId, decoded.currentRevisionId)
        assertEquals(source.assets.map { it.id to it.fileName }, decoded.assets.map { it.id to it.fileName })
        assertArrayEquals(source.assets.single().bytes, decoded.assets.single().bytes)
    }

    @Test
    fun rejectsPayloadTampering() {
        val archive = TaloraArchive.encode(samplePackage())
        val entries = unzip(archive).toMutableMap()
        entries["document.json"] = entries.getValue("document.json")
            .toString(Charsets.UTF_8)
            .replace("ሰላም", "ሰላም!")
            .toByteArray(Charsets.UTF_8)

        assertThrows(IllegalArgumentException::class.java) {
            TaloraArchive.decode(zip(entries))
        }
    }

    @Test
    fun rejectsPathTraversalEntries() {
        val archive = zip(
            mapOf(
                "../outside.txt" to "unsafe".toByteArray(),
                "manifest.json" to "{}".toByteArray()
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            TaloraArchive.decode(archive)
        }
    }

    @Test
    fun rejectsUnlistedPayloadEntries() {
        val entries = unzip(TaloraArchive.encode(samplePackage())).toMutableMap()
        entries["extra.txt"] = "not listed in manifest".toByteArray()

        assertThrows(IllegalArgumentException::class.java) {
            TaloraArchive.decode(zip(entries))
        }
    }

    @Test
    fun rejectsMismatchedAssetReferencesBeforeWriting() {
        val source = samplePackage()
        val invalid = source.copy(document = source.document.copy(assetIds = emptyList()))

        assertThrows(IllegalArgumentException::class.java) {
            TaloraArchive.encode(invalid)
        }
    }

    @Test
    fun rejectsUnsupportedSchemaVersion() {
        val archive = zip(
            mapOf(
                "manifest.json" to """{"format":"TALORA","schemaVersion":99,"projectId":"project-001","createdAt":1,"updatedAt":2,"currentRevisionId":null,"revisionIds":[],"assets":[],"entrySha256":{}}""".toByteArray()
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            TaloraArchive.decode(archive)
        }
    }

    private fun samplePackage(): TaloraPackage {
        val document = TaloraDocument(
            id = "project-001",
            title = "የመጀመሪያ ታሪክ",
            scriptType = "screenplay",
            languageTag = "am-ET",
            content = "ሰላም ዓለም\nINT. ቤት - ሌሊት",
            createdAt = 1_700_000_000,
            updatedAt = 1_700_000_100,
            metadata = mapOf("author" to "Akefa", "calendar" to "EC"),
            revisionIds = listOf("revision-001"),
            assetIds = listOf("asset-001")
        )
        val revision = TaloraRevision(
            id = "revision-001",
            documentId = document.id,
            createdAt = 1_700_000_050,
            title = document.title,
            content = "ሰላም ዓለም"
        )
        val asset = TaloraAsset(
            id = "asset-001",
            fileName = "reference.png",
            mediaType = "image/png",
            bytes = byteArrayOf(0, 1, 2, 3, 4, 5)
        )
        return TaloraPackage(document, listOf(revision), listOf(asset), revision.id)
    }

    private fun unzip(bytes: ByteArray): Map<String, ByteArray> {
        val result = linkedMapOf<String, ByteArray>()
        java.util.zip.ZipInputStream(bytes.inputStream()).use { input ->
            while (true) {
                val entry = input.nextEntry ?: break
                result[entry.name] = input.readBytes()
                input.closeEntry()
            }
        }
        return result
    }

    private fun zip(entries: Map<String, ByteArray>): ByteArray {
        val output = ByteArrayOutputStream()
        ZipOutputStream(output).use { zip ->
            entries.forEach { (name, bytes) ->
                zip.putNextEntry(ZipEntry(name))
                zip.write(bytes)
                zip.closeEntry()
            }
        }
        return output.toByteArray()
    }
}
