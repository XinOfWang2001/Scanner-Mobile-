package com.ui.scannerapp.pages.CheckoutScreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product

class CheckoutViewmodel: ViewModel() {
    private val _checkout = mutableStateOf(Checkout())
    val checkout: State<Checkout> = _checkout

    fun addProducts(products: List<Product>) {
        products.forEach { _checkout.value.addProduct(it) }
        val updatedCheckout = Checkout().apply {
            this.cart = HashMap(_checkout.value.cart)
        }
        _checkout.value = updatedCheckout
    }
}