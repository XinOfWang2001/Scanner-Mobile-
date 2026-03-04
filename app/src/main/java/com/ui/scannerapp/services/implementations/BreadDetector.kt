package com.ui.scannerapp.services.implementations

import ai.onnxruntime.OrtEnvironment
import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.ui.scannerapp.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BreadDetector(val rawResources: RawResourceService, val productService: ProductService): ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        // Somewhat working prototype
        val localModel = LocalModelService(OrtEnvironment.getEnvironment(), productService, rawResources)
        val inputStream = ByteArrayOutputStream()
        image.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, inputStream)
        val result = localModel.predict(ByteArrayInputStream(inputStream.toByteArray()))
        println("Scan this frame?!! Prediction is ${result.predictedProduct?.name}")
        image.close()
    }

}