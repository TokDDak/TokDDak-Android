package com.sopt.tokddak.feature.planning.lodgement

object Lodgement {
    var totalCount: Int = 0 // 총 숙박 일 수
    var totalPrice: Int = 0 // 총 가격, 서버 전달
    var userData = mutableMapOf<Int, Int>() // 정보, 서버 전달

    var serverData = arrayListOf<String>() // 서버 통신시 data class 생성
}