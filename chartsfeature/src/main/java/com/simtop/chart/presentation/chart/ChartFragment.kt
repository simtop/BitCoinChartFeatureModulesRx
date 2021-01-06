package com.simtop.chart.presentation.chart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.simtop.testutils.core.observe
import com.simtop.testutils.core.showToast
import com.simtop.chart.R
import com.simtop.chart.databinding.FragmentChartFragmentBinding
import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import com.simtop.testutils.core.gone
import com.simtop.testutils.core.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : Fragment(R.layout.fragment_chart_fragment) {

    private val chartViewModel : ChartViewModel by viewModels()

    private var _fragmentChartFragmentBinding: FragmentChartFragmentBinding? = null
    private val fragmentChartFragmentBinding get() = _fragmentChartFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentChartFragmentBinding.bind(view)
        _fragmentChartFragmentBinding = binding

        setUpRetryListener()

        observe(
            chartViewModel.chartViewState,
            { viewState ->
                viewState?.let { renderChartViewState(it) }
            }
        )
    }

    private fun setUpRetryListener() {
        fragmentChartFragmentBinding?.retry?.setOnClickListener {
            chartViewModel.getMarketPrice()
        }
    }

    private fun renderChartViewState(it: ChartViewState<MarketPriceModel>) {
        when (it) {
            is ChartViewState.Success -> {
                fragmentChartFragmentBinding?.chartView?.visible()
                fragmentChartFragmentBinding?.progressBar?.gone()
                fragmentChartFragmentBinding?.errorState?.gone()
                fragmentChartFragmentBinding?.chartView?.bind(it.result)

            }
            is ChartViewState.Error -> {
                fragmentChartFragmentBinding?.chartView?.gone()
                fragmentChartFragmentBinding?.progressBar?.gone()
                fragmentChartFragmentBinding?.errorState?.visible()
                requireActivity().showToast(it.result)
            }
            ChartViewState.Loading -> {
                fragmentChartFragmentBinding?.chartView?.gone()
                fragmentChartFragmentBinding?.progressBar?.visible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentChartFragmentBinding = null
    }
}