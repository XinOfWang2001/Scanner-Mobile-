package com.ui.scannerapp.services.interfaces

import com.ui.scannerapp.entities.domain.Prediction
import java.io.InputStream

interface IPredictionService {
    fun predict(bread: InputStream): Prediction
}