package com.ui.scannerapp.services.implementations.modelservices

import ai.onnxruntime.OrtEnvironment
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.services.implementations.RawResourceService
import java.io.InputStream
import kotlin.run

class ObjectDetectionService(
    val onnxEnvironment: OrtEnvironment,
    val rawResourceService: RawResourceService,
    val tensorConverter: TensorConverter) {

    // Other data type is needed, cannot inherit from IPredictionService.
    fun predict(rawImage: InputStream): Prediction {
        val model = rawResourceService.loadYoloModel()
        val session = onnxEnvironment.createSession(model)
        val tensorInput = tensorConverter.convertInputToTensor(rawImage)
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensorInput)
        // A two-step model usage is in place
        var result = session.run(inputs)
        return Prediction(1, 1.0.toFloat(), null)
    }

}