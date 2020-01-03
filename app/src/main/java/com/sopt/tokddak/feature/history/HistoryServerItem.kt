package com.sopt.tokddak.feature.history

import com.google.gson.annotations.SerializedName

data class HistoryServerItem(
    @SerializedName("status")
    val status : Int,
    @SerializedName("data")
    val data : List<HistoryServerInItem>
)

data class HistoryServerInItem(
    @SerializedName("day")
    val day : Int,
    @SerializedName("cost")
    val price : Int,
    @SerializedName("category")
    val category : Int,
    @SerializedName("content")
    val detail : String
)