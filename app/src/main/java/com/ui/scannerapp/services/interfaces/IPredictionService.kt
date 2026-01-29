package com.ui.scannerapp.services.interfaces

import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.entities.dto.PredictionDTO

interface IPredictionService {
    fun PredictBakkery(bread: ByteArray): Prediction;
}