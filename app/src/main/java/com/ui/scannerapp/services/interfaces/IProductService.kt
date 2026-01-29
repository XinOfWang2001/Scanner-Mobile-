package com.ui.scannerapp.services.interfaces

interface IProductService {
    fun GetByLabel(label: String);
    fun GetById(id: Int);
}