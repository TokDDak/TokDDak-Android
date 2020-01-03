package com.sopt.tokddak.feature.day

class DayData {
    fun getDay():List<DayItem>{
        return listOf(
            DayItem(
                day = "Day 1"
            ),
            DayItem(
                day = "Day 2"
            ),
            DayItem(
                day = "Day 3"
            ),
            DayItem(
                day = "Day 4"
            ),
            DayItem(
                day = "Day 5"
            ),
            DayItem(
                day = "Day 6"
            )
        )
    }
}