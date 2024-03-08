package com.kerolosatya.currencyconverter.data.dataSource.currency

import com.kerolosatya.currencyconverter.data.api.IApis
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import retrofit2.Response

class CurrencyRemoteDataSourceImpl(
    private val iApis: IApis
) : CurrencyRemoteDataSource {
    override suspend fun getCurrency(): Response<CurrencyModel> {
        return iApis.getCurrencies()
    }
}

