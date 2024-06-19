package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel
import com.example.smartnotetaker.compoments.GraphView
import com.example.smartnotetaker.components.AppBottomBar

@Composable
fun GraphScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
) {
    val notes = viewModel.getNotesUiState()
    val adjacencyMatrix by viewModel.adjacencyMatrix

    LaunchedEffect(collectionId) {
        viewModel.getNotesForCollection(collectionId)
        viewModel.fetchNoteGraphUseCase(collectionId.toLong())
    }

    Scaffold(
        bottomBar = { AppBottomBar(navController = navController, collectionId) }
    ) { innerPadding ->
        if (notes.isEmpty()) {
            Text(text = "Нотаток в даній колекції не знайдено", modifier = Modifier.padding(innerPadding))
        } else if (adjacencyMatrix == null) {
            Text(text = "Loading...", modifier = Modifier.padding(innerPadding))
        } else {
            GraphView(
                notes = notes,
                adjacencyMatrix = adjacencyMatrix!!,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}
