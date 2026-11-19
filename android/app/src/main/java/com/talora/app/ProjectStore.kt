package com.talora.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class TaloraRevisionSummary(
    val id: String,
    val projectId: String,
    val createdAt: Long,
    val title: String,
    val changeNote: String?,
    val contentLength: Int = 0
)

data class TaloraRevisionDetail(
    val id: String,
    val projectId: String,
    val createdAt: Long,
    val title: String,
    val content: String,
    val changeNote: String?
)

data class TaloraAssetSummary(
    val id: String,
    val fileName: String,
    val mediaType: String,
    val sizeBytes: Int
)

data class TaloraProject(
    val id: String,
    val title: String,
    val body: String,
    val updatedAt: Long,
    val createdAt: Long,
    val scriptType: String,
    val languageTag: String,
    val metadata: Map<String, String>,
    val currentRevisionId: String?
)

class ProjectStore(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val json = Json { encodeDefaults = true; ignoreUnknownKeys = false }

    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE projects (
                id TEXT PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                body TEXT NOT NULL,
                updated_at INTEGER NOT NULL,
                created_at INTEGER NOT NULL,
                script_type TEXT NOT NULL,
                language_tag TEXT NOT NULL,
                metadata_json TEXT NOT NULL,
                current_revision_id TEXT
            )
            """.trimIndent()
        )
        createRelatedTables(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE projects ADD COLUMN created_at INTEGER NOT NULL DEFAULT 0")
            db.execSQL("UPDATE projects SET created_at = updated_at WHERE created_at = 0")
            db.execSQL("ALTER TABLE projects ADD COLUMN script_type TEXT NOT NULL DEFAULT 'screenplay'")
            db.execSQL("ALTER TABLE projects ADD COLUMN language_tag TEXT NOT NULL DEFAULT 'am'")
            db.execSQL("ALTER TABLE projects ADD COLUMN metadata_json TEXT NOT NULL DEFAULT '{}'")
            db.execSQL("ALTER TABLE projects ADD COLUMN current_revision_id TEXT")
            createRelatedTables(db)
        }
    }

    private fun createRelatedTables(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS project_revisions (
                id TEXT PRIMARY KEY NOT NULL,
                project_id TEXT NOT NULL,
                created_at INTEGER NOT NULL,
                title TEXT NOT NULL,
                content TEXT NOT NULL,
                change_note TEXT,
                FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS project_assets (
                id TEXT PRIMARY KEY NOT NULL,
                project_id TEXT NOT NULL,
                file_name TEXT NOT NULL,
                media_type TEXT NOT NULL,
                content BLOB NOT NULL,
                FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX IF NOT EXISTS index_project_revisions_project_id ON project_revisions(project_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_project_assets_project_id ON project_assets(project_id)")
    }

    fun listProjects(): List<TaloraProject> {
        val projects = mutableListOf<TaloraProject>()
        readableDatabase.rawQuery(
            """
            SELECT id, title, body, updated_at, created_at, script_type,
                   language_tag, metadata_json, current_revision_id
            FROM projects ORDER BY updated_at DESC
            """.trimIndent(),
            null
        ).use { cursor ->
            while (cursor.moveToNext()) projects += cursor.toProject()
        }
        return projects
    }

    /** Lightweight rows for the project switcher; script bodies are fetched only when opened. */
    fun listProjectSummaries(): List<TaloraProject> {
        val result = mutableListOf<TaloraProject>()
        readableDatabase.rawQuery(
            """
            SELECT id, title, '' AS body, updated_at, created_at, script_type,
                   language_tag, metadata_json, current_revision_id
            FROM projects ORDER BY updated_at DESC
            """.trimIndent(),
            null
        ).use { cursor ->
            while (cursor.moveToNext()) result += cursor.toProject()
        }
        return result
    }

    fun getProject(id: String): TaloraProject? {
        return readableDatabase.rawQuery(
            """
            SELECT id, title, body, updated_at, created_at, script_type,
                   language_tag, metadata_json, current_revision_id
            FROM projects WHERE id = ?
            """.trimIndent(),
            arrayOf(id)
        ).use { cursor -> if (cursor.moveToFirst()) cursor.toProject() else null }
    }

    fun createProject(initialTitle: String, scriptType: String = "screenplay"): TaloraProject {
        require(scriptType in setOf("screenplay", "stage_play")) { "Unsupported TALORA script type." }
        val now = System.currentTimeMillis()
        val project = TaloraProject(
            id = UUID.randomUUID().toString(),
            title = initialTitle,
            body = "",
            updatedAt = now,
            createdAt = now,
            scriptType = scriptType,
            languageTag = "am",
            metadata = emptyMap(),
            currentRevisionId = null
        )
        writableDatabase.insertOrThrow("projects", null, project.toContentValues())
        return project
    }

    fun updateProject(id: String, title: String, body: String) {
        val values = ContentValues().apply {
            put("title", title)
            put("body", body)
            put("updated_at", System.currentTimeMillis())
        }
        val changed = writableDatabase.update("projects", values, "id = ?", arrayOf(id))
        check(changed == 1) { "TALORA project no longer exists." }
    }

    fun updateProjectDetails(id: String, title: String, body: String, scriptType: String) {
        require(scriptType in setOf("screenplay", "stage_play")) { "Unsupported TALORA script type." }
        val values = ContentValues().apply {
            put("title", title)
            put("body", body)
            put("script_type", scriptType)
            put("updated_at", System.currentTimeMillis())
        }
        val changed = writableDatabase.update("projects", values, "id = ?", arrayOf(id))
        check(changed == 1) { "TALORA project no longer exists." }
    }

    fun createRevision(projectId: String, title: String, content: String, changeNote: String?): TaloraRevisionSummary {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val projectExists = db.rawQuery("SELECT 1 FROM projects WHERE id = ?", arrayOf(projectId)).use { it.moveToFirst() }
            check(projectExists) { "TALORA project no longer exists." }
            val revision = TaloraRevisionSummary(
                id = UUID.randomUUID().toString(),
                projectId = projectId,
                createdAt = System.currentTimeMillis(),
                title = title,
                changeNote = changeNote?.takeIf { it.isNotBlank() }
            )
            val values = ContentValues().apply {
                put("id", revision.id)
                put("project_id", projectId)
                put("created_at", revision.createdAt)
                put("title", title)
                put("content", content)
                put("change_note", revision.changeNote)
            }
            db.insertOrThrow("project_revisions", null, values)
            db.update(
                "projects",
                ContentValues().apply {
                    put("current_revision_id", revision.id)
                    put("updated_at", System.currentTimeMillis())
                },
                "id = ?",
                arrayOf(projectId)
            )
            db.setTransactionSuccessful()
            return revision
        } finally {
            db.endTransaction()
        }
    }

    fun listRevisions(projectId: String): List<TaloraRevisionSummary> {
        val result = mutableListOf<TaloraRevisionSummary>()
        readableDatabase.rawQuery(
            "SELECT id, project_id, created_at, title, change_note, length(content) FROM project_revisions WHERE project_id = ? ORDER BY created_at DESC",
            arrayOf(projectId)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result += TaloraRevisionSummary(
                    id = cursor.getString(0),
                    projectId = cursor.getString(1),
                    createdAt = cursor.getLong(2),
                    title = cursor.getString(3),
                    changeNote = if (cursor.isNull(4)) null else cursor.getString(4),
                    contentLength = cursor.getInt(5)
                )
            }
        }
        return result
    }

    fun getRevision(projectId: String, revisionId: String): TaloraRevisionDetail? {
        return readableDatabase.rawQuery(
            "SELECT id, project_id, created_at, title, content, change_note FROM project_revisions WHERE id = ? AND project_id = ?",
            arrayOf(revisionId, projectId)
        ).use { cursor ->
            if (!cursor.moveToFirst()) null else TaloraRevisionDetail(
                id = cursor.getString(0),
                projectId = cursor.getString(1),
                createdAt = cursor.getLong(2),
                title = cursor.getString(3),
                content = cursor.getString(4),
                changeNote = if (cursor.isNull(5)) null else cursor.getString(5)
            )
        }
    }

    fun updateRevisionNote(projectId: String, revisionId: String, note: String?) {
        val changed = writableDatabase.update(
            "project_revisions",
            ContentValues().apply { put("change_note", note?.takeIf { it.isNotBlank() }) },
            "id = ? AND project_id = ?",
            arrayOf(revisionId, projectId)
        )
        check(changed == 1) { "Revision does not belong to this project." }
    }

    fun restoreRevision(projectId: String, revisionId: String): TaloraProject {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val snapshot = db.rawQuery(
                "SELECT title, content FROM project_revisions WHERE id = ? AND project_id = ?",
                arrayOf(revisionId, projectId)
            ).use { cursor ->
                check(cursor.moveToFirst()) { "Revision does not belong to this project." }
                cursor.getString(0) to cursor.getString(1)
            }
            val values = ContentValues().apply {
                put("title", snapshot.first)
                put("body", snapshot.second)
                put("current_revision_id", revisionId)
                put("updated_at", System.currentTimeMillis())
            }
            check(db.update("projects", values, "id = ?", arrayOf(projectId)) == 1) {
                "TALORA project no longer exists."
            }
            val project = db.rawQuery(
                "SELECT id, title, body, updated_at, created_at, script_type, language_tag, metadata_json, current_revision_id FROM projects WHERE id = ?",
                arrayOf(projectId)
            ).use { cursor ->
                check(cursor.moveToFirst())
                cursor.toProject()
            }
            db.setTransactionSuccessful()
            return project
        } finally {
            db.endTransaction()
        }
    }

    fun listAssets(projectId: String): List<TaloraAssetSummary> {
        val result = mutableListOf<TaloraAssetSummary>()
        readableDatabase.rawQuery(
            "SELECT id, file_name, media_type, length(content) FROM project_assets WHERE project_id = ? ORDER BY file_name COLLATE NOCASE",
            arrayOf(projectId)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                result += TaloraAssetSummary(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3))
            }
        }
        return result
    }

    fun addAsset(projectId: String, fileName: String, mediaType: String, bytes: ByteArray): TaloraAssetSummary {
        require(fileName.isNotBlank() && fileName.length <= 255 && '/' !in fileName && fileName.none { it == 92.toChar() }) {
            "Invalid asset filename."
        }
        require(mediaType.isNotBlank() && mediaType.length <= 255) { "Invalid asset media type." }
        require(bytes.size <= TaloraArchive.MAX_ENTRY_BYTES) { "Asset exceeds the TALORA size limit." }
        val id = UUID.randomUUID().toString()
        val db = writableDatabase
        db.beginTransaction()
        try {
            val exists = db.rawQuery("SELECT 1 FROM projects WHERE id = ?", arrayOf(projectId)).use { it.moveToFirst() }
            check(exists) { "TALORA project no longer exists." }
            db.insertOrThrow(
                "project_assets",
                null,
                ContentValues().apply {
                    put("id", id)
                    put("project_id", projectId)
                    put("file_name", fileName)
                    put("media_type", mediaType)
                    put("content", bytes)
                }
            )
            db.update(
                "projects",
                ContentValues().apply { put("updated_at", System.currentTimeMillis()) },
                "id = ?",
                arrayOf(projectId)
            )
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        return TaloraAssetSummary(id, fileName, mediaType, bytes.size)
    }

    fun getAssetBytes(projectId: String, assetId: String): ByteArray {
        return readableDatabase.rawQuery(
            "SELECT content FROM project_assets WHERE id = ? AND project_id = ?",
            arrayOf(assetId, projectId)
        ).use { cursor ->
            check(cursor.moveToFirst()) { "Asset does not belong to this project." }
            cursor.getBlob(0)
        }
    }

    fun replaceAsset(
        projectId: String,
        assetId: String,
        fileName: String,
        mediaType: String,
        bytes: ByteArray
    ): TaloraAssetSummary {
        require(fileName.isNotBlank() && fileName.length <= 255 && '/' !in fileName && fileName.none { it == 92.toChar() }) {
            "Invalid asset filename."
        }
        require(mediaType.isNotBlank() && mediaType.length <= 255) { "Invalid asset media type." }
        require(bytes.size <= TaloraArchive.MAX_ENTRY_BYTES) { "Asset exceeds the TALORA size limit." }
        val changed = writableDatabase.update(
            "project_assets",
            ContentValues().apply {
                put("file_name", fileName)
                put("media_type", mediaType)
                put("content", bytes)
            },
            "id = ? AND project_id = ?",
            arrayOf(assetId, projectId)
        )
        check(changed == 1) { "Asset does not belong to this project." }
        return TaloraAssetSummary(assetId, fileName, mediaType, bytes.size)
    }

    fun removeAsset(projectId: String, assetId: String) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val removed = db.delete("project_assets", "id = ? AND project_id = ?", arrayOf(assetId, projectId))
            check(removed == 1) { "Asset does not belong to this project." }
            db.update(
                "projects",
                ContentValues().apply { put("updated_at", System.currentTimeMillis()) },
                "id = ?",
                arrayOf(projectId)
            )
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    /** Imports the complete package in one transaction, remapping IDs on collision. */
    fun importPackage(source: TaloraPackage): TaloraProject {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val sourceId = source.document.id
            val idExists = db.rawQuery("SELECT 1 FROM projects WHERE id = ?", arrayOf(sourceId)).use { it.moveToFirst() }
            val projectId = if (idExists) UUID.randomUUID().toString() else sourceId
            val revisionIdMap = source.revisions.associate { revision ->
                revision.id to uniqueId(db, "project_revisions", revision.id)
            }
            val assetIdMap = source.assets.associate { asset ->
                asset.id to uniqueId(db, "project_assets", asset.id)
            }
            val document = source.document.copy(
                id = projectId,
                revisionIds = source.document.revisionIds.map { revisionIdMap.getValue(it) },
                assetIds = source.document.assetIds.map { assetIdMap.getValue(it) }
            )
            val now = System.currentTimeMillis()
            val project = TaloraProject(
                id = projectId,
                title = document.title,
                body = document.content,
                updatedAt = document.updatedAt.takeIf { it > 0 } ?: now,
                createdAt = document.createdAt.takeIf { it > 0 } ?: now,
                scriptType = document.scriptType,
                languageTag = document.languageTag,
                metadata = document.metadata,
                currentRevisionId = source.currentRevisionId?.let { revisionIdMap.getValue(it) }
            )
            db.insertOrThrow("projects", null, project.toContentValues())
            source.revisions.forEach { revision ->
                val values = ContentValues().apply {
                    put("id", revisionIdMap.getValue(revision.id))
                    put("project_id", projectId)
                    put("created_at", revision.createdAt)
                    put("title", revision.title)
                    put("content", revision.content)
                    put("change_note", revision.changeNote)
                }
                db.insertOrThrow("project_revisions", null, values)
            }
            source.assets.forEach { asset ->
                val values = ContentValues().apply {
                    put("id", assetIdMap.getValue(asset.id))
                    put("project_id", projectId)
                    put("file_name", asset.fileName)
                    put("media_type", asset.mediaType)
                    put("content", asset.bytes)
                }
                db.insertOrThrow("project_assets", null, values)
            }
            db.setTransactionSuccessful()
            return project
        } finally {
            db.endTransaction()
        }
    }

    fun exportPackage(projectId: String): TaloraPackage {
        val db = readableDatabase
        val project = db.rawQuery(
            """
            SELECT id, title, body, updated_at, created_at, script_type,
                   language_tag, metadata_json, current_revision_id
            FROM projects WHERE id = ?
            """.trimIndent(),
            arrayOf(projectId)
        ).use { cursor ->
            check(cursor.moveToFirst()) { "TALORA project was not found." }
            cursor.toProject()
        }

        val revisions = mutableListOf<TaloraRevision>()
        db.rawQuery(
            "SELECT id, project_id, created_at, title, content, change_note FROM project_revisions WHERE project_id = ? ORDER BY created_at",
            arrayOf(projectId)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                revisions += TaloraRevision(
                    id = cursor.getString(0),
                    documentId = cursor.getString(1),
                    createdAt = cursor.getLong(2),
                    title = cursor.getString(3),
                    content = cursor.getString(4),
                    changeNote = if (cursor.isNull(5)) null else cursor.getString(5)
                )
            }
        }

        val assets = mutableListOf<TaloraAsset>()
        db.rawQuery(
            "SELECT id, file_name, media_type, content FROM project_assets WHERE project_id = ?",
            arrayOf(projectId)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                assets += TaloraAsset(
                    id = cursor.getString(0),
                    fileName = cursor.getString(1),
                    mediaType = cursor.getString(2),
                    bytes = cursor.getBlob(3)
                )
            }
        }

        val document = TaloraDocument(
            id = project.id,
            title = project.title,
            scriptType = project.scriptType,
            languageTag = project.languageTag,
            content = project.body,
            createdAt = project.createdAt,
            updatedAt = project.updatedAt,
            metadata = project.metadata,
            revisionIds = revisions.map { it.id },
            assetIds = assets.map { it.id }
        )
        return TaloraPackage(document, revisions, assets, project.currentRevisionId)
    }

    private fun uniqueId(db: SQLiteDatabase, table: String, requestedId: String): String {
        val exists = db.rawQuery("SELECT 1 FROM $table WHERE id = ?", arrayOf(requestedId)).use { it.moveToFirst() }
        return if (exists) UUID.randomUUID().toString() else requestedId
    }

    private fun android.database.Cursor.toProject() = TaloraProject(
        id = getString(0),
        title = getString(1),
        body = getString(2),
        updatedAt = getLong(3),
        createdAt = getLong(4),
        scriptType = getString(5),
        languageTag = getString(6),
        metadata = json.decodeFromString(getString(7)),
        currentRevisionId = if (isNull(8)) null else getString(8)
    )

    private fun TaloraProject.toContentValues() = ContentValues().apply {
        put("id", id)
        put("title", title)
        put("body", body)
        put("updated_at", updatedAt)
        put("created_at", createdAt)
        put("script_type", scriptType)
        put("language_tag", languageTag)
        put("metadata_json", json.encodeToString(metadata))
        put("current_revision_id", currentRevisionId)
    }
}

private const val DATABASE_NAME = "talora-local.db"
private const val DATABASE_VERSION = 2
