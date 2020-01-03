package com.sopt.tokddak.feature.history

import retrofit2.Call
interface HistoryServer {
    fun getHistory(TripId : Int):Call<HistoryServerItem>
}