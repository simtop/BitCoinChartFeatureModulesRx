package com.simtop.chart.utils

import com.simtop.chart.data.models.marketprice.AxisResponse
import com.simtop.chart.data.models.marketprice.MarketPriceResponse

const val FAKE_MARKET_PRICE_JSON = "fake_market_price_response.json"

val fakeMarketPriceResponse =
    MarketPriceResponse(
        "Average USD market price across major bitcoin exchanges.",
        "Market Price (USD)",
        "day",
        "ok",
        "USD",
        listOf(
            AxisResponse(
                1605225600,
                16295.57f
            ),
            AxisResponse(
                1605312000,
                16339.33f
            )
        )
    )
