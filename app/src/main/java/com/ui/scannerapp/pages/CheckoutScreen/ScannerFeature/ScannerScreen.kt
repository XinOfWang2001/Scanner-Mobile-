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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.ScannerViewModel
import com.ui.scannerapp.pages.theme.horizontalPadding

// Main screen
// TODO: Should have own viewmodel to in order to store state of this screen.
@Composable
fun ScannerScreen(navController: NavHostController, viewModel: ScannerViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.padding(4.dp),
        bottomBar = {
            BottomButtonComponent(navController, viewModel)
        })
    { values ->
        Column(modifier = Modifier
            .padding(10.dp, 10.dp, 10.dp, values.calculateBottomPadding())
        ) {
            ScannerUI(navController, viewModel)
        }
    }
}

@Preview
@Composable
fun PreviewScanner(){
    ScannerScreen(NavHostController(LocalContext.current))
}

@Composable
fun ScannerUI(navController: NavHostController, viewModel: ScannerViewModel){
    Row {
        // Main Camera screen.
        Box{
            VisionFunction(viewModel)
        }
    }
}