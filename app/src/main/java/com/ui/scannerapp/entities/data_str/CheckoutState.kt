package com.ui.scannerapp.entities.data_str

import com.ui.scannerapp.entities.domain.Checkout

data class CheckoutState(
    val checkout: Checkout,
    val versionNumber: Int
)
