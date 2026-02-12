package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ui.scannerapp.pages.theme.horizontalPadding

// Main screen
@Composable
fun ScannerScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.padding(4.dp),
        bottomBar = {
            BottomButtonComponent(navController)
        })
    { values ->
        Column(modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, values.calculateBottomPadding())
        ) {
            ScannerUI(navController)
        }
    }
}

@Preview
@Composable
fun PreviewScanner(){
    ScannerScreen(NavHostController(LocalContext.current))
}

@Composable
fun ScannerUI(navController: NavHostController){
    Row {
        // Main Camera screen.
        Box{
            VisionFunction()
        }
    }
}

@Composable
fun BottomButtonComponent(navController: NavHostController){
    // Bottom buttons: Basic design. TODO: Add more interactive bottom design.
    Column {
        Row(modifier = Modifier.padding(48.dp),
            verticalAlignment = Alignment.Bottom) {
            Button(
                modifier = horizontalPadding, onClick = {
                navController.popBackStack()
            }) {
                Text("Back")
            }
            Button(modifier = horizontalPadding,onClick = {}) {
                Text("Scan")
            }
            Button(modifier = horizontalPadding,onClick = {}) {
                Text("Next")
            }
        }
    }
}