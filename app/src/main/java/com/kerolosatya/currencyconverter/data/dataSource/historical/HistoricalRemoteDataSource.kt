package com.kerolosatya.currencyconverter.data.dataSource.historical

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import retrofit2.Response

interface HistoricalRemoteDataSource {
    suspend fun getHistorical(
        date: String,
        base: String,
        symbols: String,
    ): Response<CurrencyModel>
}