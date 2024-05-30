package com.example.smartnotetaker.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.models.Note

@Composable
fun NoteCard(
    note: Note,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 100.dp, max = 150.dp) // Set the min and max height of the card
            .clickable {
                navController.navigate("ShowNote/${note.id}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3, // Limit the number of lines for the text
                overflow = TextOverflow.Ellipsis // Show "..." when text is cut off
            )
        }
    }
}

