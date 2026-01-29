package com.ui.scannerapp.entities.dto

data class PredictionDTO(
    val label_id: Int,
    val confidence_percentage: Float,
    val prediction_label: String
)
