package com.simtop.bitcoinapp.di

import com.simtop.bitcoinapp.test.ChartTest
import com.simtop.chart.di.BitCoinApiModule
import com.simtop.di.*
import dagger.BindsInstance
import dagger.Component
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        BitCoinApiModule::class,
        TestUrlModule::class
    ]
)
interface TestApplicationComponent : ApplicationComponent {

    var mockWebServer: MockWebServer

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: TestApplication): TestApplicationComponent
    }

    fun inject(chartTest: ChartTest)
}