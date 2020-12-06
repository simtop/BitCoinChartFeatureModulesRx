package com.simtop.chart.data.network

import com.simtop.chart.data.models.marketprice.MarketPriceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BitCoinService {
    @GET("charts/market-price")
    fun getMarketPrice(
        @Query("timespan") timeSpan: String? = "60days"
    ): Single<MarketPriceResponse>
}