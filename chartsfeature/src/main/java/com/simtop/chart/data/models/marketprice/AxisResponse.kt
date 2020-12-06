package com.simtop.chart.data.models.marketprice

import com.google.gson.annotations.SerializedName

data class AxisResponse(
    @SerializedName("x")
    val date: Long?,
    @SerializedName("y")
    val price: Float?
)