package com.id.mobileuts.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object util {
    fun convertCurrency(
        jumlah: String, npemecah: Int,
        insert: Char
    ): String {
        var jumlah = jumlah
        if (jumlah == "" || jumlah == "null") {
            return "0"
        }
        val data = jumlah.toCharArray()
        jumlah = ""
        for (i in data.indices) {
            if ((data.size - i) % npemecah == 0) {
                jumlah += insert
            }
            jumlah += data[i]
        }
        if (data.size % npemecah == 0) {
            jumlah = jumlah.substring(1, jumlah.length)
        }
        return "$jumlah"
    }

    val SHORT_MONTHS = arrayOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "Mei",
        "Jun",
        "Jul",
        "Agu",
        "Sep",
        "Okt",
        "Nov",
        "Des"
    )

    val TIME_ZONE = dateToString(Calendar.getInstance(), "ZZZZZ")

    const val DATE_PATTERN_FROM_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun dateToString(calendar: Calendar, pattern: String, locale: Locale = Locale.US): String? {
        return try {
            val formatter = SimpleDateFormat(pattern, locale)
            formatter.format(calendar.time)
        } catch (e: ParseException) {
            null
        }
    }

    fun dateToShortStringWithTime(calendar: Calendar): String {
        val numberFormat: NumberFormat = DecimalFormat("00")
        val dateOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val month = SHORT_MONTHS[calendar.get(Calendar.MONTH)]
        val year = calendar.get(Calendar.YEAR)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$dateOfMonth $month $year ${numberFormat.format(hour)}:${numberFormat.format(minute)}"
    }
}