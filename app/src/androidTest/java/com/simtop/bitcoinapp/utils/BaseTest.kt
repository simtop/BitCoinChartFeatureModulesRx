package com.simtop.bitcoinapp.utils

import android.content.Context
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.simtop.bitcoinapp.di.TestApplication
import org.hamcrest.Matcher

abstract class BaseTest {

    val application: TestApplication =
        ApplicationProvider.getApplicationContext<Context>() as TestApplication

    abstract fun injectTest()

    fun waitViewShown(matcher: Matcher<View>) {
        val idlingResource: IdlingResource =
            ViewShownIdlingResource(
                matcher,
                isDisplayed()
            )
        try {
            IdlingRegistry.getInstance().register(idlingResource)
            onView(withId(0)).check(doesNotExist())
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }
}