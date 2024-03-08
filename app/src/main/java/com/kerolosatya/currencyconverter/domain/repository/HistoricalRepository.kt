package com.kerolosatya.currencyconverter.domain.repository

import com.kerolosatya.currencyconverter.data.model.CurrencyModel

interface HistoricalRepository {
    suspend fun getHistorical(
        date: String,
        base: String,
        symbols: String,
    ): CurrencyModel
}
