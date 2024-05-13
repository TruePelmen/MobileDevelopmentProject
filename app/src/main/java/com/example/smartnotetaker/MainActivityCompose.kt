package com.example.smartnotetaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
//        val db = Room.databaseBuilder(
//            this,
//            MyDatabase::class.java, "database-name"
//        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
        // Отримайте ViewModel з Koin
        val viewModel: MainViewModel = get<MainViewModel>()
        enableEdgeToEdge()
        setContent {
            SmartNoteTakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CollectionsScreen(
                        viewModel = viewModel,
                        onCollectionClicked = { /* обробка кліку на колекцію */ },
                        onAddCollectionClicked = { /* обробка додавання нової колекції */ },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionsScreen(
    viewModel: MainViewModel,
    onCollectionClicked: (Collection) -> Unit,
    onAddCollectionClicked: () -> Unit,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Collections") },
                actions = {
                    IconButton(onClick = { onAddCollectionClicked() }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add new collection")
                    }
                }
            )
        }
    ) { padding ->
        CollectionsList(collections = viewModel.getCollections(), onCollectionClicked = onCollectionClicked, modifier = Modifier
            .padding(padding))
    }
}

@Composable
fun CollectionsList(
    collections: List<Collection>,
    onCollectionClicked: (Collection) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier//Modifier.fillMaxSize()
    ) {
        items(collections) { collection ->
            CollectionItem(collection = collection, onCollectionClicked = onCollectionClicked)
            Divider()
        }
    }
}

@Composable
fun CollectionItem(
    collection: Collection,
    onCollectionClicked: (Collection) -> Unit
) {
    Text(
        text = collection.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onCollectionClicked(collection) }
    )
}



