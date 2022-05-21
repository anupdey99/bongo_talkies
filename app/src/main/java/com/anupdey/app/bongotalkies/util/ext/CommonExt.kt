package com.anupdey.app.bongotalkies.util.ext

import java.text.SimpleDateFormat
import java.util.*

fun formatToHHMM(minute: Int): String {
    val hours = minute / 60
    val minutes = minute % 60
    return String.format("%dh %02dm", hours, minutes)
}

fun formatDate(input: String?, inputFormat: String = "yyyy-MM-dd", outputFormat: String = "dd MMM, yyyy"): String {
    input ?: return ""
    val sdf1 = SimpleDateFormat(inputFormat, Locale.US)
    val sdf2 = SimpleDateFormat(outputFormat, Locale.US)

    return try {
        val date = sdf1.parse(input)
        if (date != null) {
            sdf2.format(date)
        } else {
            input
        }
    } catch (e: Exception) {
        e.printStackTrace()
        input
    }
}