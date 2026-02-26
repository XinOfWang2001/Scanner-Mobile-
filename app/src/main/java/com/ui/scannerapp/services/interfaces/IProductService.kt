package com.ui.scannerapp.services.interfaces

import com.ui.scannerapp.entities.domain.Product

interface IProductService {
    fun getProductByLabelId(id: Int): Product?
}
