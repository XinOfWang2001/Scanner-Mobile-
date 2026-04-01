package com.ui.scannerapp.entities.data_str

import android.net.Uri

data class ScannerUiState(
    val capturedImage: Uri? = null,
    val isProcessing: Boolean = false,
    val infoMessages: String? = null,
    val errorMessage: String? = null,
    val predictions: List<DetectedBox> = emptyList(),
    val detectedBoxes: List<DetectedBox> = emptyList()
)