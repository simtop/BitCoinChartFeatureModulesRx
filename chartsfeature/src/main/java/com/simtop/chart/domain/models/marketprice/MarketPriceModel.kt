package com.simtop.chart.domain.models.marketprice

data class MarketPriceModel(
    val description: String,
    val name: String,
    val period: String,
    val status: String,
    val currency: String,
    val axis: List<AxisModel>
)