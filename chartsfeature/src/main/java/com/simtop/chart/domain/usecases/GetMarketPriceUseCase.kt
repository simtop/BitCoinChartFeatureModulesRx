package com.simtop.chart.domain.usecases

import com.simtop.testutils.core.BaseUseCase
import com.simtop.chart.domain.models.marketprice.MarketPriceModel
import com.simtop.chart.domain.repository.MarketPriceRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMarketPriceUseCase @Inject constructor(
    private val marketPriceRepository: MarketPriceRepository
) : BaseUseCase<MarketPriceModel, GetMarketPriceUseCase.Params>() {

    inner class Params()

    override fun buildUseCase(params: Params): Single<MarketPriceModel> {
        return marketPriceRepository.getMarketPrice()
    }
}