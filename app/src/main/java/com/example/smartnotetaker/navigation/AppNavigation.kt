package com.example.smartnotetaker.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartnotetaker.MainViewModel
import com.example.smartnotetaker.screens.AddCollectionScreen
import com.example.smartnotetaker.screens.AddNoteScreen
import com.example.smartnotetaker.screens.CollectionsScreen
import com.example.smartnotetaker.screens.DeleteCollectionDialog
import com.example.smartnotetaker.screens.EditCollectionScreen
import com.example.smartnotetaker.screens.NotesScreen

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Collections",
    ) {
        composable("Collections") {
            CollectionsScreen(
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        composable("AddCollection") {
            AddCollectionScreen(navController = navController, viewModel = viewModel)
        }
        composable("EditCollection/{collectionId}") { backStackEntry ->
            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
            EditCollectionScreen(collectionId, navController, viewModel)
        }
        composable("DeleteCollectionDialog/{collectionId}"){backStackEntry ->
            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
            DeleteCollectionDialog(
                collectionId = collectionId,
                onDismissRequest = { navController.popBackStack() },
                onConfirmation = { viewModel.getCollectionById(collectionId)
                    ?.let { viewModel.deleteCollection(it)
                        viewModel.loadCollections()}
                    navController.popBackStack()},
                dialogTitle = "Deleting the collection",
                dialogText = "Are you sure you want to delete ${viewModel.getCollectionById(collectionId)}",
                icon = Icons.Default.Info
            )
        }
        composable("Notes/{collectionId}") { backStackEntry ->
            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
            NotesScreen(collectionId, navController, viewModel)
        }
        composable("AddNote/{collectionId}"){backStackEntry ->
            val collectionId = backStackEntry.arguments?.getString("collectionId") ?: return@composable
            AddNoteScreen(collectionId.toLong(), navController, viewModel)
        }
    }
}