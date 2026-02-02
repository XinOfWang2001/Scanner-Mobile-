package com.ui.scannerapp.entities.domain

import android.os.Build
import androidx.annotation.RequiresApi

class Checkout {
    // TODO: Check if number instead of Mutable list is more efficiënt.
    var cart: HashMap<String, MutableList<Product>> = HashMap<String, MutableList<Product>>()

    fun addProduct(product: Product){
        var productList = cart.get(product.labelId)
        if(productList.isNullOrEmpty()){
            productList = mutableListOf(product)
            cart[product.labelId] = productList;
        }
        else
        {
            productList.add(product)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun removeProduct(product: Product){
        val productList: MutableList<Product>  = cart.get(product.labelId)!!
        if(productList.size > 1){
            productList.removeLast();
        } else{
            // Otherwise remove entire product line from Checkout cart.
            cart.remove(product.labelId)
        }
    }
//    fun CalculateReceipt(){
//    }
}