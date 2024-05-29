package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel

@Composable
fun NotesScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    LaunchedEffect(collectionId) {
        viewModel.getNotesForCollection(collectionId)
    }
    val notes = viewModel.getNotesUiState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Notes", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (notes.isEmpty()) {
            Text(text = "No notes in this collection.", style = MaterialTheme.typography.bodyMedium)
        } else {
            NoteList(
                notes = notes,
                onEditNoteClick = { noteId ->
                    // Navigate to edit note screen
                    navController.navigate("EditNote/$noteId")
                },
                onUndoDeleteClick = {
                    // Handle undo delete action
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(onClick = { navController.navigate("AddNote/$collectionId") },
            modifier = Modifier.height(16.dp)) {
            Icon(Icons.Filled.AddCircle, contentDescription = "Add new note")
        }
    }


}