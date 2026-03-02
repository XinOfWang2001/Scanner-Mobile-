package com.ui.scannerapp.services.implementations

import com.ui.scannerapp.R
import com.ui.scannerapp.entities.domain.Product
import com.ui.scannerapp.services.interfaces.IProductService

class ProductService : IProductService {
    private val products: HashMap<String, Product> = HashMap(3)

    constructor(){
        products.put("crois_lux", Product(0, "crois_lux", "Croissant luxe", "None", 0.1f))
        products.put("fri_brd", Product(1, "fri_brd", "Frikandelbroodje", "None", 0.1f))
        products.put("koff_brd", Product(2, "koff_brd", "Koffiebroodje", "None", 0.1f))
    }

    override fun getProductByLabelId(labelId: String): Product {
        return products.getValue(labelId)
    }

}
