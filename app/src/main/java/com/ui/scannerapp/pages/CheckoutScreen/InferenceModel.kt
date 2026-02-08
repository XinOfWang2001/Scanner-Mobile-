package com.ui.scannerapp.pages.CheckoutScreen

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageInferenceService{
    fun inferFromUri(context: Context, uri: Uri, onResult: (String) -> Unit) {
        val inputImage = InputImage.fromFilePath(context, uri)
        val model = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        model.process(inputImage)
            .addOnSuccessListener { visionText ->
                onResult(visionText.text)
            }.addOnFailureListener { exception ->
                onResult(exception.toString())
            }
    }
}