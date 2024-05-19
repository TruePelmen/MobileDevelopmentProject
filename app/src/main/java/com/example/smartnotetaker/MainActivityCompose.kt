package com.example.smartnotetaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartnotetaker.components.AddCollectionScreen
import com.example.smartnotetaker.components.AddNoteScreen
import com.example.smartnotetaker.components.CollectionsScreen
import com.example.smartnotetaker.components.DeleteCollectionDialog
import com.example.smartnotetaker.components.EditCollectionScreen
import com.example.smartnotetaker.components.NotesScreen
import com.example.smartnotetaker.di.appModule
import com.example.smartnotetaker.di.dataModule
import com.example.smartnotetaker.di.domainModule
import com.example.smartnotetaker.ui.theme.SmartNoteTakerTheme
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(application)
            // Вказати список модулів для ініціалізації
            modules(domainModule, dataModule, appModule)
        }
        // Отримайте ViewModel з Koin
        val viewModel: MainViewModel = get<MainViewModel>()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SmartNoteTakerTheme {
                Scaffold(modifier = Modifier.padding(0.dp)) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "Collections",
                        modifier = Modifier.padding(innerPadding)
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
            }
        }
    }
}







