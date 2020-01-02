package com.sopt.tokddak.feature.planning.result

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDateFormat
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import kotlinx.android.synthetic.main.activity_planning_result.*
import kotlinx.android.synthetic.main.activity_planning_result.img_toBack
import kotlinx.android.synthetic.main.activity_planning_result.tv_message

// 모듈화 무조건 필요
class PlanningResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning_result)

        init()
    }

    fun init() {

        bringToFront()

        tv_tripTotalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()
        tv_tripTitle.text = TripInfo.title
        tv_tripDestination.text = TripInfo.destination
        tv_country.text = TripInfo.country
        tv_startDate.text = TripInfo.startDate.toDateFormat()
        tv_endDate.text = TripInfo.endDate.toDateFormat()

        // lodgement 설정
        if (TripInfo.lodgementInfo.isNullOrEmpty()) {
            tv_lodgement.setTextColor(Color.parseColor("#d5d5d5"))
            tv_lodgementCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_lodgementCost.text = "0원"
            img_lodgementRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_lodgementCost.text =
                TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // food 설정
        if (TripInfo.foodInfo.isNullOrEmpty()) {
            tv_food.setTextColor(Color.parseColor("#d5d5d5"))
            tv_foodCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_foodCost.text = "0원"
            img_foodRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_foodCost.text =
                TripInfo.foodInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // snack 설정
        if (TripInfo.snackInfo.isNullOrEmpty()) {
            tv_snack.setTextColor(Color.parseColor("#d5d5d5"))
            tv_snackCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_snackCost.text = "0원"
            img_snackRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_snackCost.text =
                TripInfo.snackInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // activity 설정
        if (TripInfo.activityInfo.isNullOrEmpty()) {
            tv_activity.setTextColor(Color.parseColor("#d5d5d5"))
            tv_activityCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_activityCost.text = "0원"
            img_activityRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_activityCost.text = TripInfo.activityInfo.map { it.price }.sum().toDecimalFormat()
        }

        // shopping 설정
        if (TripInfo.shoppingInfo == 0) {
            tv_shopping.setTextColor(Color.parseColor("#d5d5d5"))
            tv_shoppingCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_shoppingCost.text = "0원"
            img_shoppingRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_shoppingCost.text = TripInfo.shoppingInfo.toDecimalFormat()
        }

        // transportation 설정
        if (TripInfo.transInfo == 0) {
            tv_transportation.setTextColor(Color.parseColor("#d5d5d5"))
            tv_transportationCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_transportationCost.text = "0원"
            img_transportationRanking.setImageResource(R.drawable.img_total_blueline_w90x3)
        } else {
            tv_transportationCost.text = TripInfo.transInfo.toDecimalFormat()
        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            // popup
        }
    }

    fun bringToFront() {
        img_toBack.bringToFront()
        tv_message.bringToFront()
        icn_w.bringToFront()
        tv_tripTotalPrice.bringToFront()
    }
}
