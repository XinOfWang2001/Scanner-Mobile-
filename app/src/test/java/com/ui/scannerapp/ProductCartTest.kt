package com.ui.scannerapp

import com.ui.scannerapp.entities.domain.Checkout
import com.ui.scannerapp.entities.domain.Product
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ProductCartTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_add_product(){
        val donut = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val chocolateDonut = Product(2, "choc_donut", "Chocolate donut", "Brood", 0.59F)
        val frikandelBrood = Product(12, "frk_brd", "Frikandelbrood","Brood",  0.89F)

        val cart = Checkout()

        // Test
        cart.addProduct(donut)
        cart.addProduct(chocolateDonut)
        cart.addProduct(frikandelBrood)
        // validate

        assertEquals(3, cart.cart.size)
        assertEquals(1, cart.cart.get("donut")!!.size)
        assertEquals(1, cart.cart.get("choc_donut")!!.size)
        assertEquals(1, cart.cart.get("frk_brd")!!.size)
    }

    @Test
    fun test_same_list(){
        val donut = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val donut2 = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val cart = Checkout()

        // Test
        cart.addProduct(donut)
        cart.addProduct(donut2)

        // Validate
        assertEquals(1, cart.cart.size)
        assertEquals(2, cart.cart.get("donut")!!.size)
    }

    @Test
    fun test_removal_one_product(){
        val donut = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val donut2 = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val cart = Checkout()
        cart.addProduct(donut)
        cart.addProduct(donut2)

        // Test one removal
        cart.removeProduct(donut)

        assertEquals(1, cart.cart.size)
        assertEquals(1, cart.cart.get("donut")!!.size)
    }
    @Test
    fun test_all_removal_one_product(){
        val donut = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val donut2 = Product(1, "donut", "Plain donut", "Brood", 0.4f)
        val cart = Checkout()
        cart.addProduct(donut)
        cart.addProduct(donut2)

        // Test one removal
        cart.removeProduct(donut)
        cart.removeProduct(donut2)

        assertEquals(0, cart.cart.size)
    }
}