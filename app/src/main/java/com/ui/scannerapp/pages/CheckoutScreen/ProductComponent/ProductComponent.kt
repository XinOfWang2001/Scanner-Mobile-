package com.ui.scannerapp.pages.CheckoutScreen.ProductComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.pages.theme.textPad

@Composable
fun ProductComponent(product: Product, quantity: Int){
    val total = product.price * quantity
    val maxColumns = 5
    Column() {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                text = product.name)
        }
        FlowRow (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            maxItemsInEachRow =  maxColumns
        ) {
            Column(modifier = textPad.weight(0.2f)) {
                Text("Price", color = MaterialTheme.colorScheme.secondary,style = MaterialTheme.typography.titleSmall)
                Text("${product.price}", color = MaterialTheme.colorScheme.secondary,style = MaterialTheme.typography.labelMedium)
            }
            Column(modifier = textPad.weight(0.2f)) {
                Text("Qty",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall)
                Text("$quantity",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium)
            }
            Column(modifier = textPad.weight(0.3f)) {
                Text("($)",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)
                Text("$total",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium)
            }
            Column(modifier = textPad.weight(0.6f)) {
                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                    Button(
                        onClick = {println("Delete")},
                        shape = RoundedCornerShape(4.dp),
                    ) { Text("-") }
                    Button(
                        onClick = {println("Edit")},
                        shape = RoundedCornerShape(4.dp)
                    ) { Text("=") }
                }
            }
        }
    }

}

@Preview
@Composable
fun Demo(){
    ProductComponent(Product(0, "crois_lux", "Croissant luxe", "None", 0.1f), 2)
}
