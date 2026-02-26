package com.ui.scannerapp.services.implementations

import com.ui.scannerapp.entities.domain.Prediction
import com.ui.scannerapp.entities.dto.PredictionDTO
import com.ui.scannerapp.services.interfaces.IPredictionService
import java.io.InputStream

class OnlineModelService: IPredictionService {
    override fun predict(bread: InputStream): Prediction {
        TODO("Not yet implemented")
    }
}