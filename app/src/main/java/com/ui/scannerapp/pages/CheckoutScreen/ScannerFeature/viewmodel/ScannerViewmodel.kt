package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel
import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.pytorch.LiteModuleLoader
import org.pytorch.torchvision.TensorImageUtils
import androidx.core.graphics.scale
import androidx.lifecycle.AndroidViewModel
import org.pytorch.IValue
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
        // In a real implementation, you would load your PyTorch model here
        // and perform inference on the image.
        val context = getApplication<Application>().applicationContext
        // For now, this is just a stub.
        return withContext(Dispatchers.IO) {
            try {
                // 1. Load the Model (Should be in your assets folder)
                val modelPath: String = assetFilePath(context, modelName)
                val module = LiteModuleLoader.load(modelPath)

                // 2. Convert Uri to Bitmap
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // 3. Pre-process (Resizing and Normalizing)
                // Note: Standard PyTorch models usually expect 224x224
                val resizedBitmap = bitmap.scale(224, 224)
                val inputTensor = TensorImageUtils.bitmapToFloat32Tensor(
                    resizedBitmap,
                    TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
                    TensorImageUtils.TORCHVISION_NORM_STD_RGB
                )

                // 4. Run Inference
                val outputTensor = module.forward(IValue.from(inputTensor)).toTensor()
                val scores = outputTensor.dataAsFloatArray

                // 5. Find the index with the highest score
                val maxIdx = scores.indices.maxByOrNull { scores[it] } ?: -1
                "Detected Class ID: $maxIdx"

            } catch (e: Exception) {
                "Error: ${e.localizedMessage}"
            }
        }
    }

    @Throws(IOException::class)
    fun assetFilePath(context: Context, assetName: String): String {
        val file = File(context.filesDir, assetName)

        // If the file already exists, just return the path
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }

        // Otherwise, copy the asset to internal storage
        context.assets.open(assetName).use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
            }
        }
        return file.absolutePath
    }
}