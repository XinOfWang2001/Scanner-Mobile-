package com.ui.scannerapp.entities.domain

class Prediction {
    val LabelId: Int
    val ConfidencePercentage: Float
    val Label: String

    constructor(LabelId: Int, ConfidencePercentage: Float, Label: String) {
        this.LabelId = LabelId
        this.ConfidencePercentage = ConfidencePercentage
        this.Label = Label
    }
}