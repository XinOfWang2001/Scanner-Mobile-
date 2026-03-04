package com.ui.scannerapp.services.implementations

import ai.onnxruntime.OrtEnvironment
import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.ui.scannerapp.R
import com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel.ScannerViewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BreadDetector(val vm: ScannerViewModel): ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        // Somewhat working prototype
        val inputStream = ByteArrayOutputStream()
        image.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, inputStream)
        val result = vm.predictionService.predict(ByteArrayInputStream(inputStream.toByteArray()))
        println("Scan this frame?!! Prediction is ${result.predictedProduct?.name}")
        // Some callback to the UI
        image.close()
    }

}