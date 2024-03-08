package com.kerolosatya.currencyconverter.data.dataSource.currency

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import retrofit2.Response

interface CurrencyRemoteDataSource {
    suspend fun getCurrency(): Response<CurrencyModel>
}