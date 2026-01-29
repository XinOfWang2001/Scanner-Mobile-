package com.ui.scannerapp.viewmodels

class ScannerViewModel {
    /*
    Responsibilities:
    * Open camera
    * Send prediction to prediction service
    - If API-returns an error. Show error message: It is not available
    - If Error occurs due to system mistakes, return system error.
    - Otherwise return predictions and confirmation
    * Able to correct mistakes made by model.

    Needed services:
    - ProductServices: To map prediction to the right product.
    - PredictionService: Both Online and Offline model.
    */

    fun OpenCamera(){
        println("Open Camera");
    }

    fun DetectProducts(){
        // Check which model is needed for each detected product
    }

    fun GeneratePrediction(){
        println("Predict this product");
    }

    fun ChangeProducts(index: Int){
        println("Product on $index")
    }

    fun Confirm(){
        println("Products have been scanned. Return to checkout screen.")
    }
}