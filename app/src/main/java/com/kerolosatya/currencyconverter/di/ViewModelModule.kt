package com.kerolosatya.currencyconverter.di

import android.app.Application
import com.kerolosatya.currencyconverter.domain.useCase.CurrencyUseCase
import com.kerolosatya.currencyconverter.ui.home.currency.CurrencyViewModel
import com.kerolosatya.currencyconverter.domain.useCase.HistoricalUseCase
import com.kerolosatya.currencyconverter.ui.home.details.HistoricalViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    fun provideCurrencyViewModel(app: Application, currencyUseCase: CurrencyUseCase): CurrencyViewModel {
        return CurrencyViewModel(app,currencyUseCase)
    }

    @Provides
    fun provideHistoricalViewModel(app: Application, historicalUseCase: HistoricalUseCase): HistoricalViewModel {
        return HistoricalViewModel(app,historicalUseCase)
    }

}