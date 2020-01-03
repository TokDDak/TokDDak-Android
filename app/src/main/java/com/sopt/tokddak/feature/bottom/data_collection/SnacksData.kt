package com.sopt.tokddak.feature.bottom.data_collection

import com.sopt.tokddak.feature.bottom.BottomItem

class SnacksData {
    fun getCategory():MutableList<BottomItem>{
        return listOf(
            BottomItem(
                name = "펍 & 바"
            ),
            BottomItem(
                name = "디저트"
            ),
            BottomItem(
                name = "카페"
            )
        ).toMutableList()
    }
}