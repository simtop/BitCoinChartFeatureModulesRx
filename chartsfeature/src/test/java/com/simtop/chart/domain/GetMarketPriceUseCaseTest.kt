package com.simtop.chart.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.simtop.chart.domain.repository.MarketPriceRepository
import com.simtop.testutils.RxTestSchedulerRule
import com.simtop.chart.utils.fakeMarketPriceModel
import io.mockk.coEvery
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class  GetMarketPriceUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaTestRule = RxTestSchedulerRule()

    private val marketPriceRepository: MarketPriceRepository = mockk()

    @Test
    fun `should get market price model from repository`() {

        coEvery { marketPriceRepository.getMarketPrice() } returns Single.just(
            fakeMarketPriceModel
        )

        val result = marketPriceRepository.getMarketPrice().test()

        result.assertValue(fakeMarketPriceModel)
        result.assertNoErrors()
    }

    @Test
    fun `should get error when asking for market price model from repository`() {

        coEvery { marketPriceRepository.getMarketPrice() } returns Single.error(
            Exception("Error getting list of categories")
        )

        val result = marketPriceRepository.getMarketPrice().test()

        result.assertError(Exception::class.java)

    }
}