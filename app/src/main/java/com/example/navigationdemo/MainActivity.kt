package com.example.navigationdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            // State to hold the error message
            val errorMessage = rememberSaveable { mutableStateOf("") }

            NavHost(
                navController = navController,
                startDestination = "home_screen"
            ) {
                // Home Screen
                composable("home_screen") {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            try {
                                navController.navigate("details_screen")
                            } catch (e: IllegalArgumentException) {
                                // Update UI
                                errorMessage.value = "Navigation error: ${e.message}"
                                // Log to Logcat
                                Log.e("NavigationError", e.message ?: "Unknown error")
                            }
                        }) {
                            Text("Go to Details")
                        }

                        // Show error message if it exists
                        if (errorMessage.value.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = errorMessage.value,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                // Details Screen
                composable("details_screen/{name}") { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name") ?: "Guest"
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hello, $name!",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { navController.popBackStack() }) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
    }
}