package com.ui.scannerapp.services.implementations.modelservices

import ai.onnxruntime.OrtEnvironment
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.implementations.RawResourceService
import java.io.InputStream

class ObjectDetectionService(val onnxEnvironment: OrtEnvironment,val rawResourceService: RawResourceService, val tensorConverter: TensorConverter) {
    // Should return smaller pieces, usable for prediction purposes.
    fun predict(rawImage: InputStream): Prediction {
        val model = rawResourceService.loadYoloModel()
        val session = onnxEnvironment.createSession(model)
        val tensorInput = tensorConverter.convertInputToTensor(rawImage)
        TODO("Not yet implemented")
    }
}