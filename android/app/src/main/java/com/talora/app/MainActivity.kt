package com.talora.app

import android.net.Uri
import android.provider.OpenableColumns
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
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
            withContext(Dispatchers.IO) { store.updateProject(id, savedTitle, savedBody) }
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

    val assetLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        val projectId = selectedId
        if (uri != null && projectId != null) {
            scope.launch {
                busy = true
                try {
                    val asset = withContext(Dispatchers.IO) {
                        val bytes = context.contentResolver.openInputStream(uri)?.use {
                            it.readBounded(TaloraArchive.MAX_ENTRY_BYTES)
                        } ?: error("Unable to read the selected asset.")
                        val name = context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
                            ?.use { cursor ->
                                if (cursor.moveToFirst()) cursor.getString(0) else null
                            } ?: uri.lastPathSegment?.substringAfterLast('/') ?: "attachment"
                        val mediaType = context.contentResolver.getType(uri) ?: "application/octet-stream"
                        store.addAsset(projectId, name.substringAfterLast('/').take(255), mediaType, bytes)
                    }
                    assets = withContext(Dispatchers.IO) { store.listAssets(projectId) }
                    status = if (amharic) "አባሪው ተጨምሯል።" else "Asset attached."
                } catch (error: Exception) {
                    status = error.message ?: "Unable to attach asset."
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
                    val imported = withContext(Dispatchers.IO) {
                        val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBounded(TaloraArchive.MAX_ARCHIVE_BYTES) }
                            ?: error("Unable to read the selected file.")
                        TaloraArchive.decode(bytes)
                    }
                    val (importedProject, refreshedProjects) = withContext(Dispatchers.IO) {
                        val created = store.importPackage(imported)
                        created to store.listProjects()
                    }
                    projects = refreshedProjects
                    selectedId = importedProject.id
                    title = importedProject.title
                    body = importedProject.body
                    status = if (amharic) "ፕሮጀክቱ ተጨምሯል።" else "TALORA project imported."
                } catch (error: Exception) {
                    status = error.message ?: if (amharic) "ፋይሉን ማስገባት አልተቻለም።" else "Import failed."
                } finally {
                    busy = false
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        projects = withContext(Dispatchers.IO) { store.listProjects() }
    }

    LaunchedEffect(selectedId) {
        val id = selectedId
        if (id == null) {
            revisions = emptyList()
            assets = emptyList()
        } else {
            val related = withContext(Dispatchers.IO) {
                store.listRevisions(id) to store.listAssets(id)
            }
            revisions = related.first
            assets = related.second
        }
    }

    LaunchedEffect(selectedId, title, body) {
        val id = selectedId ?: return@LaunchedEffect
        delay(450)
        withContext(Dispatchers.IO) {
            store.updateProject(id, title, ProjectTextPolicy.preserveSourceText(body))
        }
        projects = withContext(Dispatchers.IO) { store.listProjects() }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(18.dp),
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
                            val created = withContext(Dispatchers.IO) { store.createProject(untitled) }
                            projects = withContext(Dispatchers.IO) { store.listProjects() }
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
                onClick = { importLauncher.launch(arrayOf(TALORA_MIME_TYPE, "application/zip", "application/octet-stream")) }
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
                                        selectedId = project.id
                                        title = project.title
                                        body = project.body
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
            Card(modifier = Modifier.fillMaxWidth().weight(1f)) {
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
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                enabled = !busy,
                label = { Text(editorLabel) },
                modifier = Modifier.fillMaxWidth().weight(1f),
                minLines = 8
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
                LazyColumn(modifier = Modifier.fillMaxWidth().height(104.dp)) {
                    items(revisions, key = { it.id }) { revision ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(revision.title, style = MaterialTheme.typography.bodySmall)
                                Text(
                                    revision.changeNote ?: java.text.DateFormat.getDateTimeInstance().format(java.util.Date(revision.createdAt)),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
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
            Text(if (amharic) "አባሪዎች" else "Attachments", style = MaterialTheme.typography.titleSmall)
            if (assets.isEmpty()) {
                Text(if (amharic) "አባሪ የለም" else "No attachments", style = MaterialTheme.typography.bodySmall)
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth().height(88.dp)) {
                    items(assets, key = { it.id }) { asset ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(asset.fileName, style = MaterialTheme.typography.bodySmall)
                                Text("${asset.mediaType} · ${asset.sizeBytes} bytes", style = MaterialTheme.typography.labelSmall)
                            }
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
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = !busy,
                    onClick = {
                        val projectId = selectedId ?: return@Button
                        scope.launch {
                            busy = true
                            try {
                                saveCurrent()
                                val packageData = withContext(Dispatchers.IO) {
                                    store.exportPackage(projectId)
                                }
                                val packageBytes = withContext(Dispatchers.Default) {
                                    TaloraArchive.encode(packageData)
                                }
                                pendingExport = packageBytes
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
        }
        if (status.isNotBlank()) {
            Text(status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
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
    val base = title.trim().replace(Regex("[^A-Za-z0-9_-]+"), "-").trim('-').take(60)
    return "${base.ifBlank { "talora-project" }}.talora"
}

private const val TALORA_MIME_TYPE = "application/vnd.talora.project+zip"
