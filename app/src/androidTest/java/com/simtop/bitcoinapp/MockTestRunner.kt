package com.simtop.bitcoinapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.simtop.bitcoinapp.di.TestApplication

class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}