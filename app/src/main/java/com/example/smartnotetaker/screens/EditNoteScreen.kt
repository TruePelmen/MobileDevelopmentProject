package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel

@Composable
fun EditNoteScreen(
    id: Long,
    navController: NavController,
    viewModel: MainViewModel
) {
    val note by viewModel.noteState

    LaunchedEffect(id) {
        viewModel.getNoteById(id)
    }

    var noteName by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    LaunchedEffect(note) {
        note?.let {
            noteName = it.name
            noteText = it.text
        }
    }

    if (note != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "Edit Note", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = noteName,
                onValueChange = { noteName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedNote = note!!.copy(name = noteName, text = noteText)
                    viewModel.updateNote(updatedNote)
                    navController.popBackStack()
                }
            ) {
                Text("Save")
            }
        }
    } else {
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}