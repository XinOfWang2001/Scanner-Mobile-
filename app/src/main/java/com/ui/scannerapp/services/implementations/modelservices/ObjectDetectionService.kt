package com.ui.scannerapp.services.implementations.modelservices

import ai.onnxruntime.OrtEnvironment
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.services.implementations.RawResourceService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DetectedBoxes(val x: Int, val y: Int, val width: Int, val height: Int){
    fun cropDetectedBox(source: Bitmap): Bitmap? {
        return try {
            // Ensure coordinates are within bitmap bounds to avoid crashes
            val x = this.x.coerceIn(0, source.width - 1)
            val y = this.y.coerceIn(0, source.height - 1)
            val width = this.width.coerceAtMost(source.width - x)
            val height = this.height.coerceAtMost(source.height - y)

            if (width > 0 && height > 0) {
                Bitmap.createBitmap(source, x, y, width, height)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
class ObjectDetectionService(
    val onnxEnvironment: OrtEnvironment,
    val rawResourceService: RawResourceService,
    val tensorConverter: TensorConverter
) {

    fun predict(rawImage: InputStream): Prediction {
        val imageBytes = rawImage.readBytes()
        val model = rawResourceService.loadYoloModel()
        val session = onnxEnvironment.createSession(model)
        val tensorInput = tensorConverter.convertInputToTensor(imageBytes.inputStream())
        val inputName = session.inputNames.iterator().next()
        val inputs = mapOf(inputName to tensorInput)
        session.use {
            val outputs = it.run(inputs)
            outputs.use {
                // For the AGENT: Need to be this implementation in order for the next lines of code to work.
                val outputValue = outputs?.get(0)?.value
                // Fix for ClassCastException: Cast to Array<Array<FloatArray>> for float[][][]
                @Suppress("UNCHECKED_CAST")
                val detections: Array<FloatArray> = (outputValue as Array<Array<FloatArray>>)[0]
                // detection[X-Coordinates, Y-coordinates, width, height, confidence, class_id]
                // Convert these data to int and capture pieces of the image.
                val boxes = processDetections(detections)
                boxes.forEach { box ->

                    val fullBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    if (fullBitmap != null){
                        val cropped = box.cropDetectedBox(fullBitmap)
                        println("Detected box at : ${box.x} and ${box.y}")
                        val stream = ByteArrayOutputStream()
                        cropped?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val inputStream = ByteArrayInputStream(stream.toByteArray())
                        println("Processed ${detections.size} potential detections")
                    }
                }
                // Return predictions.
                // TODO: Process detections (e.g., Non-Max Suppression)
                println("Processed ${detections.size} potential detections")
            }
        }
        return Prediction(1, 1.0f, null)
    }

    private fun processDetections(detections: Array<FloatArray>): List<DetectedBoxes>{
        val confidenceThreshold = 0.2f
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
    private fun cropDetectedBox(source: Bitmap, box: DetectedBoxes): Bitmap? {
        return try {
            // Ensure coordinates are within bitmap bounds to avoid crashes
            val x = box.x.coerceIn(0, source.width - 1)
            val y = box.y.coerceIn(0, source.height - 1)
            val width = box.width.coerceAtMost(source.width - x)
            val height = box.height.coerceAtMost(source.height - y)

            if (width > 0 && height > 0) {
                Bitmap.createBitmap(source, x, y, width, height)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ClassLabel is not necessary since the YOLO model only functions as a detector.
    private fun captureDetections(x:Float, y: Float, width: Float, height: Float): DetectedBoxes{
        val detection = DetectedBoxes(x.toInt(), y.toInt(), width.toInt(), height.toInt())
        return detection
    }
}
