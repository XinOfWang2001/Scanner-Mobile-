package com.ui.scannerapp.services.implementations

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class BreadDetector(val rawResources: RawResourceService, val productService: ProductService): ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        TODO("Not yet implemented")
    }

}