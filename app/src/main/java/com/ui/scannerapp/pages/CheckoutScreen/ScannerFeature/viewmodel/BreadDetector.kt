package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature.viewmodel

import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.ui.scannerapp.entities.domain.Prediction
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.use

class BreadDetector(val vm: ScannerViewModel): ImageAnalysis.Analyzer {

    private var counter = 1

    override fun analyze(image: ImageProxy) {
        // Somewhat working prototype
        val inputStream = ByteArrayOutputStream()
        image.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, inputStream)
        val result = vm.objectDetector.predict(ByteArrayInputStream(inputStream.toByteArray()))
        // Pass the detected boxes to the UI
        if(interval()){
            counter = 0
            vm.onAnalysis(result)
        }
        counter++
        image.close()
    }

    private fun interval(): Boolean{
        return (counter == 1)
    }
}
