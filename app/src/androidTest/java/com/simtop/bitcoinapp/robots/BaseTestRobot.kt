package com.simtop.bitcoinapp.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.simtop.bitcoinapp.utils.RecyclerViewMatchers
import com.simtop.bitcoinapp.utils.ToastMatcher
import com.simtop.bitcoinapp.utils.ViewVisibilityIdlingResource
import com.simtop.bitcoinapp.utils.waitUntilVisible
import java.util.concurrent.TimeUnit

open class BaseTestRobot {

    fun textView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)

    fun matchCountRecyclerViewItems(listRes: Int, count: Int = 0) {
        onView(withId(listRes))
            .check(ViewAssertions.matches(RecyclerViewMatchers.withItemCount(count)))
    }

    fun registerIdlingRegistry(idlingResource: ViewVisibilityIdlingResource) {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun unregisterIdlingRegistry(idlingResource: ViewVisibilityIdlingResource) {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    fun setIdlingResourceTimeout(time: Long, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        IdlingPolicies.setIdlingResourceTimeout(time, timeUnit)
    }

    fun swipeUpScrollView(resId: Int) {
        onView(withId(resId)).perform(swipeUp(), click())
    }

    fun swipeDownScrollView(resId: Int) {
        onView(withId(resId)).perform(swipeDown(), click())
    }

    fun clickAndWaitView(resId: Int, timeout: Long = 2000) {
        onView((withId(resId)))
            .waitUntilVisible(timeout)
            .perform(click())
    }

    fun isDisplayedViewAfterWaiting(resId: Int, timeout: Long = 2000) {
        onView(withId(resId))
            .waitUntilVisible(timeout)
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun isToastDisplayed(text: String) {
        onView(withText(text)).inRoot(ToastMatcher()).check(matches(isDisplayed()));
    }
}