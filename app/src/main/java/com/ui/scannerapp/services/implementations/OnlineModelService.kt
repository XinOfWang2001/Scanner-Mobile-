package com.ui.scannerapp.services.implementations

import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.entities.dto.PredictionDTO
import com.ui.scannerapp.services.interfaces.IPredictionService

class OnlineModelService: IPredictionService {
    override fun PredictBakkery(bread: ByteArray): Prediction {
        TODO("Not yet implemented")
    }
}