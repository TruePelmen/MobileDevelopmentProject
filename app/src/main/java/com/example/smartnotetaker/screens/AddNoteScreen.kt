package com.example.smartnotetaker.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.models.Note
import com.example.smartnotetaker.MainViewModel
import java.sql.Date

@Composable
fun AddNoteScreen(
    collectionId: Long,
    navController: NavController,
    viewModel: MainViewModel
) {
    Log.d("Navigation", "AddCollectionScreen has started")

    var noteName by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            maxLines = 1,
            value = noteName,
            onValueChange = { newName -> noteName = newName },
            label = { Text("Note Name") },
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = noteText,
            onValueChange = { newText -> noteText = newText },
            label = { Text("Note Text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (noteName.isNotBlank()) {
                val currentDate = Date(System.currentTimeMillis())
                val note = Note(
                    name = noteName,
                    text = noteText,
                    nextRepetition = currentDate,
                    lastRepetition = currentDate,
                    creationDate = currentDate,
                    collectionId = collectionId
                )
                viewModel.addNote(note, collectionId.toString())
                navController.popBackStack()
            }
        }) {
            Text(text = "Create Note")
        }
    }
}
