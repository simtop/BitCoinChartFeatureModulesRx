package com.simtop.bitcoinapp.test

import android.content.Context
import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.simtop.bitcoinapp.R
import com.simtop.bitcoinapp.MainActivity
import com.simtop.bitcoinapp.robots.homeScreen
import com.simtop.bitcoinapp.utils.*
import com.simtop.chart.di.UrlModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
@UninstallModules(UrlModule::class)
class ChartTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val mockResponse =
        JsonUtils.loadJsonFromAssets(
            context,
            MARKET_PRICE_RESPONSE_FILE_NAME
        )

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    private val progressBarVisibility by lazy {
        ViewVisibilityIdlingResource(
            R.id.progress_bar,
            View.GONE
        )
    }

    @Test
    fun shouldDisplayChartAndShowToast() {

        homeScreen {
            mockWebServer.enqueue(
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockResponse)
            )

            setIdlingResourceTimeout(3)
            registerIdlingRegistry(progressBarVisibility)
            clickAndWaitView(R.id.chart)
            isToastDisplayed(SELECTED_DATE)
            unregisterIdlingRegistry(progressBarVisibility)
        }
    }

    @Test
    fun shouldDisplayErrorToast() {

        homeScreen {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
                    .setBody(mockResponse)
            )
            setIdlingResourceTimeout(3)
            registerIdlingRegistry(progressBarVisibility)
            isToastDisplayed("HTTP 503 Server Error")
        }
    }
}