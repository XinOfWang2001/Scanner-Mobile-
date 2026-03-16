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
    // Should return smaller pieces, usable for prediction purposes.

    // Other data type is needed, cannot inherit from IPredictionService.
    fun predict(rawImage: InputStream): Prediction {
        val model = rawResourceService.loadYoloModel()
        val session = onnxEnvironment.createSession(model)
        val tensorInput = tensorConverter.convertInputToTensor(rawImage)
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensorInput)
        return session.use {
            val outputs = it.run(inputs)
            val outputTensorValue = outputs.first().value
            val scores: FloatArray = tensorConverter.outputTensorValue(outputTensorValue)
            val maxIdx = scores.indices.maxByOrNull { scores[it] } ?: -1
            // Should split images into smaller input streams usable for the other model.

            // Placeholder value
            Prediction(maxIdx, 1.0.toFloat(), null)
        }
    }

}