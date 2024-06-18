package com.example.smartnotetaker.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartnotetaker.MainViewModel
import com.example.smartnotetaker.components.AppBottomBar

@Composable
fun RepetitionScreen(
    collectionId: String,
    navController: NavController,
    viewModel: MainViewModel
)
{
    Scaffold(
        bottomBar = { AppBottomBar(navController = navController, collectionId) }
    ) { innerPadding ->
        Text(text = "Aaaaaaa", modifier = Modifier.padding(innerPadding))
    }
}