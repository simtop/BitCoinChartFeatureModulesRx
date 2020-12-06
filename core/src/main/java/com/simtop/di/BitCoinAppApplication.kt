package com.simtop.di

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment

open class BitCoinAppApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        buildApiComponent()
    }

    open fun buildApiComponent() {
        appComponent = DaggerApplicationComponent
            .factory()
            .create(this)
    }
}

val Activity.appComponent get() = (applicationContext as BitCoinAppApplication).appComponent
val Fragment.appComponent get() = requireActivity().appComponent