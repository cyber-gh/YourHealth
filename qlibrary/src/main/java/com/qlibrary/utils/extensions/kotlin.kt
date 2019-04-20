package com.qlibrary.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String?.isSmth() = !(this?.isEmpty() ?: true)

fun Int?.isNullOrZero() = (this == null || this == 0)

fun String?.toCDouble() = if(this == null || this == "" || this == "," || this == ".") 0.0 else
    this.replace(",",".").toDouble()


fun CharSequence?.toCDouble() = if(this == null || this == "" || this == "," || this == ".") 0.0 else
    this.toString().replace(",",".").toDouble()

fun CharSequence?.toCInt() = if(this == null || this == "" || this == "," || this == ".") 0 else
    this.toString().toInt()


fun <T> MutableList<T>.setAll(list: List<T>){
    this.clear()
    this.addAll(list)
}

val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK)
val dateTimeFormatterZ = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.UK)

val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.UK)

val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.UK)


fun getUTC(): String{
    return dateTimeFormatterZ.apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date())
}

fun Calendar.copy() = Calendar.getInstance().apply {
    time = this.time
}
