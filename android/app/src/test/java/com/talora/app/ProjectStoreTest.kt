package com.talora.app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ProjectStoreTest {
    private lateinit var context: Context
    private lateinit var store: ProjectStore

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        context.deleteDatabase("talora-local.db")
        store = ProjectStore(context)
    }

    @After
    fun tearDown() {
        store.close()
        context.deleteDatabase("talora-local.db")
    }

    @Test
    fun revisionSnapshotsCanBeListedAndRestored() {
        val project = store.createProject("Draft")
        store.updateProject(project.id, "First title", "First draft")
        val revision = store.createRevision(project.id, "First title", "First draft", "Initial scene")

        store.updateProject(project.id, "Second title", "Changed scene")
        val restored = store.restoreRevision(project.id, revision.id)

        assertEquals("First title", restored.title)
        assertEquals("First draft", restored.body)
        assertEquals(revision.id, restored.currentRevisionId)
        assertEquals(revision.id, store.listRevisions(project.id).single().id)
        assertEquals("First draft".length, store.listRevisions(project.id).single().contentLength)
        store.updateRevisionNote(project.id, revision.id, "Reviewed")
        assertEquals("Reviewed", store.getRevision(project.id, revision.id)?.changeNote)
    }

    @Test
    fun assetsPersistAndAreIncludedInNativePackage() {
        val project = store.createProject("Asset project")
        val bytes = byteArrayOf(0, 1, 2, 3, -1)
        val asset = store.addAsset(project.id, "reference.bin", "application/octet-stream", bytes)

        assertEquals(asset.id, store.listAssets(project.id).single().id)
        val packageData = store.exportPackage(project.id)
        assertEquals(listOf(asset.id), packageData.document.assetIds)
        assertArrayEquals(bytes, packageData.assets.single().bytes)
        assertArrayEquals(bytes, store.getAssetBytes(project.id, asset.id))
        val replacement = byteArrayOf(9, 8, 7)
        store.replaceAsset(project.id, asset.id, "replacement.bin", "application/octet-stream", replacement)
        assertArrayEquals(replacement, store.getAssetBytes(project.id, asset.id))
        assertEquals("replacement.bin", store.listAssets(project.id).single().fileName)

        store.removeAsset(project.id, asset.id)
        assertTrue(store.listAssets(project.id).isEmpty())
    }

    @Test
    fun importingPackageRemapsCollidingProjectRevisionAndAssetIds() {
        val source = store.createProject("Portable")
        store.updateProject(source.id, "Portable", "Amharic: ሰላም")
        val revision = store.createRevision(source.id, "Portable", "Amharic: ሰላም", "Snapshot")
        store.addAsset(source.id, "photo.png", "image/png", byteArrayOf(7, 8, 9))
        val packageData = store.exportPackage(source.id)

        val imported = store.importPackage(packageData)
        val importedPackage = store.exportPackage(imported.id)

        assertNotEquals(source.id, imported.id)
        assertNotEquals(revision.id, importedPackage.revisions.single().id)
        assertNotEquals(packageData.assets.single().id, importedPackage.assets.single().id)
        assertEquals(imported.id, importedPackage.revisions.single().documentId)
        assertEquals(importedPackage.revisions.single().id, importedPackage.currentRevisionId)
        assertEquals("Amharic: ሰላም", importedPackage.document.content)
    }

    @Test
    fun upgradesVersionOneDatabaseWithoutLosingProjects() {
        store.close()
        context.deleteDatabase("talora-local.db")
        val dbFile = context.getDatabasePath("talora-local.db")
        dbFile.parentFile?.mkdirs()
        val legacy = SQLiteDatabase.openOrCreateDatabase(dbFile, null)
        legacy.execSQL(
            """
            CREATE TABLE projects (
                id TEXT PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                body TEXT NOT NULL,
                updated_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
        legacy.insertOrThrow(
            "projects",
            null,
            ContentValues().apply {
                put("id", "legacy-1")
                put("title", "Legacy draft")
                put("body", "Preserve me")
                put("updated_at", 1234L)
            }
        )
        legacy.version = 1
        legacy.close()

        store = ProjectStore(context)
        val upgraded = store.listProjects().single()

        assertEquals("legacy-1", upgraded.id)
        assertEquals("Legacy draft", upgraded.title)
        assertEquals("Preserve me", upgraded.body)
        assertEquals(1234L, upgraded.createdAt)
        assertEquals("screenplay", upgraded.scriptType)
        assertEquals("am", upgraded.languageTag)
        assertNull(upgraded.currentRevisionId)
    }
}
