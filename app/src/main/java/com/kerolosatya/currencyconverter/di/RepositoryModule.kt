package com.kerolosatya.currencyconverter.di

import com.kerolosatya.currencyconverter.data.dataSource.historical.HistoricalRemoteDataSource
import com.kerolosatya.currencyconverter.data.dataSource.historical.HistoricalRepositoryImpl
import com.kerolosatya.currencyconverter.data.dataSource.currency.CurrencyRemoteDataSource
import com.kerolosatya.currencyconverter.data.dataSource.currency.CurrencyRepositoryImpl
import com.kerolosatya.currencyconverter.domain.repository.HistoricalRepository
import com.kerolosatya.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyRemoteDataSource: CurrencyRemoteDataSource,
    ): CurrencyRepository {

        return CurrencyRepositoryImpl(
            currencyRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCompetitionDetailsRepository(
        historicalRemoteDataSource: HistoricalRemoteDataSource,
    ): HistoricalRepository {

        return HistoricalRepositoryImpl(
            historicalRemoteDataSource
        )
    }
}