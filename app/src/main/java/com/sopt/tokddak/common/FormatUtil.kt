package com.sopt.tokddak.common

import java.text.DecimalFormat

fun Int.toDecimalFormat(): String {
    val formatter = DecimalFormat("###,###")
    return formatter.format(this)
}