package com.example.smartnotetaker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.models.Collection
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
                    }
                }
            }
        }
    }
}

@Composable
fun CollectionsScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier
) {
    val collections = viewModel.getCollections()
    Column(modifier = modifier) {
        Row{
            Text(text = "Collections", style = MaterialTheme.typography.headlineLarge)

        }

        Spacer(modifier = Modifier.height(16.dp))
        if (collections.isEmpty()) {
            Text(text = "No collections yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(collections) { collection ->
                    CollectionItem(collection, onCollectionClicked = {
                        // Handle collection click event (e.g., navigate to edit screen)
                    }, onEditClicked = {
                        navController.navigate("EditCollection/${collection.id}")
                    },
                        onDeleteClicked = {
                            navController.navigate("DeleteCollectionDialog/${collection.id}")
                        }
                    )
                }
            }
            IconButton(onClick = { navController.navigate("AddCollection") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add new collection")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                IconButton(onClick = { onEditClicked()}) {
                    Icon(Icons.Filled.Edit, contentDescription = "Add new collection")
                }
                IconButton(onClick = { onDeleteClicked()}) {
                    Icon(Icons.Filled.Delete, contentDescription = "Add new collection")
                }
           }
        }
    }
}


@Composable
fun AddCollectionScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Log.d("Navigation", "AddCollectionScreen has started")

    var collectionName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = collectionName,
            onValueChange = { newName -> collectionName = newName },
            label = { Text("Collection Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (collectionName.isNotBlank()) {
                viewModel.addCollection(collectionName)
                navController.popBackStack()
            }
        }) {
            Text(text = "Create Collection")
        }
    }
}

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


@Composable
fun DeleteCollectionDialog(
    collectionId: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}