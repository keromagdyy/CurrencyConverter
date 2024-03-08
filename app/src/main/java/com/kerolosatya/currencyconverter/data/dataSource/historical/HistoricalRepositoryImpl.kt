package com.kerolosatya.currencyconverter.data.dataSource.historical

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.domain.repository.HistoricalRepository

class HistoricalRepositoryImpl(
    private val historicalRemoteDataSource: HistoricalRemoteDataSource,
) : HistoricalRepository {

    override suspend fun getHistorical(
        date: String,
        base: String,
        symbols: String,
    ): CurrencyModel {
        val competitionDetailModel = CurrencyModel()
        if (historicalRemoteDataSource.getHistorical(date, base, symbols).isSuccessful
        ) {
            val body = historicalRemoteDataSource.getHistorical(date, base, symbols).body()
            if (body != null) {
                return body
            }
        }
        return competitionDetailModel
    }
}