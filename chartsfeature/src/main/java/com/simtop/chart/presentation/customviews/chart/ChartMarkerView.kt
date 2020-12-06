package com.simtop.chart.presentation.customviews.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.simtop.chart.R
import com.simtop.chart.databinding.MarkerViewBinding
import com.simtop.testutils.core.convertTimeToString


@SuppressLint("ViewConstructor")
class ChartMarkerView(
    private val currency: String,
    context: Context
) : MarkerView(context, R.layout.marker_view) {

    private val markerViewBinding: MarkerViewBinding =
        MarkerViewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private val totalWidthInPx = resources.displayMetrics.widthPixels

    private var controlOffset: MPPointF? = null

    override fun refreshContent(entry: Entry, highlight: Highlight) {

        val formattedDate = entry.x.toLong().convertTimeToString()
        val formattedAmount = entry.y.toLong()
        val amountWithCurrency = "$formattedAmount $currency"

        markerViewBinding.markerDate.text = context.getString(R.string.date, formattedDate)
        markerViewBinding.markerPrice.text = context.getString(R.string.price, amountWithCurrency)

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {

        if (controlOffset == null) {
            controlOffset = MPPointF(-(width / 2).toFloat(), -height.toFloat())
        }

        return controlOffset as MPPointF
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {

        var newPosX = posX

        val width = width
        if (totalWidthInPx - newPosX - width < width) {
            newPosX -= width.toFloat()
        } else if(newPosX < 50f) {
            newPosX = 0f
        }

        canvas.translate(newPosX, posY)
        draw(canvas)
        canvas.translate(-newPosX, -posY)
    }

    companion object {
        private const val LEFT_OFFSET = 50f
    }
}