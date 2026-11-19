package com.talora.app

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    private val projectStore by lazy { ProjectStore(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TaloraHome(projectStore)
                }
            }
        }
    }
}

@Composable
private fun TaloraHome(store: ProjectStore) {
    val context = LocalContext.current
    val scope = androidx.compose.runtime.rememberCoroutineScope()
    var projects by remember { mutableStateOf(emptyList<TaloraProject>()) }
    var selectedId by remember { mutableStateOf<String?>(null) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var amharic by remember { mutableStateOf(true) }
    var status by remember { mutableStateOf("") }
    var busy by remember { mutableStateOf(false) }
    var pendingExport by remember { mutableStateOf<ByteArray?>(null) }
    var revisions by remember { mutableStateOf(emptyList<TaloraRevisionSummary>()) }
    var assets by remember { mutableStateOf(emptyList<TaloraAssetSummary>()) }
    var changeNote by remember { mutableStateOf("") }
    var scriptType by remember { mutableStateOf("screenplay") }
    var selectedRevisionA by remember { mutableStateOf<String?>(null) }
    var selectedRevisionB by remember { mutableStateOf<String?>(null) }
    var comparisonLines by remember { mutableStateOf(emptyList<RevisionDiffLine>()) }
    var comparisonTitle by remember { mutableStateOf("") }
    var showComparison by remember { mutableStateOf(false) }
    var editingRevisionId by remember { mutableStateOf<String?>(null) }
    var revisionNoteDraft by remember { mutableStateOf("") }
    var previewAssetName by remember { mutableStateOf("") }
    var previewAssetType by remember { mutableStateOf("") }
    var previewText by remember { mutableStateOf("") }
    var previewBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showAssetPreview by remember { mutableStateOf(false) }
    var assetReplaceTargetId by remember { mutableStateOf<String?>(null) }
    var pendingAssetExport by remember { mutableStateOf<Pair<String, ByteArray>?>(null) }

    val untitled = if (amharic) "ርዕስ የሌለው ፕሮጀክት" else "Untitled project"
    val createLabel = if (amharic) "አዲስ ጽሑፍ" else "New script"
    val projectsLabel = if (amharic) "ፕሮጀክቶቼ" else "My projects"
    val titleLabel = if (amharic) "የፕሮጀክት ርዕስ" else "Project title"
    val editorLabel = if (amharic) "ታሪክዎን እዚህ ይጻፉ…" else "Write your story here…"
    val savedLabel = if (amharic) "በዚህ መሣሪያ ላይ ተቀምጧል" else "Saved on this device"
    val intro = if (amharic) "የፊልም እና የመድረክ ታሪኮችዎን በነፃ ይጻፉ።"
        else "Write your screen and stage stories, even offline."

    val saveCurrent: suspend () -> Unit = {
        val id = selectedId
        if (id != null) {
            val savedTitle = title
            val savedBody = ProjectTextPolicy.preserveSourceText(body)
            withContext(Dispatchers.IO) { store.updateProjectDetails(id, savedTitle, savedBody, scriptType) }
        }
    }

    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument(TALORA_MIME_TYPE)
    ) { uri: Uri? ->
        val bytes = pendingExport
        pendingExport = null
        if (uri != null && bytes != null) {
            scope.launch {
                busy = true
                status = ""
                try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri, "wt")?.use { it.write(bytes) }
                            ?: error("Unable to open the selected destination.")
                    }
                    status = if (amharic) "የTALORA ፋይል ተቀምጧል።" else "TALORA project exported."
                } catch (error: Exception) {
                    status = error.message ?: "Export failed."
                } finally {
                    busy = false
                }
            }
        }
    }

    val textExportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri: Uri? ->
        val bytes = pendingExport
        pendingExport = null
        if (uri != null && bytes != null) {
            scope.launch {
                busy = true
                try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri, "wt")?.use { it.write(bytes) }
                            ?: error("Unable to open the selected destination.")
                    }
                    status = if (amharic) "ጽሑፉ ተልኳል።" else "Text script exported."
                } catch (error: Exception) {
                    status = error.message ?: "Text export failed."
                } finally {
                    busy = false
                }
            }
        }
    }

    val pdfExportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri: Uri? ->
        val bytes = pendingExport
        pendingExport = null
        if (uri != null && bytes != null) {
            scope.launch {
                busy = true
                try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri, "wt")?.use { it.write(bytes) }
                            ?: error("Unable to open the selected destination.")
                    }
                    status = if (amharic) "PDF ተልኳል።" else "PDF exported."
                } catch (error: Exception) {
                    status = error.message ?: "PDF export failed."
                } finally {
                    busy = false
                }
            }
        }
    }

    val assetSaveLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("*/*")
    ) { uri: Uri? ->
        val pending = pendingAssetExport
        pendingAssetExport = null
        if (uri != null && pending != null) {
            scope.launch {
                busy = true
                try {
                    withContext(Dispatchers.IO) {
                        context.contentResolver.openOutputStream(uri, "wt")?.use { it.write(pending.second) }
                            ?: error("Unable to open the selected destination.")
                    }
                    status = if (amharic) "አባሪው ተቀምጧል።" else "Attachment saved as a copy."
                } catch (error: Exception) {
                    status = error.message ?: "Unable to save attachment."
                } finally {
                    busy = false
                }
            }
        }
    }

    val assetLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        val projectId = selectedId
        val replaceId = assetReplaceTargetId
        assetReplaceTargetId = null
        if (uri != null && projectId != null) {
            scope.launch {
                busy = true
                try {
                    withContext(Dispatchers.IO) {
                        val bytes = context.contentResolver.openInputStream(uri)?.use {
                            it.readBounded(TaloraArchive.MAX_ENTRY_BYTES)
                        } ?: error("Unable to read the selected asset.")
                        val name = context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
                            ?.use { cursor ->
                                if (cursor.moveToFirst()) cursor.getString(0) else null
                            } ?: uri.lastPathSegment?.substringAfterLast('/') ?: "attachment"
                        val mediaType = context.contentResolver.getType(uri) ?: "application/octet-stream"
                        val safeName = name.substringAfterLast('/').take(255)
                        if (replaceId == null) {
                            store.addAsset(projectId, safeName, mediaType, bytes)
                        } else {
                            store.replaceAsset(projectId, replaceId, safeName, mediaType, bytes)
                        }
                    }
                    assets = withContext(Dispatchers.IO) { store.listAssets(projectId) }
                    status = if (replaceId == null) {
                        if (amharic) "አባሪው ተጨምሯል።" else "Asset attached."
                    } else {
                        if (amharic) "አባሪው ተተክቷል።" else "Asset replaced."
                    }
                } catch (error: Exception) {
                    status = error.message ?: "Unable to attach or replace asset."
                } finally {
                    busy = false
                }
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                busy = true
                status = ""
                try {
                    saveCurrent()
                    val selectedName = withContext(Dispatchers.IO) {
                        context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
                            ?.use { cursor -> if (cursor.moveToFirst()) cursor.getString(0) else null }
                            ?: uri.lastPathSegment?.substringAfterLast('/') ?: "imported-script"
                    }
                    val selectedMime = context.contentResolver.getType(uri)
                    val textFormat = ScriptInterchange.detectImportFormat(selectedName, selectedMime)
                    val bytes = withContext(Dispatchers.IO) {
                        val limit = if (textFormat != null) TaloraArchive.MAX_ENTRY_BYTES else TaloraArchive.MAX_ARCHIVE_BYTES
                        context.contentResolver.openInputStream(uri)?.use { it.readBounded(limit) }
                            ?: error("Unable to read the selected file.")
                    }
                    if (textFormat != null) {
                        val importedText = withContext(Dispatchers.Default) { ScriptInterchange.decodeUtf8(bytes) }
                        val importedType = if (textFormat == TextImportFormat.FOUNTAIN) "screenplay" else scriptType
                        val importedTitle = selectedName.substringBeforeLast('.').ifBlank { untitled }
                        val (createdProject, refreshedProjects) = withContext(Dispatchers.IO) {
                            val created = store.createProject(importedTitle, importedType)
                            store.updateProjectDetails(created.id, importedTitle, importedText, importedType)
                            created.copy(title = importedTitle, body = importedText, scriptType = importedType) to store.listProjectSummaries()
                        }
                        projects = refreshedProjects
                        selectedId = createdProject.id
                        title = createdProject.title
                        body = createdProject.body
                        scriptType = createdProject.scriptType
                        status = if (amharic) "የጽሑፍ ፕሮጀክት ገብቷል።" else "Text script imported."
                    } else {
                        val imported = withContext(Dispatchers.Default) { TaloraArchive.decode(bytes) }
                        val (importedProject, refreshedProjects) = withContext(Dispatchers.IO) {
                            val created = store.importPackage(imported)
                            created to store.listProjectSummaries()
                        }
                        projects = refreshedProjects
                        selectedId = importedProject.id
                        title = importedProject.title
                        body = importedProject.body
                        scriptType = importedProject.scriptType
                        status = if (amharic) "ፕሮጀክቱ ተጨምሯል።" else "TALORA project imported."
                    }
                } catch (error: Exception) {
                    status = error.message ?: if (amharic) "ፋይሉን ማስገባት አልተቻለም።" else "Import failed."
                } finally {
                    busy = false
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        projects = withContext(Dispatchers.IO) { store.listProjectSummaries() }
    }

    LaunchedEffect(selectedId) {
        val id = selectedId
        if (id == null) {
            revisions = emptyList()
            assets = emptyList()
            selectedRevisionA = null
            selectedRevisionB = null
        } else {
            val related = withContext(Dispatchers.IO) {
                store.listRevisions(id) to store.listAssets(id)
            }
            revisions = related.first
            assets = related.second
        }
    }

    LaunchedEffect(selectedId, title, body, scriptType) {
        val id = selectedId ?: return@LaunchedEffect
        delay(750)
        withContext(Dispatchers.IO) {
            store.updateProjectDetails(id, title, ProjectTextPolicy.preserveSourceText(body), scriptType)
        }
        projects = withContext(Dispatchers.IO) { store.listProjectSummaries() }
    }

    Column(
        modifier = Modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("TALORA", style = MaterialTheme.typography.headlineMedium)
                Text(intro, style = MaterialTheme.typography.bodyMedium)
            }
            TextButton(onClick = { amharic = !amharic }) {
                Text(if (amharic) "EN" else "አማ")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                enabled = !busy,
                onClick = {
                    scope.launch {
                        busy = true
                        try {
                            saveCurrent()
                            val created = withContext(Dispatchers.IO) { store.createProject(untitled, scriptType) }
                            projects = withContext(Dispatchers.IO) { store.listProjectSummaries() }
                            selectedId = created.id
                            title = created.title
                            body = created.body
                            status = ""
                        } catch (error: Exception) {
                            status = error.message ?: "Unable to create project."
                        } finally {
                            busy = false
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text(createLabel) }
            TextButton(
                enabled = !busy,
                onClick = { importLauncher.launch(arrayOf(TALORA_MIME_TYPE, "application/zip", "application/octet-stream", "text/plain")) }
            ) { Text(if (amharic) "ክፈት" else "Open") }
        }

        Text(projectsLabel, style = MaterialTheme.typography.titleMedium)
        if (projects.isEmpty()) {
            Text(if (amharic) "ገና ፕሮጀክት አልፈጠሩም።" else "No projects yet.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(112.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(projects, key = { it.id }) { project ->
                    TextButton(
                        enabled = !busy,
                        onClick = {
                            if (project.id != selectedId) {
                                scope.launch {
                                    busy = true
                                    try {
                                        saveCurrent()
                                        val opened = withContext(Dispatchers.IO) {
                                            store.getProject(project.id)
                                        } ?: error("TALORA project no longer exists.")
                                        selectedId = opened.id
                                        title = opened.title
                                        body = opened.body
                                        scriptType = opened.scriptType
                                        status = ""
                                    } catch (error: Exception) {
                                        status = error.message ?: "Unable to switch project."
                                    } finally {
                                        busy = false
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(project.title.ifBlank { untitled }) }
                }
            }
        }

        if (selectedId == null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("TALORA Creative Studio", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        if (amharic) "አዲስ ጽሑፍ ይፍጠሩ። ስራዎ በመሣሪያዎ ላይ ይቀመጣል፤ ኢንተርኔት አያስፈልግም።"
                        else "Create a script. Your work is stored locally and does not require an internet connection."
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                enabled = !busy,
                label = { Text(titleLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val elements = if (scriptType == "screenplay") {
                    listOf("Scene", "Action", "Character", "Dialogue", "Transition")
                } else {
                    listOf("Act", "Scene", "Direction", "Character", "Dialogue")
                }
                elements.forEach { element ->
                    TextButton(enabled = !busy, onClick = {
                        val template = when (scriptType to element) {
                            "screenplay" to "Scene" -> "INT. LOCATION - DAY"
                            "screenplay" to "Action" -> "Action description."
                            "screenplay" to "Character" -> "CHARACTER NAME"
                            "screenplay" to "Dialogue" -> "Dialogue text."
                            "screenplay" to "Transition" -> "CUT TO:"
                            "stage_play" to "Act" -> "ACT I"
                            "stage_play" to "Scene" -> "SCENE 1"
                            "stage_play" to "Direction" -> "[Stage direction]"
                            "stage_play" to "Character" -> "CHARACTER NAME:"
                            else -> "    Dialogue text."
                        }
                        body = if (body.isBlank()) template else body.trimEnd() + "\n\n" + template
                    }) { Text(element) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = { scriptType = "screenplay" }, enabled = !busy) {
                    Text(if (scriptType == "screenplay") "● Screenplay" else "Screenplay")
                }
                TextButton(onClick = { scriptType = "stage_play" }, enabled = !busy) {
                    Text(if (scriptType == "stage_play") "● Stage play" else "Stage play")
                }
            }
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                enabled = !busy,
                label = { Text(editorLabel) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 260.dp, max = 520.dp),
                minLines = 8
            )
            Text(
                "${body.length} characters · ${body.count { it == 10.toChar() } + 1} lines",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
                value = changeNote,
                onValueChange = { changeNote = it },
                enabled = !busy,
                label = { Text(if (amharic) "የስሪት ማስታወሻ (አማራጭ)" else "Revision note (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = !busy,
                    onClick = {
                        val projectId = selectedId ?: return@Button
                        scope.launch {
                            busy = true
                            try {
                                saveCurrent()
                                withContext(Dispatchers.IO) {
                                    store.createRevision(projectId, title, ProjectTextPolicy.preserveSourceText(body), changeNote)
                                }
                                revisions = withContext(Dispatchers.IO) { store.listRevisions(projectId) }
                                changeNote = ""
                                status = if (amharic) "የስሪት ቅጂ ተቀምጧል።" else "Revision snapshot saved."
                            } catch (error: Exception) {
                                status = error.message ?: "Unable to save revision."
                            } finally {
                                busy = false
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text(if (amharic) "ስሪት አስቀምጥ" else "Save revision") }
                TextButton(
                    enabled = !busy,
                    onClick = { assetLauncher.launch(arrayOf("*/*")) }
                ) { Text(if (amharic) "አባሪ ጨምር" else "Attach asset") }
            }
            if (revisions.isNotEmpty()) {
                Text(if (amharic) "የስሪት ታሪክ" else "Revision history", style = MaterialTheme.typography.titleSmall)
                Text(
                    if (amharic) "ለማነፃፀር A እና B ይምረጡ።" else "Select A and B to compare two immutable snapshots.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 190.dp)) {
                    items(revisions, key = { it.id }) { revision ->
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                            Text(revision.title, style = MaterialTheme.typography.bodySmall)
                            Text(
                                "${revision.changeNote ?: java.text.DateFormat.getDateTimeInstance().format(java.util.Date(revision.createdAt))} · ${revision.contentLength} chars",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                TextButton(enabled = !busy, onClick = {
                                    selectedRevisionA = if (selectedRevisionA == revision.id) null else revision.id
                                    if (selectedRevisionB == revision.id) selectedRevisionB = null
                                }) { Text(if (selectedRevisionA == revision.id) "A ✓" else "A") }
                                TextButton(enabled = !busy, onClick = {
                                    selectedRevisionB = if (selectedRevisionB == revision.id) null else revision.id
                                    if (selectedRevisionA == revision.id) selectedRevisionA = null
                                }) { Text(if (selectedRevisionB == revision.id) "B ✓" else "B") }
                                TextButton(enabled = !busy, onClick = {
                                    editingRevisionId = revision.id
                                    revisionNoteDraft = revision.changeNote.orEmpty()
                                }) { Text(if (amharic) "ማስታወሻ" else "Note") }
                                TextButton(
                                    enabled = !busy,
                                    onClick = {
                                        val projectId = selectedId ?: return@TextButton
                                        scope.launch {
                                            busy = true
                                            try {
                                                val restored = withContext(Dispatchers.IO) {
                                                    store.restoreRevision(projectId, revision.id)
                                                }
                                                title = restored.title
                                                body = restored.body
                                                revisions = withContext(Dispatchers.IO) { store.listRevisions(projectId) }
                                                status = if (amharic) "ስሪቱ ተመልሷል።" else "Revision restored."
                                            } catch (error: Exception) {
                                                status = error.message ?: "Unable to restore revision."
                                            } finally {
                                                busy = false
                                            }
                                        }
                                    }
                                ) { Text(if (amharic) "መልስ" else "Restore") }
                            }
                        }
                    }
                }
                Button(
                    enabled = !busy && selectedRevisionA != null && selectedRevisionB != null,
                    onClick = {
                        val projectId = selectedId ?: return@Button
                        val beforeId = selectedRevisionA ?: return@Button
                        val afterId = selectedRevisionB ?: return@Button
                        scope.launch {
                            busy = true
                            try {
                                val pair = withContext(Dispatchers.IO) {
                                    store.getRevision(projectId, beforeId) to store.getRevision(projectId, afterId)
                                }
                                val before = pair.first ?: error("Earlier revision was not found.")
                                val after = pair.second ?: error("Later revision was not found.")
                                comparisonTitle = "${before.title} → ${after.title}"
                                comparisonLines = withContext(Dispatchers.Default) {
                                    RevisionDiff.compare(before.content, after.content)
                                }
                                showComparison = true
                            } catch (error: Exception) {
                                status = error.message ?: "Unable to compare revisions."
                            } finally {
                                busy = false
                            }
                        }
                    }
                ) { Text(if (amharic) "ስሪቶችን አነፃፅር" else "Compare revisions") }
            }
            Text(if (amharic) "አባሪዎች" else "Attachments", style = MaterialTheme.typography.titleSmall)
            if (assets.isEmpty()) {
                Text(if (amharic) "አባሪ የለም" else "No attachments", style = MaterialTheme.typography.bodySmall)
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 220.dp)) {
                    items(assets, key = { it.id }) { asset ->
                        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Text(asset.fileName, style = MaterialTheme.typography.bodySmall)
                            Text("${asset.mediaType} · ${asset.sizeBytes} bytes", style = MaterialTheme.typography.labelSmall)
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                TextButton(enabled = !busy, onClick = {
                                    val projectId = selectedId ?: return@TextButton
                                    scope.launch {
                                        busy = true
                                        try {
                                            val bytes = withContext(Dispatchers.IO) { store.getAssetBytes(projectId, asset.id) }
                                            previewAssetName = asset.fileName
                                            previewAssetType = asset.mediaType
                                            previewBitmap = if (asset.mediaType.startsWith("image/")) {
                                                withContext(Dispatchers.Default) { decodeImagePreview(bytes) }
                                            } else null
                                            previewText = if (asset.mediaType.startsWith("text/") ||
                                                asset.mediaType.contains("json") || asset.mediaType.contains("xml")) {
                                                String(bytes, Charsets.UTF_8).take(16_000)
                                            } else ""
                                            showAssetPreview = true
                                        } catch (error: Exception) {
                                            status = error.message ?: "Unable to preview asset."
                                        } finally {
                                            busy = false
                                        }
                                    }
                                }) { Text(if (amharic) "ቅድመ እይታ" else "Preview") }
                                TextButton(enabled = !busy, onClick = {
                                    val projectId = selectedId ?: return@TextButton
                                    scope.launch {
                                        busy = true
                                        try {
                                            val bytes = withContext(Dispatchers.IO) { store.getAssetBytes(projectId, asset.id) }
                                            pendingAssetExport = asset.fileName to bytes
                                            assetSaveLauncher.launch(asset.fileName)
                                        } catch (error: Exception) {
                                            status = error.message ?: "Unable to prepare asset copy."
                                        } finally {
                                            busy = false
                                        }
                                    }
                                }) { Text(if (amharic) "እንደ አዲስ አስቀምጥ" else "Save as") }
                                TextButton(enabled = !busy, onClick = {
                                    assetReplaceTargetId = asset.id
                                    assetLauncher.launch(arrayOf("*/*"))
                                }) { Text(if (amharic) "ተካ" else "Replace") }
                                TextButton(
                                    enabled = !busy,
                                    onClick = {
                                        val projectId = selectedId ?: return@TextButton
                                        scope.launch {
                                            busy = true
                                            try {
                                                withContext(Dispatchers.IO) { store.removeAsset(projectId, asset.id) }
                                                assets = withContext(Dispatchers.IO) { store.listAssets(projectId) }
                                                status = if (amharic) "አባሪው ተወግዷል።" else "Asset removed."
                                            } catch (error: Exception) {
                                                status = error.message ?: "Unable to remove asset."
                                            } finally {
                                                busy = false
                                            }
                                        }
                                    }
                                ) { Text(if (amharic) "አስወግድ" else "Remove") }
                            }
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = !busy,
                    onClick = {
                        val projectId = selectedId ?: return@Button
                        scope.launch {
                            busy = true
                            try {
                                saveCurrent()
                                val packageData = withContext(Dispatchers.IO) { store.exportPackage(projectId) }
                                pendingExport = withContext(Dispatchers.Default) { TaloraArchive.encode(packageData) }
                                exportLauncher.launch(safeTaloraFileName(title))
                            } catch (error: Exception) {
                                status = error.message ?: "Export preparation failed."
                            } finally {
                                busy = false
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text(if (amharic) "ወደ ውጭ ላክ" else "Export .talora") }
                Text(savedLabel, style = MaterialTheme.typography.labelMedium)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                TextButton(enabled = !busy, onClick = {
                    pendingExport = ScriptInterchange.exportPlainText(body)
                    textExportLauncher.launch(safeScriptFileName(title, "txt"))
                }) { Text("TXT") }
                TextButton(enabled = !busy && scriptType == "screenplay", onClick = {
                    if (scriptType == "screenplay") {
                        pendingExport = ScriptInterchange.exportFountain(scriptType, body)
                        textExportLauncher.launch(safeScriptFileName(title, "fountain"))
                    }
                }) { Text("Fountain") }
                TextButton(enabled = !busy, onClick = {
                    scope.launch {
                        busy = true
                        try {
                            saveCurrent()
                            pendingExport = withContext(Dispatchers.Default) { ScriptPdfExporter.create(title, body) }
                            pdfExportLauncher.launch(safeScriptFileName(title, "pdf"))
                        } catch (error: Exception) {
                            status = error.message ?: "PDF export preparation failed."
                        } finally {
                            busy = false
                        }
                    }
                }) { Text("PDF") }
            }
        }
        if (status.isNotBlank()) {
            Text(status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }

    if (editingRevisionId != null) {
        AlertDialog(
            onDismissRequest = { editingRevisionId = null },
            title = { Text(if (amharic) "የስሪት ማስታወሻ" else "Edit revision note") },
            text = {
                OutlinedTextField(
                    value = revisionNoteDraft,
                    onValueChange = { revisionNoteDraft = it },
                    label = { Text(if (amharic) "ማስታወሻ" else "Change note") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(enabled = !busy, onClick = {
                    val projectId = selectedId ?: return@TextButton
                    val revisionId = editingRevisionId ?: return@TextButton
                    scope.launch {
                        busy = true
                        try {
                            withContext(Dispatchers.IO) {
                                store.updateRevisionNote(projectId, revisionId, revisionNoteDraft)
                            }
                            revisions = withContext(Dispatchers.IO) { store.listRevisions(projectId) }
                            editingRevisionId = null
                            status = if (amharic) "ማስታወሻው ተዘምኗል።" else "Revision note updated."
                        } catch (error: Exception) {
                            status = error.message ?: "Unable to update revision note."
                        } finally {
                            busy = false
                        }
                    }
                }) { Text(if (amharic) "አስቀምጥ" else "Save") }
            },
            dismissButton = {
                TextButton(onClick = { editingRevisionId = null }) { Text(if (amharic) "ዝጋ" else "Cancel") }
            }
        )
    }

    if (showComparison) {
        AlertDialog(
            onDismissRequest = { showComparison = false },
            title = { Text(if (amharic) "የስሪት ንፅፅር" else "Revision comparison") },
            text = {
                Column(modifier = Modifier.heightIn(max = 480.dp)) {
                    Text(comparisonTitle, style = MaterialTheme.typography.titleSmall)
                    Text(
                        if (amharic) "ቀይ = የተወገደ፣ አረንጓዴ = የተጨመረ" else "Red = removed · Green = added",
                        style = MaterialTheme.typography.labelSmall
                    )
                    LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                        items(comparisonLines) { line ->
                            val color = when (line.kind) {
                                RevisionDiffKind.REMOVED -> MaterialTheme.colorScheme.error
                                RevisionDiffKind.ADDED -> MaterialTheme.colorScheme.primary
                                RevisionDiffKind.UNCHANGED -> MaterialTheme.colorScheme.onSurfaceVariant
                            }
                            val prefix = when (line.kind) {
                                RevisionDiffKind.REMOVED -> "− "
                                RevisionDiffKind.ADDED -> "+ "
                                RevisionDiffKind.UNCHANGED -> "  "
                            }
                            Text(prefix + line.text, color = color, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showComparison = false }) { Text(if (amharic) "ዝጋ" else "Close") }
            }
        )
    }

    if (showAssetPreview) {
        AlertDialog(
            onDismissRequest = { showAssetPreview = false; previewBitmap = null },
            title = { Text(previewAssetName) },
            text = {
                Column(modifier = Modifier.heightIn(max = 420.dp)) {
                    Text(previewAssetType, style = MaterialTheme.typography.labelSmall)
                    if (previewBitmap != null) {
                        Image(
                            bitmap = previewBitmap!!.asImageBitmap(),
                            contentDescription = previewAssetName,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth().heightIn(max = 320.dp)
                        )
                    } else if (previewText.isNotBlank()) {
                        Text(
                            previewText,
                            modifier = Modifier.fillMaxWidth().heightIn(max = 340.dp).verticalScroll(rememberScrollState()),
                            style = MaterialTheme.typography.bodySmall
                        )
                    } else {
                        Text(if (amharic) "የዚህ ፋይል ቅድመ እይታ አይደገፍም።" else "Preview is not available for this file type.")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAssetPreview = false; previewBitmap = null }) { Text(if (amharic) "ዝጋ" else "Close") }
            }
        )
    }
}

private fun decodeImagePreview(bytes: ByteArray): Bitmap? {
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bounds)
    if (bounds.outWidth <= 0 || bounds.outHeight <= 0) return null
    var sample = 1
    while (bounds.outWidth / sample > 1400 || bounds.outHeight / sample > 1400) sample *= 2
    val options = BitmapFactory.Options().apply { inSampleSize = sample }
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
}

private fun InputStream.readBounded(maxBytes: Int): ByteArray {
    val output = ByteArrayOutputStream()
    val buffer = ByteArray(8192)
    var total = 0
    while (true) {
        val count = read(buffer)
        if (count < 0) break
        total += count
        require(total <= maxBytes) { "Selected TALORA file exceeds the size limit." }
        output.write(buffer, 0, count)
    }
    return output.toByteArray()
}

private fun safeTaloraFileName(title: String): String {
    return safeScriptFileName(title, "talora")
}

private fun safeScriptFileName(title: String, extension: String): String {
    val base = title.trim().replace(Regex("[^A-Za-z0-9_-]+"), "-").trim('-').take(60)
    return "${base.ifBlank { "talora-project" }}.$extension"
}

private const val TALORA_MIME_TYPE = "application/vnd.talora.project+zip"
