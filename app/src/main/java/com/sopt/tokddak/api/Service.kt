package com.sopt.tokddak.api

import com.sopt.tokddak.feature.history.HistoryServerItem
import com.sopt.tokddak.feature.expense.PostExpense
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Service {
    @GET("/timeline/{TripId}")
    fun getHistory( @Path("TripId") TripId : Int) : Call<HistoryServerItem>

    @POST("/timeline")
    fun setExpense(
        @Body body:PostExpense
    ) : Call<Unit>

}