package com.example.xmlproject.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


object DateUtils {
    fun formatDateTime(isoDate: String?): String {
        if (isoDate.isNullOrEmpty()) return "N/A"

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val date = inputFormat.parse(isoDate)
            val calendar = Calendar.getInstance()
            calendar.time = date!!

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val suffix = when {
                day in 11..13 -> "th"
                day % 10 == 1 -> "st"
                day % 10 == 2 -> "nd"
                day % 10 == 3 -> "rd"
                else -> "th"
            }

            val outputFormat = SimpleDateFormat("MMM, hh:mm a", Locale.getDefault())
            val formatted = outputFormat.format(date)
            "$day$suffix $formatted"
        } catch (e: Exception) {
            "N/A"
        }
    }
}
