package com.simtop.testutils.core

import android.util.Log
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertTimeToString(format : String = "dd MMM") : String {
    var stringDate = ""
    try {
        val timestamp = Timestamp(this * 1000)
        val date = Date(timestamp.time)
        val formattedDate = SimpleDateFormat(format, Locale("en", "us"))
        stringDate = formattedDate.format(date)
    } catch (e: Exception) {
        Log.e("Date formatting error", "format $format date $this")
    }
    return  stringDate
}