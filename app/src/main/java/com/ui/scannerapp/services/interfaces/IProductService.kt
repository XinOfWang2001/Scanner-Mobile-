package com.ui.scannerapp.services.interfaces

import com.ui.scannerapp.entities.domain.Product

interface IProductService {
    fun getProductByLabelId(labelId: String): Product?
}
