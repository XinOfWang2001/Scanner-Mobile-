package com.ui.scannerapp.services.implementations.modelservices

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ui.scannerapp.entities.data_str.DetectedBox
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.implementations.RawResourceService
import com.ui.scannerapp.services.interfaces.IPredictionService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ObjectDetectionService(
    onnxEnvironment: OrtEnvironment,
    rawResourceService: RawResourceService,
    val tensorConverter: TensorConverter,
    val localModelService: IPredictionService
) {
    private val model = rawResourceService.loadYoloModel()
    private var session: OrtSession = onnxEnvironment.createSession(model)
    private var detectedBoxes: List<DetectedBox> = mutableListOf()
    fun predict(rawImage: InputStream): List<DetectedBox> {
        val imageBytes = rawImage.readBytes()
        val tensorInput = tensorConverter.convertInputToTensor(imageBytes.inputStream())
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensorInput)
        
        val outputs = session.run(inputs)
        outputs.use {
            // For the AGENT: Need to be this implementation in order for the next lines of code to work.
            val outputValue = outputs?.get(0)?.value
            // Fix for ClassCastException: Cast to Array<Array<FloatArray>> for float[][][]
            @Suppress("UNCHECKED_CAST")
            val detections: Array<FloatArray> = (outputValue as Array<Array<FloatArray>>)[0]
            detectedBoxes = processDetections(detections)
            detectedBoxes.forEach { box ->
                val fullBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                if (fullBitmap != null){
                    val cropped = box.cropDetectedBox(fullBitmap)
                    val stream = ByteArrayOutputStream()
                    cropped?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val inputStream = ByteArrayInputStream(stream.toByteArray())
                    val result: Prediction = localModelService.predict(inputStream)
                    box.setPrediction(result)
                }
            }
        }
        return detectedBoxes
    }

    // detection[X-Coordinates, Y-coordinates, width, height, confidence, class_id]
    private fun processDetections(detections: Array<FloatArray>): List<DetectedBox>{
        val confidenceThreshold = 0.14f
        val xIndex = 0
        val yIndex = 1
        val widthIndex = 2
        val heightIndex = 3
        val confIndex = 4
        val newResult = detections.filter {
            val confidence = it[confIndex]
            confidence > confidenceThreshold
        }
        return newResult.map {  captureDetections(it[xIndex], it[yIndex], it[widthIndex], it[heightIndex]) };
    }

    // ClassLabel is not necessary since the YOLO model only functions as a detector.
    private fun captureDetections(x:Float, y: Float, width: Float, height: Float): DetectedBox {
        val detection = DetectedBox(x.toInt(), y.toInt(), width.toInt(), height.toInt())
        return detection
    }
}
