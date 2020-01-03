package com.sopt.tokddak.feature.planning.shopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.sopt.tokddak.R
import com.sopt.tokddak.api.GetShoppingData
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_shopping_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingPlanningActivity : AppCompatActivity() {

    var selectedCategoryList: ArrayList<String> = ArrayList()
    var shoppingCost = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
    }

    override fun onResume() {
        super.onResume()
        TripInfo.tripTotalCost -= shoppingCost

        Log.d("총 금액", TripInfo.tripTotalCost.toString())

    }

    fun init() {

        getShoppingImage()

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
            shoppingCost = edt_shoppingCost.text.toString().toInt()

            Log.d("더하기 전", TripInfo.tripTotalCost.toString())
            TripInfo.shoppingInfo = shoppingCost
            TripInfo.tripTotalCost += shoppingCost

            Log.d("토탈 코스트", TripInfo.tripTotalCost.toString())
            Log.d("쇼핑 코스트", shoppingCost.toString())

            if (selectedCategoryList.isNullOrEmpty()) {
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

    private fun setViewState() {
        tv_unit.isGone = edt_shoppingCost.text.toString() != ""
        btn_cancel.isGone = edt_shoppingCost.text.toString() == ""
    }

    private fun String.goCategoryIntent() {
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@ShoppingPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@ShoppingPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@ShoppingPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(
                this@ShoppingPlanningActivity,
                TransportationPlanningActivity::class.java
            )
            "쇼핑" -> Intent(this@ShoppingPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@ShoppingPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
        }
        startActivity(categoryIntent)
    }

    fun getShoppingImage() {
        val call: Call<GetShoppingData> =
            PlanningServiceImpl.planningService.getShopping(
                "application/json",
                2
            )

        call.enqueue(
            object : Callback<GetShoppingData> {

                override fun onFailure(call: Call<GetShoppingData>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<GetShoppingData>,
                    response: Response<GetShoppingData>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {
                            Glide.with(this@ShoppingPlanningActivity)
                                .load(response.body()!!.data[0].img).into(img_shoppingInfo)

                        }
                    }
                }
            }
        )
    }
}
