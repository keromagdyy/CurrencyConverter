package com.kerolosatya.currencyconverter.data.dataSource.historical

import com.kerolosatya.currencyconverter.data.api.IApis
import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import retrofit2.Response

class HistoricalRemoteDataSourceImpl(
    private val iApis: IApis
) : HistoricalRemoteDataSource {
    override suspend fun getHistorical(
        date: String,
        base: String,
        symbols: String,
    ): Response<CurrencyModel> {
        return iApis.getHistorical(
            date,
            base,
            symbols,
        )
    }
}

