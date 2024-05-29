package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NoteScreen(
    id: Long,
    navController: NavController,
    viewModel: MainViewModel
) {
    LaunchedEffect(id) {
        viewModel.getNoteById(id)
    }

    val note by viewModel.noteState

    if (note != null) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val creationDate = dateFormat.format(note!!.creationDate)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note!!.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = creationDate,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = note!!.text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        // Можна додати індикатор завантаження або повідомлення про відсутність нотатки
        Text("Loading...", modifier = Modifier.padding(16.dp))
    }
}