package com.simtop.chart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.simtop.chart.data.remotesources.MarketPriceRemoteSource
import com.simtop.chart.data.repository.MarketPriceRepositoryImpl
import com.simtop.testutils.RxTestSchedulerRule
import com.simtop.chart.utils.fakeMarketPriceModel
import com.simtop.chart.utils.fakeMarketPriceResponse
import io.mockk.coEvery
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class MarketPriceRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaTestRule = RxTestSchedulerRule()

    private val marketPriceRemoteSource: MarketPriceRemoteSource = mockk()

    private val marketPriceRepository = MarketPriceRepositoryImpl(marketPriceRemoteSource)


    @Test
    fun `should get market price model from data source`() {
        coEvery { marketPriceRemoteSource.getMarketPrices() } returns Single.just(
            fakeMarketPriceResponse
        )

        val result = marketPriceRepository.getMarketPrice().test()

        result.assertValue(fakeMarketPriceModel)
        result.assertNoErrors()
    }

    @Test
    fun `should get error when asking for market price model from data source`() {
        coEvery { marketPriceRemoteSource.getMarketPrices() } returns Single.error(
            Exception("Error getting list of categories")
        )

        val result = marketPriceRepository.getMarketPrice().test()

        result.assertError(Exception::class.java)
    }
}