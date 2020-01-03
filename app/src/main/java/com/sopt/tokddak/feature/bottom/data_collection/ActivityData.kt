package com.sopt.tokddak.feature.bottom.data_collection

import com.sopt.tokddak.feature.bottom.BottomItem

class ActivityData {
    fun getCategory():MutableList<BottomItem>{
        return listOf(
            BottomItem(
                name = "존잼"
            ),
            BottomItem(
                name = "중잼"
            ),
            BottomItem(
                name = "노잼"
            )
        ).toMutableList()
    }
}