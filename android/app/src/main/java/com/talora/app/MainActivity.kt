package com.talora.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
    var projects by remember { mutableStateOf(store.listProjects()) }
    var selectedId by remember { mutableStateOf<String?>(null) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var amharic by remember { mutableStateOf(true) }

    val untitled = if (amharic) "ርዕስ የሌለው ፕሮጀክት" else "Untitled project"
    val createLabel = if (amharic) "አዲስ ጽሑፍ" else "New script"
    val projectsLabel = if (amharic) "ፕሮጀክቶቼ" else "My projects"
    val titleLabel = if (amharic) "የፕሮጀክት ርዕስ" else "Project title"
    val editorLabel = if (amharic) "ታሪክዎን እዚህ ይጻፉ…" else "Write your story here…"
    val savedLabel = if (amharic) "በዚህ መሣሪያ ላይ ተቀምጧል" else "Saved on this device"
    val intro = if (amharic) {
        "የፊልም እና የመድረክ ታሪኮችዎን በነፃ ይጻፉ።"
    } else {
        "Write your screen and stage stories, even offline."
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

        Button(
            onClick = {
                val created = store.createProject(untitled)
                projects = store.listProjects()
                selectedId = created.id
                title = created.title
                body = created.body
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(createLabel)
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
                        onClick = {
                            selectedId = project.id
                            title = project.title
                            body = project.body
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(project.title.ifBlank { untitled })
                    }
                }
            }
        }

        if (selectedId == null) {
            Card(modifier = Modifier.fillMaxWidth().weight(1f)) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        if (amharic) "TALORA Creative Studio" else "TALORA Creative Studio",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        if (amharic) {
                            "አዲስ ጽሑፍ ይፍጠሩ። ስራዎ በመሣሪያዎ ላይ ይቀመጣል፤ ኢንተርኔት አያስፈልግም።"
                        } else {
                            "Create a script. Your work is stored locally and does not require an internet connection."
                        }
                    )
                }
            }
        } else {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(titleLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                label = { Text(editorLabel) },
                modifier = Modifier.fillMaxWidth().weight(1f),
                minLines = 8
            )
            Text(savedLabel, style = MaterialTheme.typography.labelMedium)
        }
    }
}
