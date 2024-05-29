package com.example.smartnotetaker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.models.Note

@Composable
fun NoteCard(
    note: Note,
    navController: NavController
) {
    // Example card implementation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("ShowNote/${note.id}")
            }
    ) {
        Row{
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = note.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = note.text, style = MaterialTheme.typography.bodyMedium)
            }
        }

    }
}
