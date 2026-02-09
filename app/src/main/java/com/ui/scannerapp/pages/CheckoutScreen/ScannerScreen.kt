package com.ui.scannerapp.pages.CheckoutScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ScannerScreen(navController: NavHostController) {
    Column {
        Text("Hello scanner!")

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back")
        }
    }
}