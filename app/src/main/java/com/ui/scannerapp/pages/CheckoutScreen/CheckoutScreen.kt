package com.ui.scannerapp.pages.CheckoutScreen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.pages.theme.textPad


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
        }
    ) { values ->
        LazyColumn (modifier = Modifier.padding(0.dp, 0.dp, 0.dp, values.calculateBottomPadding())){
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
    ) {
       Text("Scan bread")
    }
}

fun sampleProducts(checkOut: Checkout): Checkout{
    // Dummy values
    val donut = Product(1, "donut", "Plain donut", "Brood", 0.4f)
    val donut2 = Product(1, "donut", "Plain donut", "Brood", 0.4f)
    val ananas1 = Product(22, "ananas", "anannas", "Brood", 0.4f)
    val apple = Product(23, "appel", "appel", "Brood", 0.4f)
    val peer = Product(24, "peer", "peer", "Brood", 0.4f)
    checkOut.addProduct(donut)
    checkOut.addProduct(donut2)
    checkOut.addProduct(ananas1)
    checkOut.addProduct(apple)
    checkOut.addProduct(peer)
    return  checkOut
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