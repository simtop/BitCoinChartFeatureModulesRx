package com.simtop.chart.data.repository

import com.simtop.chart.data.mappers.MarketPriceMapper
import com.simtop.chart.data.remotesources.MarketPriceRemoteSource
import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import com.simtop.chart.domain.repository.MarketPriceRepository
import io.reactivex.Single
import javax.inject.Inject

class MarketPriceRepositoryImpl @Inject constructor(
    private val marketPriceRemoteSource: MarketPriceRemoteSource
) : MarketPriceRepository {

    override fun getMarketPrice(): Single<MarketPriceModel> {
        return marketPriceRemoteSource.getMarketPrices()
            .map {
                MarketPriceMapper.fromMarketPriceResponseToMarketPriceModel(it)
            }
    }
}
