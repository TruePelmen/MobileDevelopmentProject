package com.example.smartnotetaker.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.models.Collection

@Composable
fun CollectionItem(
    collection: Collection,
    onCollectionClicked: (Collection) -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Surface(modifier = Modifier.padding(8.dp), color = Color.Transparent) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Text(text = collection.name, style = MaterialTheme.typography.bodyMedium, )
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = { onCollectionClicked(collection)}) {
                    Icon(Icons.Filled.List, contentDescription = "View notes from this collection")
                }
                IconButton(onClick = { onEditClicked()}) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit this collection")
                }
                IconButton(onClick = { onDeleteClicked()}) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete this collection")
                }
            }
        }
    }
}