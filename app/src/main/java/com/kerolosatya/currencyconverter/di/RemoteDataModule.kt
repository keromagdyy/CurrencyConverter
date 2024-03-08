package com.kerolosatya.currencyconverter.di

import com.kerolosatya.currencyconverter.data.api.IApis
import com.kerolosatya.currencyconverter.data.dataSource.historical.HistoricalRemoteDataSource
import com.kerolosatya.currencyconverter.data.dataSource.historical.HistoricalRemoteDataSourceImpl
import com.kerolosatya.currencyconverter.data.dataSource.currency.CurrencyRemoteDataSource
import com.kerolosatya.currencyconverter.data.dataSource.currency.CurrencyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCurrencyRemoteDataSource(
        aPIService: IApis
    ): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSourceImpl(aPIService)
    }

    @Singleton
    @Provides
    fun provideDetailsRemoteDataSource(
        aPIService: IApis
    ): HistoricalRemoteDataSource {
        return HistoricalRemoteDataSourceImpl(aPIService)
    }
}