package com.sopt.tokddak.common

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.toDecimalFormat(): String {
    val formatter = DecimalFormat("###,###")
    return formatter.format(this)
}

fun Date.toDateFormat(): String{
    val formatter = SimpleDateFormat("yyyy.MM.dd")
    return formatter.format(this)
}