package com.sopt.tokddak.feature.planning.transportation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.sopt.tokddak.R
import com.sopt.tokddak.api.GetTransData
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.result.PlanningResultActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import kotlinx.android.synthetic.main.activity_transportation_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransportationPlanningActivity : AppCompatActivity() {

    var selectedCategoryList: ArrayList<String> = ArrayList()
    var transCost = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transportation_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
    }

    override fun onResume() {
        super.onResume()
        TripInfo.tripTotalCost -= transCost

        Log.d("총 금액", TripInfo.tripTotalCost.toString())

    }

    fun init() {
        getTransImage()

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
            transCost = edt_transCost.text.toString().toInt()
            TripInfo.transInfo = transCost
            TripInfo.tripTotalCost += transCost

            if (selectedCategoryList.isNullOrEmpty()) {
                val intent = Intent(this, PlanningResultActivity::class.java)
                startActivity(intent)
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

    private fun setViewState() {
        tv_unit.isGone = edt_transCost.text.toString() != ""
        btn_cancel.isGone = edt_transCost.text.toString() == ""
    }

    private fun String.goCategoryIntent() {
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(
                this@TransportationPlanningActivity,
                LodgementPlanningActivity::class.java
            )
            "식사" -> Intent(this@TransportationPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(
                this@TransportationPlanningActivity,
                SnackPlanningActivity::class.java
            )
            "교통" -> Intent(
                this@TransportationPlanningActivity,
                TransportationPlanningActivity::class.java
            )
            "쇼핑" -> Intent(
                this@TransportationPlanningActivity,
                ShoppingPlanningActivity::class.java
            )
            "액티비티" -> Intent(
                this@TransportationPlanningActivity,
                ActivitesPlanningActivity::class.java
            )
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
        }
        startActivity(categoryIntent)
    }

    fun getTransImage() {
        val call: Call<GetTransData> =
            PlanningServiceImpl.planningService.getTransportation(
                "application/json",
                2
            )

        call.enqueue(
            object : Callback<GetTransData> {

                override fun onFailure(call: Call<GetTransData>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<GetTransData>,
                    response: Response<GetTransData>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {
                            Glide.with(this@TransportationPlanningActivity)
                                .load(response.body()!!.data[0].img).into(img_transInfo)

                        }
                    }
                }
            }
        )
    }
}
