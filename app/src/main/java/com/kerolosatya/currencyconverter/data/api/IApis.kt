package com.kerolosatya.currencyconverter.data.api

import com.kerolosatya.currencyconverter.data.model.CurrencyModel
import com.kerolosatya.currencyconverter.data.util.ConstantLinks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApis {

    @GET(ConstantLinks.LATEST)
    suspend fun getCurrencies(): Response<CurrencyModel>

    @GET("${ConstantLinks.API}/{date}")
    suspend fun getHistorical(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String,
    ): Response<CurrencyModel>

}