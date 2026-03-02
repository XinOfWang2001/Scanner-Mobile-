package com.ui.scannerapp.entities.domain

class Prediction {
    val labelId: Int
    val confidencePercentage: Float
    val predictedProduct: Product?

    constructor(labelId: Int, ConfidencePercentage: Float, predictedProduct: Product? = null) {
        this.labelId = labelId
        this.confidencePercentage = ConfidencePercentage
        this.predictedProduct = predictedProduct
    }
}