package com.ui.scannerapp.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.CameraWithCapture
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.VisionFunction
import com.ui.scannerapp.pages.theme.spaceHeightSmall

@Composable
fun HomeView(onNavigateToProfile: () -> Unit) {
    Column {
        Spacer(modifier = spaceHeightSmall)
        Row {
            Text("Friends List")
        }
        Row {
            Button(onClick = { onNavigateToProfile() }) {
                Text("Go to Profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    VisionFunction()
    CameraWithCapture()
    HomeView(onNavigateToProfile = {})
}