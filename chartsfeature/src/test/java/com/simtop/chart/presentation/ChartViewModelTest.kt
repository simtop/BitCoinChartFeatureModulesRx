package com.simtop.chart.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.simtop.chart.presentation.chart.ChartViewState
import com.simtop.chart.presentation.chart.ChartViewModel
import com.simtop.chart.domain.usecases.GetMarketPriceUseCase
import com.simtop.chart.utils.fakeMarketPriceModel
import com.simtop.testutils.RxTestSchedulerRule
import com.simtop.testutils.core.testObserver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

internal class ChartViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val rxJavaTestRule = RxTestSchedulerRule()

    private val getMarketPriceUseCase: GetMarketPriceUseCase = mockk()

    private lateinit var chartViewModel: ChartViewModel

    @Test
    fun `when we get market model it succeeds`() {
        every { getMarketPriceUseCase.execute(any()) } returns Single.just(
            fakeMarketPriceModel
        )

        chartViewModel = ChartViewModel(getMarketPriceUseCase)

        val liveDataUnderTest = chartViewModel.chartViewState.testObserver()

        liveDataUnderTest.observedValues.last() shouldBeEqualTo ChartViewState.Success(fakeMarketPriceModel)
    }

    @Test
    fun `when we get market price model it fails and shows error`() {
        val errorName = "Error getting list of categories"
        coEvery { getMarketPriceUseCase.execute(any()) } returns Single.error(
            Exception(errorName)
        )
        chartViewModel = ChartViewModel(getMarketPriceUseCase)

        val liveDataUnderTest = chartViewModel.chartViewState.testObserver()

        coVerify(exactly = 1) {
            getMarketPriceUseCase.execute(any())
        }

        liveDataUnderTest.observedValues.last() shouldBeEqualTo ChartViewState.Error(errorName)
    }
}
