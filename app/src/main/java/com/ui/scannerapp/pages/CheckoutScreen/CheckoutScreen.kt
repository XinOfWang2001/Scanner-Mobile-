package com.ui.scannerapp.pages.CheckoutScreen

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.pages.CheckoutScreen.ProductComponent.ProductComponent
import com.ui.scannerapp.pages.CheckoutScreen.viewmodel.CheckoutViewmodel
import kotlin.collections.emptyList

// MAIN VIEW
@Composable
fun CheckoutScreen(overrideCheckout: Checkout? = null, navController: NavHostController, viewModel: CheckoutViewmodel = viewModel()){
    val checkOut by viewModel.checkout

    val navBackStackEntry = navController.currentBackStackEntry
    val result = navBackStackEntry?.savedStateHandle
        ?.getStateFlow<List<Product>>("products", emptyList())?.collectAsState()
    LaunchedEffect(result) {
        viewModel.addProducts(result!!.value)
        navBackStackEntry.savedStateHandle.remove<List<Product>>("products")
    }
    Scaffold(
        floatingActionButton = {
            ScannerButton(navController)
        },
    ) { values ->
        LazyColumn (modifier = Modifier.padding(values.calculateBottomPadding())){
            item {
                Text("New changes made with ${result!!.value.size}")
                CheckoutOverview(checkOut)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    // Dummy values
    CheckoutScreen(null, NavHostController(LocalContext.current))
}

@Composable
fun ScannerButton(navController: NavHostController){
    FloatingActionButton(
        onClick = { navController.navigate("Bread-scanner") },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add",
            modifier = Modifier.padding(5.dp))
    }
}

@Composable
fun CheckoutOverview(checkOut: Checkout){
    checkOut.cart.forEach { (_, value) ->
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Column {
                ProductComponent(value[0], value.size)
            }
        }
    }
}