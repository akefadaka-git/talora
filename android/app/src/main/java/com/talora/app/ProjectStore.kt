package com.talora.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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

    fun createProject(initialTitle: String): TaloraProject {
        val now = System.currentTimeMillis()
        val project = TaloraProject(
            id = UUID.randomUUID().toString(),
            title = initialTitle,
            body = "",
            updatedAt = now,
            createdAt = now,
            scriptType = "screenplay",
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

    /** Imports the complete package in one transaction, remapping IDs on collision. */
    fun importPackage(source: TaloraPackage): TaloraProject {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val sourceId = source.document.id
            val idExists = db.rawQuery("SELECT 1 FROM projects WHERE id = ?", arrayOf(sourceId)).use { it.moveToFirst() }
            val projectId = if (idExists) UUID.randomUUID().toString() else sourceId
            val document = source.document.copy(id = projectId)
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
                currentRevisionId = source.currentRevisionId
            )
            db.insertOrThrow("projects", null, project.toContentValues())
            source.revisions.forEach { revision ->
                val values = ContentValues().apply {
                    put("id", revision.id)
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
                    put("id", asset.id)
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
