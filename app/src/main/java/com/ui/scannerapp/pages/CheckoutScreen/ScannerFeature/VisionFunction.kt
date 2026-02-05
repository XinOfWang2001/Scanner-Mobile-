package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.io.File
import java.util.concurrent.Executor


@Composable
fun VisionFunction() {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        hasCameraPermission = isGranted
    }

    if (hasCameraPermission) {
        CameraWithCapture()
    } else {
        Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
            Text("Request Camera Permission")
        }
    }
}

@Composable
fun CameraWithCapture() {
    val context = LocalContext.current
    val cameraController = remember { LifecycleCameraController(context) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    if (imageUri == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(cameraController, modifier = Modifier.fillMaxSize())
            Button(onClick = {
                takePicture(context, cameraController) { uri ->
                    imageUri = uri
                }
            }) {
                Text("Take Picture")
            }
        }
    } else {
        imageUri?.let { uri ->
            // You can add a composable to display the image here
            Text("Image captured at: $uri")
            Button(onClick = {
                // Stub for processing image with a model
                val result = processImageWithModel(uri, "YourModelName")
                // You can display the result here
            }) {
                Text("Process Image")
            }
        }
    }
}

private fun takePicture(context: Context, cameraController: LifecycleCameraController, onImageCaptured: (Uri) -> Unit) {
    val photoFile = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    val executor: Executor = ContextCompat.getMainExecutor(context)
    // Camera controller
    cameraController.takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            outputFileResults.savedUri?.let { onImageCaptured(it) }
        }
        override fun onError(exception: ImageCaptureException) {
            // Handle error
        }
    })
}

// Stub function for processing the image with a PyTorch model
private fun processImageWithModel(imageUri: Uri, modelName: String): String {
    // In a real implementation, you would load your PyTorch model here
    // and perform inference on the image.
    // For now, this is just a stub.
    return "Processed image with model: $modelName"
}
