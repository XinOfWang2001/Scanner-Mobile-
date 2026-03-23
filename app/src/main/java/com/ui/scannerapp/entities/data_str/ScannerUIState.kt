package com.ui.scannerapp.entities.data_str

import android.net.Uri

data class ScannerUiState(
    val capturedImage: Uri? = null,
    val isProcessing: Boolean = false,
    val processingResult: String? = null,
    val errorMessage: String? = null,
    val detectedBoxes: List<DetectedBox> = emptyList()
)