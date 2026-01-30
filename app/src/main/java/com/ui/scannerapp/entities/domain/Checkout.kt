package com.ui.scannerapp.entities.domain

import android.os.Build
import androidx.annotation.RequiresApi

class Checkout {
    val Cart: HashMap<String, MutableList<Product>>
        get() {
            TODO()
        }
    fun AddToCart(product: Product){
        var productList = Cart.get(product.LabelId)
        if(productList.isNullOrEmpty()){
            productList = MutableList(1,  { product });
        }
        productList.add(product);
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun RemoveProduct(product: Product){
        val productList: MutableList<Product>  = Cart.get(product.LabelId)!!
        if(productList.size > 1){
            productList.removeLast();
        } else{
            // Otherwise remove entire product line from Checkout cart.
            Cart.remove(product.LabelId)
        }
    }

    fun CalculateReceipt(){
    }
}