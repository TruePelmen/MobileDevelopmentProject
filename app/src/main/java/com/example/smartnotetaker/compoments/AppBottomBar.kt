package com.example.smartnotetaker.compoments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AppBottomBar(navController: NavController) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Arrange buttons evenly spaced
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("Collections") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = { navController.navigate("Search") }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(onClick = { navController.navigate("Settings") }) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }
    }
}
