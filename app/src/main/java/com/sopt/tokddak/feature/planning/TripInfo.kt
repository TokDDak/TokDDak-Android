package com.sopt.tokddak.feature.planning

import java.util.*

object TripInfo {
    var country = ""
    var title = ""
    var destination = ""
    var startDate = Date()
    var endDate = Date()
    var lodgementInfo = listOf<Lodgement>()
    var foodInfo = listOf<Food>()
    var snackInfo = listOf<Snack>()
    var activityInfo = listOf<Activity>()
    var shoppingInfo: Int = 0
    var transInfo: Int = 0
    var tripTotalCost = 0

    // 여행지 선택 시 호출
    fun clear() {
        country = ""
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
        tripTotalCost = 0
    }

    fun foodInfoClear(){
        foodInfo = listOf()
    }

    fun snackInfoClear(){
        snackInfo = listOf()
    }

    //TODO: 예산 계산 함수
}

data class Lodgement(
    val type: String,
    val count: Int,
    val avgPrice: Int
)

data class Food(
    var type: String,
    var count: Int,
    var avgPrice: Int,
    var image: Int
)

data class Snack(
    var type: String,
    var count: Int,
    var avgPrice: Int,
    var image: Int
)

data class Activity(
    var name: String,
    var price: Int,
    var isSelected: Int?,
    var actImg: Int,
    var flag: Boolean,
    var url: String?,
    var detailInfo: String
)

/*data class ActivityData(
    val id: Int,
    val name: String,
    val cost: Int,
    val content: String,
    val url_mrt: String,
    val url_kl: String,
    val img: String,
    val CityId: Int
)*/
