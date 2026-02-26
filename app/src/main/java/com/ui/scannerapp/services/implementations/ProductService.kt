package com.ui.scannerapp.services.implementations

import com.ui.scannerapp.R
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.services.interfaces.IProductService

class ProductService(rawResources: RawResourceService) : IProductService {

    private val labeledProducts: HashMap<Int, String> = rawResources.loadPredictionLabels(R.raw.model_label)
    private val products: HashMap<String, Product> = HashMap()

    override fun getProductByLabelId(id: Int): Product? {
        val label = labeledProducts[id]
        return products[label]
    }
}
