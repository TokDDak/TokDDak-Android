package com.sopt.tokddak.feature.history

import com.sopt.tokddak.api.ServiceImpl
import retrofit2.Call

class GetHistoryServer : HistoryServer{
    override fun getHistory(TripId : Int): Call<HistoryServerItem> {
        return ServiceImpl.service.getHistory(TripId)
    }
}