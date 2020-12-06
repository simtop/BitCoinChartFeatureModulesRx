package com.simtop.chart.presentation.chart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.simtop.testutils.core.observe
import com.simtop.testutils.core.showToast
import com.simtop.chart.R
import com.simtop.chart.databinding.FragmentChartFragmentBinding
import com.simtop.chart.di.DaggerChartComponent
import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import com.simtop.di.DaggerApplicationComponent
import com.simtop.di.appComponent
import com.simtop.testutils.core.gone
import com.simtop.testutils.core.visible
import javax.inject.Inject

class ChartFragment : Fragment(R.layout.fragment_chart_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val chartViewModel by viewModels<ChartViewModel> {
        viewModelFactory
    }

    private lateinit var fragmentChartFragmentBinding: FragmentChartFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerChartComponent.factory().create(appComponent).inject(this)

        val binding = FragmentChartFragmentBinding.bind(view)
        fragmentChartFragmentBinding = binding

       // (requireActivity() as MainActivity).showToolbar(false)

        setUpRetryListener()

        chartViewModel.getMarketPrice()

        observe(
            chartViewModel.chartViewState,
            { viewState ->
                viewState?.let { renderChartViewState(it) }
            }
        )
    }

    private fun setUpRetryListener() {
        fragmentChartFragmentBinding.retry.setOnClickListener {
            chartViewModel.getMarketPrice()
        }
    }

    private fun renderChartViewState(it: ChartViewState<MarketPriceModel>) {
        when (it) {
            is ChartViewState.Success -> {
                fragmentChartFragmentBinding.chartView.visible()
                fragmentChartFragmentBinding.progressBar.gone()
                fragmentChartFragmentBinding.errorState.gone()
                fragmentChartFragmentBinding.chartView.bind(it.result)

            }
            is ChartViewState.Error -> {
                fragmentChartFragmentBinding.chartView.gone()
                fragmentChartFragmentBinding.progressBar.gone()
                fragmentChartFragmentBinding.errorState.visible()
                requireActivity().showToast(it.result)
            }
            ChartViewState.Loading -> {
                fragmentChartFragmentBinding.chartView.gone()
                fragmentChartFragmentBinding.progressBar.visible()
            }
        }
    }
}