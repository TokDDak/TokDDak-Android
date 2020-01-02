package com.sopt.tokddak.feature.planning.transportation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import kotlinx.android.synthetic.main.activity_transportation_planning.*

class TransportationPlanningActivity : AppCompatActivity() {

    var selectedCategoryList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transportation_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
    }

    fun init() {

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        btn_cancel.isGone = true
        btn_done.isEnabled = false
        edt_transCost.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                setButtonState()
                setViewState()
            }
        )

        btn_done.setOnClickListener {
            val transCost = edt_transCost.toString().toInt()
            TripInfo.shoppingInfo = transCost
            TripInfo.tripTotalCost += transCost

            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()
        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_cancel.setOnClickListener {
            edt_transCost.text.clear()
        }
    }

    private fun setButtonState() {
        btn_done.isEnabled = edt_transCost.text.toString() != ""
    }

    private fun setViewState(){
        tv_unit.isGone = edt_transCost.text.toString() != ""
        btn_cancel.isGone = edt_transCost.text.toString() == ""
    }

    private fun String.goCategoryIntent() {
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@TransportationPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@TransportationPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@TransportationPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@TransportationPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@TransportationPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@TransportationPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }
}
