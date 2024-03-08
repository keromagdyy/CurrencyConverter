package com.kerolosatya.currencyconverter.data.model

data class CurrencyModel(
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val base: String? = null,
    val date: String? = null,
    val rates: RatesModel? = null,
)
