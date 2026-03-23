package com.ui.scannerapp.entities.data_str

import android.graphics.Bitmap
import com.ui.scannerapp.entities.domain.Prediction
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DetectedBox(val x: Int, val y: Int, val width: Int, val height: Int){

    private var prediction: Prediction? = null

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

    fun setPrediction(prediction: Prediction){
        this.prediction = prediction
    }

    fun getPrediction(): Prediction{
        if (this.prediction == null)
            throw Exception("Prediction is null")
        return this.prediction!!
    }
}