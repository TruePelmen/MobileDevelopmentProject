package com.example.smartnotetaker.components

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
import com.example.smartnotetaker.MainViewModel

@Composable
fun EditCollectionScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    val collection = viewModel.getCollectionById(collectionId)
    var collectionName by remember { mutableStateOf(collection?.name ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = collectionName,
            onValueChange = { newName -> collectionName = newName },
            label = { Text("Collection Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (collection != null) {
                collection.name = collectionName
                viewModel.editCollection(collection)
                navController.popBackStack()
            }
        }) {
            Text(text = "Save Changes")
        }
    }
}
