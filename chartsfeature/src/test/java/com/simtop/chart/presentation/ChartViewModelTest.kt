package com.simtop.chart.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.simtop.chart.presentation.chart.ChartViewState
import com.simtop.chart.presentation.chart.ChartViewModel
import com.simtop.chart.domain.usecases.GetMarketPriceUseCase
import com.simtop.chart.utils.fakeMarketPriceModel
import com.simtop.testutils.core.testObserver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class ChartViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val getMarketPriceUseCase: GetMarketPriceUseCase = mockk()

    private val chartViewModel: ChartViewModel by lazy {
        ChartViewModel(getMarketPriceUseCase)
    }

    @Test
    fun `when we get market model it succeeds and shows loader`() {

        coEvery { getMarketPriceUseCase.execute(any()) } returns Single.just(
            fakeMarketPriceModel
        )
        val liveDataUnderTest = chartViewModel.chartViewState.testObserver()

        chartViewModel.getMarketPrice()

        coVerify(exactly = 1) {
            getMarketPriceUseCase.execute(any())
        }

        liveDataUnderTest.observedValues.size shouldBeEqualTo 2
        liveDataUnderTest.observedValues[0] shouldBeEqualTo ChartViewState.Loading
        liveDataUnderTest.observedValues[1] shouldBeEqualTo ChartViewState.Success(fakeMarketPriceModel)

    }

    @Test
    fun `when we get market price model it fails and shows error`() {
        val errorName = "Error getting list of categories"
        coEvery { getMarketPriceUseCase.execute(any()) } returns Single.error(
            Exception(errorName)
        )

        val liveDataUnderTest = chartViewModel.chartViewState.testObserver()

        chartViewModel.getMarketPrice()

        coVerify(exactly = 1) {
            getMarketPriceUseCase.execute(any())
        }

        liveDataUnderTest.observedValues.size shouldBeEqualTo 2
        liveDataUnderTest.observedValues[0] shouldBeEqualTo ChartViewState.Loading
        liveDataUnderTest.observedValues[1] shouldBeEqualTo ChartViewState.Error(errorName)
    }
}
