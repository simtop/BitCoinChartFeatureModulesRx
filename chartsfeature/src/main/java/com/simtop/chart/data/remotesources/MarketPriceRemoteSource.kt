package com.simtop.chart.data.remotesources

import com.simtop.chart.data.models.marketprice.MarketPriceResponse
import com.simtop.chart.data.network.BitCoinService
import io.reactivex.Single
import javax.inject.Inject

class MarketPriceRemoteSource @Inject constructor(private val service: BitCoinService) {

    fun getMarketPrices(): Single<MarketPriceResponse> = service.getMarketPrice()
}