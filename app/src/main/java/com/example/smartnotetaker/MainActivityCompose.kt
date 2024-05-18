package com.example.smartnotetaker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        Text(text = "Collections", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        if (collections.isEmpty()) {
            Text(text = "No collections yet.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(collections) { collection ->
                    CollectionItem(collection, onCollectionClicked = {
                        // Handle collection click event (e.g., navigate to edit screen)
                    })
                }
            }
        }
        Button(onClick = {
            navController.navigate("AddCollection")
        }) {
            Text(text = "Add a new collection")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionItem(
    collection: Collection,
    onCollectionClicked: (Collection) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        onClick = { onCollectionClicked(collection) }
    ) {
        Surface(modifier = Modifier.padding(16.dp)) {
            Text(text = collection.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AddCollectionScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    Log.d("Navigation", "AddCollectionScreen has started")
    var collectionName = ""
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = collectionName,
            onValueChange = { collectionName = it },
            label = { Text("Collection Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.addCollection(collectionName)
            navController.popBackStack()
        }) {
            Text(text = "Create Collection")
        }
    }
}
