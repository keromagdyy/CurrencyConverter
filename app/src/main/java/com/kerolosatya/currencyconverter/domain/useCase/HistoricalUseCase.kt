package com.kerolosatya.currencyconverter.domain.useCase

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.domain.repository.HistoricalRepository

class HistoricalUseCase(private val historicalRepository: HistoricalRepository) {
    suspend fun execute(
        date: String,
        base: String,
        symbols: String,
    ): CurrencyModel {
        return historicalRepository.getHistorical(date, base, symbols)
    }
}