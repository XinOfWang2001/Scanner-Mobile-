package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.CameraUtils.takePicture
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.ScannerViewModel


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
fun CameraWithCapture(viewModel: ScannerViewModel = viewModel()) {
    val context = LocalContext.current
    val state = viewModel.uiState
    val cameraController = remember { LifecycleCameraController(context) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.capturedImage == null) {
            // SHOW LIVE PREVIEW
            CameraPreview(cameraController, Modifier.fillMaxSize())
            Button(
                onClick = { takePicture(
                    context, cameraController, viewModel::onImageCaptured,
                    onError = viewModel::onCaptureError
                ) },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("Take Picture")
            }
        } else {
            // SHOW CAPTURED IMAGE & RESULTS
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Review Photo", style = MaterialTheme.typography.headlineMedium)

                if (state.isProcessing) {
                    CircularProgressIndicator()
                } else {
                    Text(state.processingResult ?: "Ready to process")
                    Button(onClick = { viewModel.onProcessImage(state.capturedImage, "bakkery_model_v1.pth") }) {
                        Text("Analyze Product")
                    }
                    Button(onClick = { viewModel.onRetake() }) {
                        Text("Retake")
                    }
                }
            }
        }
    }
}
