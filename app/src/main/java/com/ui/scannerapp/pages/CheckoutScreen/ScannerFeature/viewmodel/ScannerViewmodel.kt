package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel

import ai.onnxruntime.OrtEnvironment
import android.app.Application
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.ui.scannerapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.ui.scannerapp.entities.data_str.ScannerUiState
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.implementations.LocalModelService
import com.ui.scannerapp.services.implementations.ProductService
import com.ui.scannerapp.services.implementations.RawResourceService
import com.ui.scannerapp.services.interfaces.IPredictionService


class ScannerViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(ScannerUiState())
        private set

    val predictionService: IPredictionService = LocalModelService(
        this.application.resources.openRawResource(R.raw.model).readBytes(),
        OrtEnvironment.getEnvironment(),
        ProductService(RawResourceService(application.applicationContext)))

    fun onImageCaptured(uri: Uri) {
        uiState = uiState.copy(capturedImage = uri)
    }

    fun onProcessImage(uri: Uri) {
        uiState = uiState.copy(isProcessing = true)
        // Use viewModelScope to process off the main thread
        viewModelScope.launch(Dispatchers.Default) {
            val result = processImageWithModel(uri)
            uiState = uiState.copy(
                isProcessing = false,
                processingResult = result.Label
            )
        }
    }

    fun onRetake() {
        println("Time to retake the picture.")
        uiState = ScannerUiState() // Reset state
    }

    fun onCaptureError(exception: ImageCaptureException) {
        uiState = uiState.copy(errorMessage = "Capture failed: ${exception.message}")
    }
    private suspend fun processImageWithModel(imageUri: Uri): Prediction {
        val context = getApplication<Application>().applicationContext
        return withContext(Dispatchers.IO) {
            try {
                // 1. Convert Uri to Bitmap
                val inputStream = context.contentResolver.openInputStream(imageUri)
                    ?: return@withContext Prediction(0, 0.toFloat(), "No Prediction made")

                return@withContext predictionService.predict(inputStream);
            } catch (e: Exception) {
                return@withContext Prediction(0, 0.toFloat(), "No Prediction made")
            }
        }
    }
}
