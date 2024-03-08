package com.kerolosatya.currencyconverter.domain.useCase

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.domain.repository.CurrencyRepository

class CurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(): CurrencyModel {
        return currencyRepository.getCurrency()
    }
}