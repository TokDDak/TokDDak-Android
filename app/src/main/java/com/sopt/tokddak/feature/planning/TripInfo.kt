package com.sopt.tokddak.feature.planning

import java.util.*

object TripInfo {
    var title = ""
    var destination = ""
    var startDate = Date()
    var endDate = Date()
    var lodgementInfo = listOf<Lodgement>()
    var foodInfo = listOf<Food>()
    var snackInfo = listOf<Snack>()
    var activityInfo = listOf<Activity>()
    var shoppingInfo = 0
    var transInfo = 0

    fun clear(){
        title = ""
        destination = ""
        startDate = Date()
        endDate = Date()
        lodgementInfo = listOf()
        foodInfo = listOf()
        snackInfo = listOf()
        activityInfo = listOf()
        shoppingInfo = 0
        transInfo = 0
    }

    //TODO: ㅇㅔ산
}

data class Lodgement(
    val type: String,
    val count: Int,
    val avgPrice: Int
)

data class Food(
    var type: Int,
    var count: Int
)
data class Snack(
    var type: Int,
    var count: Int
)

data class Activity(
    var name: String,
    var price: Int,
    var isSelected: Int?,
    var actImg: Int,
    var flag: Boolean
)
// data class Transportation()
