package com.sopt.tokddak.feature.planning.shopping

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
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_shopping_planning.*

class ShoppingPlanningActivity : AppCompatActivity() {

    var selectedCategoryList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
    }

    fun init() {

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        btn_cancel.isGone = true
        btn_done.isEnabled = false
        edt_shoppingCost.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                setButtonState()
                setViewState()
            }
        )

        btn_done.setOnClickListener {
            val shoppingCost = edt_shoppingCost.toString().toInt()
            TripInfo.shoppingInfo = shoppingCost
            TripInfo.tripTotalCost += shoppingCost

            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()

        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_cancel.setOnClickListener {
            edt_shoppingCost.text.clear()
        }
    }

    private fun setButtonState() {
        btn_done.isEnabled = edt_shoppingCost.text.toString() != ""
    }
    
    private fun setViewState(){
        tv_unit.isGone = edt_shoppingCost.text.toString() != ""
        btn_cancel.isGone = edt_shoppingCost.text.toString() == ""
    }

    private fun String.goCategoryIntent() {
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@ShoppingPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@ShoppingPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@ShoppingPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@ShoppingPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@ShoppingPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@ShoppingPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }
}
