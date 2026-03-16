package com.ui.scannerapp.services.implementations.modelservices

import ai.onnxruntime.OrtEnvironment
import com.ui.scannerapp.R
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.services.implementations.RawResourceService
import com.ui.scannerapp.services.interfaces.IPredictionService
import com.ui.scannerapp.services.interfaces.IProductService
import java.io.InputStream

class LocalModelService(val onnxEnvironment: OrtEnvironment,
                        val productService: IProductService,
                        val tensorConverter: TensorConverter,
                        rawResources: RawResourceService
) : IPredictionService {
    val labels = rawResources.loadPredictionLabels(R.raw.model_label)
    val model = rawResources.loadModel()

    override fun predict(bread: InputStream): Prediction {
        this.onnxEnvironment.createSession(model)
        val session = onnxEnvironment.createSession(model)
        val tensor = tensorConverter.convertInputToTensor(bread)
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensor)
        return session.use { it ->
            val outputs = it.run(inputs)
            val outputTensorValue = outputs.first().value
            val scores: FloatArray = tensorConverter.outputTensorValue(outputTensorValue)
            val maxIdx = scores.indices.maxByOrNull { scores[it] } ?: -1
            val predictedProduct = mapToProduct(maxIdx)
            Prediction(maxIdx, 1.0.toFloat(), predictedProduct)
        }
    }

    private fun mapToProduct(labelId: Int): Product {
        val label: String = labels.getValue(labelId)
        return productService.getProductByLabelId(label)!!
    }
}