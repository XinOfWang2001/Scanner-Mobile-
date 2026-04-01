package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavHostController
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.CameraUtils.takePicture
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.ScannerViewModel
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.BreadDetector
import com.ui.scannerapp.pages.theme.horizontalPadding


@Composable
fun VisionFunction(viewModel: ScannerViewModel) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
        hasCameraPermission = isGranted
    }

    if (hasCameraPermission) {
        CameraWithCapture(viewModel)
    } else {
        Button(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
            Text("Request Camera Permission")
        }
    }
}

@Composable
fun CameraWithCapture(viewModel: ScannerViewModel) {
    val context = LocalContext.current
    val state = viewModel.uiState
    val cameraController = remember { LifecycleCameraController(context) }
    val breadDetector = remember { BreadDetector(viewModel) }

    // For prototype sake still in this function.
    LaunchedEffect(cameraController) {
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context),
            breadDetector
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.capturedImage == null) {
            // SHOW LIVE PREVIEW
            CameraPreview(cameraController, Modifier.fillMaxSize())
            
            // Draw Bounding Boxes Overlay
            BoundingBoxOverlay(state.detectedBoxes)

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
//                Text("Review Photo", style = MaterialTheme.typography.headlineMedium)
                if (state.isProcessing) {
                    CircularProgressIndicator()
                } else {
                    Text("Amount of products detected: ${state.detectedBoxes.size}")
                    // Show all the predictions
                    for (prediction in state.detectedBoxes) {
                        Text(prediction.getPrediction().predictedProduct?.name ?: "Unknown")
                    }
                    Button(onClick = { viewModel.onRetake() }) {
                        Text("Retake")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomButtonComponent(navController: NavHostController, viewModel: ScannerViewModel){
    // Bottom buttons: Basic design. TODO: Add more interactive bottom design.
    Column {
        Row(modifier = Modifier.padding(48.dp),
            verticalAlignment = Alignment.Bottom) {
            Button(
                modifier = horizontalPadding, onClick = {
                    navController.popBackStack()
                }) {
                Text("Back")
            }
            Button(modifier = horizontalPadding,onClick = {
                // Persist state of predictions to previous screen.
            }) {
                Text("Confirm")
            }
        }
    }
}