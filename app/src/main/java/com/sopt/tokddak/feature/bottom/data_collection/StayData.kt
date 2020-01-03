package com.sopt.tokddak.feature.bottom.data_collection

import com.sopt.tokddak.feature.bottom.BottomItem

class StayData{
    fun getCategory():MutableList<BottomItem>{
        return listOf(
            BottomItem(
                name = "최고급호텔"
            ),
            BottomItem(
                name = "고급호텔"
            ),
            BottomItem(
                name = "일반호텔"
            ),
            BottomItem(
                name = "저가호텔"
            )
        ).toMutableList()
    }
}