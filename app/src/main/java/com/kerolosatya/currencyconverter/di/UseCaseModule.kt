package com.kerolosatya.currencyconverter.di

import com.kerolosatya.currencyconverter.domain.repository.HistoricalRepository
import com.kerolosatya.currencyconverter.domain.repository.CurrencyRepository
import com.kerolosatya.currencyconverter.domain.useCase.CurrencyUseCase
import com.kerolosatya.currencyconverter.domain.useCase.HistoricalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideGetCurrencyUseCase(currencyRepository: CurrencyRepository): CurrencyUseCase {
        return CurrencyUseCase(currencyRepository)
    }

    @Provides
    fun provideGetHistoricalUseCase(historicalRepository: HistoricalRepository): HistoricalUseCase {
        return HistoricalUseCase(historicalRepository)
    }

}