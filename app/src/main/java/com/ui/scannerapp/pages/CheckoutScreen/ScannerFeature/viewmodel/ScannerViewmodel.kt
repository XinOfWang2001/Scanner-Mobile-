package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel

import ai.onnxruntime.OrtEnvironment
import android.app.Application
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.ui.scannerapp.entities.data_str.ScannerUiState
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.implementations.modelservices.LocalModelService
import com.ui.scannerapp.services.implementations.ProductService
import com.ui.scannerapp.services.implementations.RawResourceService
import com.ui.scannerapp.services.implementations.modelservices.ObjectDetectionService
import com.ui.scannerapp.services.implementations.modelservices.TensorConverter
import com.ui.scannerapp.services.interfaces.IPredictionService
import com.ui.scannerapp.entities.data_str.DetectedBox
import kotlinx.coroutines.withTimeout
import kotlin.concurrent.timer


class ScannerViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(ScannerUiState())
        private set

    // Perhaps need to be refactored with dependency injection
    private val onnxEnvironment: OrtEnvironment = OrtEnvironment.getEnvironment()
    private val tensorConverter:TensorConverter = TensorConverter(224,224)
    private val yoloTensorConverter: TensorConverter = TensorConverter(640, 640)
    private val rawResourceService: RawResourceService = RawResourceService(application.applicationContext)
    val predictionService: IPredictionService = LocalModelService(
        onnxEnvironment,
        ProductService(),
        tensorConverter,
        rawResourceService)
    val objectDetector: ObjectDetectionService = ObjectDetectionService(onnxEnvironment, rawResourceService, yoloTensorConverter, predictionService)

    fun onImageCaptured(uri: Uri) {
        // Ensure the new state is captured before processing.
        uiState = uiState.copy(capturedImage = uri, isProcessing = true)
        viewModelScope.launch(Dispatchers.Default) {
            uiState = uiState.copy(isProcessing = false, predictions = processImageWithModel(uri))
        }
    }

    fun onAnalysis(detectedBoxes: List<DetectedBox>){
        // Update the UI state, so that objects are detected.
        uiState = uiState.copy(detectedBoxes = detectedBoxes)
    }

    fun onRetake() {
        uiState = ScannerUiState() // Reset state
    }

    fun onCaptureError(exception: ImageCaptureException) {
        uiState = uiState.copy(errorMessage = "Capture failed: ${exception.message}")
    }

    private suspend fun processImageWithModel(imageUri: Uri): List<DetectedBox> {
        val context = getApplication<Application>().applicationContext
        return withContext(Dispatchers.IO) {
            try {
                // 1. Convert Uri to Bitmap
                val inputStream = context.contentResolver.openInputStream(imageUri)
                    ?: return@withContext mutableListOf()

                return@withContext objectDetector.predict(inputStream)
            } catch (e: Exception) {
                return@withContext mutableListOf()
            }
        }
    }
}
