package com.ui.scannerapp.pages.CheckoutScreen

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Shop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.pages.theme.textPad
import com.ui.scannerapp.services.implementations.sampleProducts

// MAIN VIEW
@Composable
fun CheckoutScreen(overrideCheckout: Checkout? = null, scanProduct: () -> Unit){
    var checkOut by remember { mutableStateOf(Checkout()) }

    checkOut = overrideCheckout.takeUnless {
        it == null
    } ?: sampleProducts(checkOut)
    Scaffold(
        floatingActionButton = {
            ScannerButton(onClick = scanProduct)
        },
    ) { values ->
        LazyColumn (modifier = Modifier.padding(values.calculateBottomPadding())){
            item {
                CheckoutOverview(checkOut)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    // Dummy values
    CheckoutScreen(null, scanProduct = {})
}

@Composable
fun ScannerButton(onClick: () -> Unit){
    FloatingActionButton(
        onClick = { onClick() },
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
@Composable
fun ProductComponent(product: Product, quantity: Int){
    val total = product.price * quantity
    Row(modifier = textPad) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            text = product.name)
    }
    Row {
        Column(modifier = textPad) {
            Text("Price",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall)
            Text("${product.price}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium)
        }
        Column(modifier = textPad) {
            Text("Quantity",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall)
            Text("$quantity",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium)
        }
        Column(modifier = textPad) {
            Text("Total cost",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall)
            Text("$total",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelMedium)
        }
    }
}