package com.kerolosatya.currencyconverter.data.dataSource.currency

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
) : CurrencyRepository {

    override suspend fun getCurrency(): CurrencyModel {
        val currencyModel = CurrencyModel()

        if (currencyRemoteDataSource.getCurrency().isSuccessful) {
            val body = currencyRemoteDataSource.getCurrency().body()
            if (body != null) {
                return body
            }
        }
        return currencyModel
    }
}