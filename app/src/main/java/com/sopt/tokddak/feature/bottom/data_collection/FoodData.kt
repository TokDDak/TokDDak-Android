package com.sopt.tokddak.feature.bottom.data_collection

import com.sopt.tokddak.feature.bottom.BottomItem

class FoodData {
    fun getCategory():MutableList<BottomItem>{
        return listOf(
            BottomItem(
                name = "고급음식점"
            ),
            BottomItem(
                name = "일반음식점"
            ),
            BottomItem(
                name = "간편식"
            )
        ).toMutableList()
    }
}