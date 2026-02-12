package com.ui.scannerapp.pages.CheckoutScreen.ProductComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.pages.theme.textPad

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