package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.scale
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import androidx.lifecycle.application
import com.ui.scannerapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.FloatBuffer

data class ScannerUiState(
    val capturedImage: Uri? = null,
    val isProcessing: Boolean = false,
    val processingResult: String? = null,
    val errorMessage: String? = null
)

class ScannerViewModel(application: Application) : AndroidViewModel(application) {
    var uiState by mutableStateOf(ScannerUiState())
        private set

    fun onImageCaptured(uri: Uri) {
        uiState = uiState.copy(capturedImage = uri)
    }

    fun onProcessImage(uri: Uri, modelName: String) {
        uiState = uiState.copy(isProcessing = true)
        // Use viewModelScope to process off the main thread
        viewModelScope.launch(Dispatchers.Default) {
            val result = processImageWithModel(uri, modelName)
            uiState = uiState.copy(
                isProcessing = false,
                processingResult = result
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

    // TODO: Move to seperate service class.
    private suspend fun processImageWithModel(imageUri: Uri, modelName: String): String {
        val context = getApplication<Application>().applicationContext
        return withContext(Dispatchers.IO) {
            try {
                // 1. Convert Uri to Bitmap
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // 2. Pre-process for ONNX
                val resizedBitmap = bitmap.scale(224, 224)
                val inputTensor = preprocessONNX(resizedBitmap)

                // 3. Load ONNX model and run inference
                val model = getModel()
                val ortEnvironment = OrtEnvironment.getEnvironment()
                val session = ortEnvironment.createSession(model)

                val inputName = session.inputNames.iterator().next()
                val inputs = mapOf(inputName to inputTensor)
                session.use {
                    val outputs = it.run(inputs)
                    // Assuming the model has a single output of type float array
                    val outputTensorValue = outputs.first().value
                    val scores = if (outputTensorValue.value is Array<*>) {
                        (outputTensorValue.value as Array<FloatArray>)[0]
                    } else {
                        // Handle cases where the output might be a flat FloatArray
                        (outputTensorValue.value as FloatArray)
                    }

                    // 4. Process output
                    val maxIdx = scores.indices.maxByOrNull { scores[it] } ?: -1
                    "Detected Class ID: $maxIdx"
                }
            } catch (e: Exception) {
                "Error: ${e.localizedMessage}"
            }
        }
    }
    private fun getModel(): ByteArray {
        val modelId = R.raw.model
        return this.application.resources.openRawResource(modelId).readBytes()
    }

    private fun preprocessONNX(bitmap: Bitmap): OnnxTensor {
        val width = bitmap.width
        val height = bitmap.height
        val floatBuffer = FloatBuffer.allocate(3 * width * height)

        // Normalization constants (ImageNet)
        val mean = floatArrayOf(0.485f, 0.456f, 0.406f)
        val std = floatArrayOf(0.229f, 0.224f, 0.225f)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = pixels[y * width + x]
                val r = ((pixel shr 16) and 0xFF) / 255.0f
                val g = ((pixel shr 8) and 0xFF) / 255.0f
                val b = (pixel and 0xFF) / 255.0f

                // Planar format for [1, 3, H, W] tensor: RRR...GGG...BBB...
                floatBuffer.put(y * width + x, (r - mean[0]) / std[0])
                floatBuffer.put(height * width + y * width + x, (g - mean[1]) / std[1])
                floatBuffer.put(2 * height * width + y * width + x, (b - mean[2]) / std[2])
            }
        }

        floatBuffer.rewind()

        // Shape for ONNX model: [batch_size, channels, height, width]
        val shape = longArrayOf(1, 3, height.toLong(), width.toLong())
        val env = OrtEnvironment.getEnvironment()
        return OnnxTensor.createTensor(env, floatBuffer, shape)
    }
}
