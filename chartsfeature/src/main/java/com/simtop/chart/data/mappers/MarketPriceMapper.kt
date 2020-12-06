package com.simtop.chart.data.mappers

import com.simtop.chart.data.models.marketprice.AxisResponse
import com.simtop.chart.data.models.marketprice.MarketPriceResponse
import com.simtop.chart.domain.models.marketprice.AxisModel
import com.simtop.chart.domain.models.marketprice.MarketPriceModel

object MarketPriceMapper {
    fun fromMarketPriceResponseToMarketPriceModel(response: MarketPriceResponse?) =
        MarketPriceModel(
            description = response?.description ?: "",
            name = response?.name ?: "",
            period = response?.period ?: "",
            status = response?.status ?: "",
            currency = response?.currency ?: "",
            axis = fromAxisResponseToAxisModel(response?.axis)
        )

    private fun fromAxisResponseToAxisModel(response: List<AxisResponse>?) =
        response?.map {
            AxisModel(
                date = it.date ?: 0L,
                price = it.price ?: 0f
            )
        } ?: emptyList()
}



