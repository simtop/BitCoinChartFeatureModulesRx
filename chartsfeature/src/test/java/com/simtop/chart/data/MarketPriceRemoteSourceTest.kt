package com.simtop.chart.data

import com.simtop.chart.data.utils.TestMockWebService
import com.simtop.chart.utils.FAKE_MARKET_PRICE_JSON
import com.simtop.testutils.RxTestSchedulerRule
import com.simtop.chart.utils.fakeMarketPriceResponse
import org.junit.Rule
import org.junit.Test
import java.net.HttpURLConnection

class MarketPriceRemoteSourceTest : TestMockWebService() {

    @get:Rule
    val rxJavaTestRule = RxTestSchedulerRule()

    @Test
    fun `when we make the api call for market prices then we get a success`() {
        val expectedResult = fakeMarketPriceResponse
        mockHttpResponse(FAKE_MARKET_PRICE_JSON, HttpURLConnection.HTTP_OK)

        val result = apiService.getMarketPrice().test()
        result.assertValue(expectedResult)
        result.assertNoErrors()
    }

    @Test
    fun `when we make the api call for market prices then we get a failure`() {
        mockHttpResponse(FAKE_MARKET_PRICE_JSON, HttpURLConnection.HTTP_UNAVAILABLE)

        val result = apiService.getMarketPrice().test()
        result.assertError(Exception::class.java)
    }
}