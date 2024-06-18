package com.example.smartnotetaker.components

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
fun AppBottomBar(navController: NavController, collectionId: String) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Arrange buttons evenly spaced
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route

            IconButton(onClick = {
                val targetRoute = "Notes/$collectionId"
                if (currentRoute != targetRoute) {
                    navController.navigate(targetRoute) {
                        // This ensures we do not create multiple copies of the same destination
                        launchSingleTop = true
                    }
                }
            }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = {
                val targetRoute = "Graph/$collectionId"
                if (currentRoute != targetRoute) {
                    navController.navigate(targetRoute) {
                        launchSingleTop = true
                    }
                }
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Graph")
            }
            IconButton(onClick = {
                val targetRoute = "Repetition/$collectionId"
                if (currentRoute != targetRoute) {
                    navController.navigate(targetRoute) {
                        launchSingleTop = true
                    }
                }
            }) {
                Icon(Icons.Filled.Settings, contentDescription = "Repetition")
            }
        }
    }
}

