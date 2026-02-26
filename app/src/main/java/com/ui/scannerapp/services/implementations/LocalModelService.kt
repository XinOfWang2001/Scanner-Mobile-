package com.ui.scannerapp.services.implementations

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OnnxValue
import ai.onnxruntime.OrtEnvironment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.interfaces.IPredictionService
import java.io.InputStream
import java.nio.FloatBuffer
import kotlin.use

class LocalModelService(localModel: ByteArray, val onnxEnvironment: OrtEnvironment) : IPredictionService {
    val model = localModel

    override fun predict(bread: InputStream): Prediction {
        this.onnxEnvironment.createSession(this.model)
        val session = onnxEnvironment.createSession(model)
        val tensor = convertInputToTensor(bread)
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensor)
        return session.use {
            val outputs = it.run(inputs)
            val outputTensorValue = outputs.first().value
            val scores: FloatArray = outputTensorValue(outputTensorValue)
            val maxIdx = scores.indices.maxByOrNull { scores[it] } ?: -1
            Prediction(maxIdx, 1.0.toFloat(), "Label is $maxIdx")
        }
    }

    private fun convertInputToTensor(bread: InputStream): OnnxTensor
    {
        val bitmap = BitmapFactory.decodeStream(bread)
        val resizedBitmap = bitmap.scale(224, 224)
        return preprocessONNX(resizedBitmap)
    }

    private fun outputTensorValue(onnxValue: OnnxValue): FloatArray {
         if (onnxValue.value is Array<*>) {
             return (onnxValue.value as Array<FloatArray>)[0]
        } else {
            // Handle cases where the output might be a flat FloatArray
            return onnxValue.value as FloatArray
        }

    }

    private fun preprocessONNX(bitmap: Bitmap): OnnxTensor {
        val width = bitmap.width
        val height = bitmap.height
        val floatBuffer = FloatBuffer.allocate(3 * width * height)

        // Normalization constants (ImageNet)
        val mean = floatArrayOf(0.485f, 0.456f, 0.406f)
        val std = floatArrayOf(0.229f, 0.224f, 0.225f)

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = pixels[y * width + x]
                val r = ((pixel shr 16) and 0xFF) / 255.0f
                val g = ((pixel shr 8) and 0xFF) / 255.0f
                val b = (pixel and 0xFF) / 255.0f

                // Planar format for [1, 3, H, W] tensor: RRR...GGG...BBB...
                floatBuffer.put(y * width + x, (r - mean[0]) / std[0])
                floatBuffer.put(height * width + y * width + x, (g - mean[1]) / std[1])
                floatBuffer.put(2 * height * width + y * width + x, (b - mean[2]) / std[2])
            }
        }

        floatBuffer.rewind()

        // Shape for ONNX model: [batch_size, channels, height, width]
        val shape = longArrayOf(1, 3, height.toLong(), width.toLong())
        val env = OrtEnvironment.getEnvironment()
        return OnnxTensor.createTensor(env, floatBuffer, shape)
    }
}