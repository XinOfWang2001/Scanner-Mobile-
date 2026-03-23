package com.ui.scannerapp.pages.CheckoutScreen.ScannerFeature

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import com.ui.scannerapp.entities.data_str.DetectedBox
import kotlin.collections.forEach
import androidx.compose.ui.geometry.Size as ComposeSize

@Composable
fun BoundingBoxOverlay(detectedBoxes: List<DetectedBox>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Note: Coordinates from YOLO are relative to the 640x640 input.
        val scaleX = size.width / 640f
        val scaleY = size.height / 640f

        val textPaint = Paint().apply {
            color = android.graphics.Color.RED
            textSize = 48f
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
        }

        detectedBoxes.forEach { box ->
            val rectX = box.x * scaleX
            val rectY = box.y * scaleY
            val rectWidth = box.width * scaleX
            val rectHeight = box.height * scaleY

            // Draw the bounding box
            drawRect(
                color = Color.Red,
                topLeft = Offset(rectX, rectY),
                size = ComposeSize(rectWidth, rectHeight),
                style = Stroke(width = 4f)
            )
            val label = box.getPrediction().predictedProduct?.name ?: "Unknown"
            drawContext.canvas.nativeCanvas.drawText(
                label,
                rectX,
                rectY - 15f, // Position text slightly above the box
                textPaint
            )
        }
    }
}