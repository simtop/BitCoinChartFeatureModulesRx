package com.simtop.chart.domain.repository

import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import io.reactivex.Single

interface MarketPriceRepository {

    fun getMarketPrice(): Single<MarketPriceModel>
}