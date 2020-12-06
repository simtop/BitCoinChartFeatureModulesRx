package com.simtop.bitcoinapp.utils

import android.content.Context

object JsonUtils {
    fun loadJsonFromAssets(context: Context, fileName: String): String {
        return context.applicationContext.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }
}