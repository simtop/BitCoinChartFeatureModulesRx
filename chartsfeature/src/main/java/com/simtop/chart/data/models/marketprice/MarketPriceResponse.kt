package com.simtop.chart.data.models.marketprice

import com.google.gson.annotations.SerializedName

data class MarketPriceResponse(
    val description: String?,
    val name: String?,
    val period: String?,
    val status: String?,
    @SerializedName("unit")
    val currency: String?,
    @SerializedName("values")
    val axis: List<AxisResponse>?
)