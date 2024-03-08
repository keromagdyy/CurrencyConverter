package com.kerolosatya.currencyconverter.domain.repository

import com.kerolosatya.currencyconverter.data.model.CurrencyModel


interface CurrencyRepository {
    suspend fun getCurrency(): CurrencyModel
}
