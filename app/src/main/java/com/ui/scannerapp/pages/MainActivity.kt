package com.ui.scannerapp.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ui.scannerapp.pages.home.HomeView
import com.ui.scannerapp.pages.CheckoutScreen.CheckoutScreen
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.ScannerScreen
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { SpecificTopBar(currentRoute) }
        // Optional: Add a TopBar or BottomBar here
    ) { innerPadding ->
        // 4. Pass the controller into your NavHost
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") {
                HomeView(onNavigateToProfile = { navController.navigate("Overview") })
            }
            composable("Overview") {
                // TODO: TEMP Solution, decouple this logic by passing NavHostController
                CheckoutScreen(null, scanProduct = {
                    navController.navigate("Bread-scanner")

                })
            }
            composable("Bread-scanner") {
                ScannerScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecificTopBar(currentRoute: String?) {
    val title: String = currentRoute.orEmpty()
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}
