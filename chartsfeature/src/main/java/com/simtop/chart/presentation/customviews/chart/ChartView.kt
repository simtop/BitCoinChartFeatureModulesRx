package com.simtop.chart.presentation.customviews.chart

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.simtop.chart.R
import com.simtop.chart.databinding.ChartCustomViewBinding
import com.simtop.testutils.core.convertTimeToString
import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import com.simtop.testutils.core.showToast

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), OnChartValueSelectedListener {

    private val chartCustomViewBinding: ChartCustomViewBinding =
        ChartCustomViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    fun bind(value: MarketPriceModel) {
        chartCustomViewBinding.chartTitle.text = value.name
        chartCustomViewBinding.chartDescription.text = value.description

        chartCustomViewBinding.chart.apply {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)

            val labelsCount = when (value.axis.size) {
                1 -> 1
                2 -> 2
                3 -> 3
                4 -> 4
                else -> 5
            }
            xAxis.setLabelCount(labelsCount, true)

            xAxis.setValueFormatter { axisLabel, _ ->
                axisLabel.toLong().convertTimeToString()
            }
            axisLeft.granularity = 1f
            axisRight.isEnabled = false

            val entries = value.axis.map {
                Entry(it.date.toFloat(), it.price)
            }
            val lineDataSet = LineDataSet(entries, value.currency)
            lineDataSet.setDrawValues(false)
            val color = ContextCompat.getColor(context, R.color.colorPrimary)
            lineDataSet.color = color
            lineDataSet.valueTextColor = color
            lineDataSet.highLightColor = color
            lineDataSet.setCircleColor(color)

            setScaleEnabled(false)
            description.text = value.description
            data = LineData(lineDataSet)
            val markerView = ChartMarkerView(value.currency, context)
            marker = markerView
            animateX(ANIMATION_TIME)
            setOnChartValueSelectedListener(this@ChartView)
        }
    }

    override fun onNothingSelected() {
        //Do nothing
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        //This toast is done to test the UI because we can't access to the marker from espresso
        context.showToast(e.x.toLong().convertTimeToString())
    }

    companion object {
        private const val ANIMATION_TIME = 6000
    }
}