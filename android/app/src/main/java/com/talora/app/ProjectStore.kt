package com.talora.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.UUID

data class TaloraProject(
    val id: String,
    val title: String,
    val body: String,
    val updatedAt: Long
)

class ProjectStore(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE projects (
                id TEXT PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                body TEXT NOT NULL,
                updated_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Schema migrations must be additive and versioned before production rollout.
    }

    fun listProjects(): List<TaloraProject> {
        val projects = mutableListOf<TaloraProject>()
        readableDatabase.rawQuery(
            "SELECT id, title, body, updated_at FROM projects ORDER BY updated_at DESC",
            null
        ).use { cursor ->
            while (cursor.moveToNext()) {
                projects += TaloraProject(
                    id = cursor.getString(0),
                    title = cursor.getString(1),
                    body = cursor.getString(2),
                    updatedAt = cursor.getLong(3)
                )
            }
        }
        return projects
    }

    fun createProject(initialTitle: String): TaloraProject {
        val project = TaloraProject(
            id = UUID.randomUUID().toString(),
            title = initialTitle,
            body = "",
            updatedAt = System.currentTimeMillis()
        )
        val values = ContentValues().apply {
            put("id", project.id)
            put("title", project.title)
            put("body", project.body)
            put("updated_at", project.updatedAt)
        }
        writableDatabase.insertOrThrow("projects", null, values)
        return project
    }

    fun updateProject(id: String, title: String, body: String) {
        val values = ContentValues().apply {
            put("title", title)
            put("body", body)
            put("updated_at", System.currentTimeMillis())
        }
        writableDatabase.update("projects", values, "id = ?", arrayOf(id))
    }
}

private const val DATABASE_NAME = "talora-local.db"
private const val DATABASE_VERSION = 1
