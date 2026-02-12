package com.ui.scannerapp.pages.CheckoutScreen.viewmodel

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import java.io.File

object CameraUtils {
    fun takePicture(
        context: Context,
        cameraController: LifecycleCameraController,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit
    ) {
        // Use cacheDir so the OS can clean up if storage gets low
        val name = "SCAN_${System.currentTimeMillis()}.jpg"
        val photoFile = File(context.cacheDir, name)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Use the URI from the results
                    outputFileResults.savedUri?.let { onImageCaptured(it) }
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }
}