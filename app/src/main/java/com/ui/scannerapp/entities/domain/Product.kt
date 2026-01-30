package com.ui.scannerapp.entities.domain

class Product {
    val id: Int
    val labelId: String
    val name: String
    val price: Float
    val type: String


    constructor(id: Int, labelId: String, name: String, type: String, price: Float) {
        this.id = id
        this.labelId = labelId
        this.name = name
        this.type = type
        this.price = price
    }
}