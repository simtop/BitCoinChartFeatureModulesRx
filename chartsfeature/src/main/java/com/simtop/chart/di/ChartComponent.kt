package com.simtop.chart.di

import com.simtop.chart.presentation.chart.ChartFragment
import com.simtop.di.ApplicationComponent
import com.simtop.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        ViewModelsModule::class,
        MarketPriceRepositoryModule::class,
        BitCoinApiModule::class
    ]
)
interface ChartComponent {
    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): ChartComponent
    }
    fun inject(chartFragment: ChartFragment)

}