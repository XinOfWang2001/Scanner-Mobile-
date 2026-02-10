package com.ui.scannerapp.services.implementations

import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product

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