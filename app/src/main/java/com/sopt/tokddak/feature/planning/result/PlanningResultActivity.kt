package com.sopt.tokddak.feature.planning.result

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDateFormat
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.*
import com.sopt.tokddak.feature.planning.TripInfo.activityInfo
import com.sopt.tokddak.feature.planning.TripInfo.foodInfo
import com.sopt.tokddak.feature.planning.TripInfo.lodgementInfo
import com.sopt.tokddak.feature.planning.TripInfo.shoppingInfo
import com.sopt.tokddak.feature.planning.TripInfo.snackInfo
import com.sopt.tokddak.feature.planning.TripInfo.transInfo
import kotlinx.android.synthetic.main.activity_planning_result.*
import kotlinx.android.synthetic.main.activity_planning_result.img_toBack
import kotlinx.android.synthetic.main.activity_planning_result.tv_foodCost
import kotlinx.android.synthetic.main.activity_planning_result.tv_message
import kotlinx.android.synthetic.main.activity_planning_result.tv_snackCost

// 모듈화 무조건 필요
class PlanningResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning_result)

        lodgementInfo += Lodgement("zz", 2, 2000)
        foodInfo += Food("fff", 2, 300, 0)
        snackInfo += Snack("fff", 2, 100, 0)
        // activityInfo += Activity("dd", 300000, null, 0, false, null, "zzz")


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
        if (lodgementInfo.isNullOrEmpty()) {
            tv_lodgement.setTextColor(Color.parseColor("#d5d5d5"))
            tv_lodgementCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_lodgementCost.text = "0원"
        } else {
            tv_lodgementCost.text =
                lodgementInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // food 설정
        if (foodInfo.isNullOrEmpty()) {
            tv_food.setTextColor(Color.parseColor("#d5d5d5"))
            tv_foodCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_foodCost.text = "0원"
        } else {
            tv_foodCost.text =
                foodInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // snack 설정
        if (snackInfo.isNullOrEmpty()) {
            tv_snack.setTextColor(Color.parseColor("#d5d5d5"))
            tv_snackCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_snackCost.text = "0원"
        } else {
            tv_snackCost.text =
                snackInfo.map { it.count * it.avgPrice }.sum().toDecimalFormat()
        }

        // activity 설정
        if (activityInfo.isNullOrEmpty()) {
            tv_activity.setTextColor(Color.parseColor("#d5d5d5"))
            tv_activityCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_activityCost.text = "0원"
        } else {
            tv_activityCost.text = activityInfo.map { it.cost }.sum().toDecimalFormat()
        }

        // shopping 설정
        if (shoppingInfo == 0) {
            tv_shopping.setTextColor(Color.parseColor("#d5d5d5"))
            tv_shoppingCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_shoppingCost.text = "0원"
        } else {
            tv_shoppingCost.text = shoppingInfo.toDecimalFormat()
        }

        // transportation 설정
        if (transInfo == 0) {
            tv_transportation.setTextColor(Color.parseColor("#d5d5d5"))
            tv_transportationCost.setTextColor(Color.parseColor("#d5d5d5"))
            tv_transportationCost.text = "0원"
        } else {
            tv_transportationCost.text = transInfo.toDecimalFormat()
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
