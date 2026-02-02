package com.ui.scannerapp.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeView(onNavigateToProfile: () -> Unit) {
    Column() {
        Spacer(modifier = Modifier
            .padding(horizontal = 1.2.dp, vertical = 2.dp))
        Row() {
            Text("Friends List")
        }
        Row() {
            Button(onClick = { onNavigateToProfile() }) {
                Text("Go to Profile")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(onNavigateToProfile = {})
}