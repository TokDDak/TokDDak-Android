package com.sopt.tokddak.feature.expense

data class PostExpense(
    var day : Int,
    var category : Int,
    var content : String,
    var TripId : Int,
    var cost : Int
)