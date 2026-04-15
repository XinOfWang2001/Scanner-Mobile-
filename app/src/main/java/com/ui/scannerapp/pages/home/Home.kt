package com.ui.scannerapp.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ui.scannerapp.pages.theme.spaceHeightSmall

@Composable
fun HomeView(onNavigateToProfile: () -> Unit) {
    Scaffold { mod ->
        Column(modifier = Modifier.padding(mod.calculateBottomPadding())) {
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

}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    HomeView(onNavigateToProfile = {})
}