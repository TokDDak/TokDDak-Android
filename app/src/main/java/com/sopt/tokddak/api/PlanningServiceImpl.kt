package com.sopt.tokddak.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PlanningServiceImpl {
    private const val BASE_URL = "http://tokddak.ap-northeast-2.elasticbeanstalk.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val planningService: PlanningService = retrofit.create(PlanningService::class.java)
}