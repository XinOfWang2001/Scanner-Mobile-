package com.ui.scannerapp.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ui.scannerapp.pages.example.Message
import com.ui.scannerapp.pages.example.MessageCard
import com.ui.scannerapp.pages.home.HomeView
import com.ui.scannerapp.pages.scanner.ScannerView
import com.ui.scannerapp.pages.theme.ScannerAppTheme

// Home page
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScannerAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainEntryPoint()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        println("App is on BACK.")
    }

    override fun onPause() {
        super.onPause()
        println("App is on paused. Be right back.")
    }
}
@Composable
fun MainEntryPoint(){
    val navController = rememberNavController()
    Scaffold(
        // Optional: Add a TopBar or BottomBar here
    ) { innerPadding ->
        // 4. Pass the controller into your NavHost
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeView(onNavigateToProfile = { navController.navigate("detail") })
            }
            composable("detail") {
                ScannerView()
            }
        }
    }
}

