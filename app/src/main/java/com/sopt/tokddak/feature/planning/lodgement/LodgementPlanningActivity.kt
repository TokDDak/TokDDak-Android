package com.sopt.tokddak.feature.planning.lodgement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.sopt.tokddak.R
import com.sopt.tokddak.api.GetLodgeData
import com.sopt.tokddak.api.LodgeData
import com.sopt.tokddak.api.PlanningService
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.result.PlanningResultActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_lodgement_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class LodgementPlanningActivity : AppCompatActivity() {
    var selectedCategoryList: ArrayList<String> = ArrayList()
    private lateinit var btns: List<ImageView>
    private lateinit var btnToggleMap: Map<View, Pair<Int, Int>>

    private var lodgeDataArray = arrayListOf<LodgeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lodgement_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        btns = listOf(
            img_highest, img_high, img_general, img_cheap
        )
        btnToggleMap = mapOf(
            img_highest to (R.drawable.make_btn_stay_top_active to R.drawable.make_btn_stay_top),
            img_high to (R.drawable.make_btn_stay_high_active to R.drawable.make_btn_stay_high),
            img_general to (R.drawable.make_btn_stay_general_active to R.drawable.make_btn_stay_general),
            img_cheap to (R.drawable.make_btn_stay_cheap_active to R.drawable.make_btn_stay_cheap)
        )

        init()
    }

    override fun onResume() {
        super.onResume()
        TripInfo.tripTotalCost -= TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum()
        Log.d("숙박", TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum().toString())
        Log.d("총 금액", TripInfo.tripTotalCost.toString())
    }

    private fun init() {

        tv_price.text = TripInfo.tripTotalCost.toDecimalFormat()

        getLodgementData()

        btns.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                val type = imageView.tag.toString()
                if (!imageView.isSelected) {
                    Log.d("테스트 나중에", lodgeDataArray.toString())
                    val fm = LodgementPopFragment(
                        lodgeDataArray[index].type,
                        lodgeDataArray[index].avgPrice,
                        lodgeDataArray[index].url,
                        lodgeDataArray[index].info
                    ) {
                        updateUi()
                    }
                    fm.show(supportFragmentManager, null)
                } else {
                    TripInfo.lodgementInfo -= TripInfo.lodgementInfo.find { it.type == type }
                        ?: return@setOnClickListener
                    Log.d("숙박", TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum().toString())
                    updateUi()
                }

            }
        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.tripTotalCost += TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum()
            Log.d("숙박", TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum().toString())
            if (selectedCategoryList.isNullOrEmpty()) {
                val intent = Intent(this, PlanningResultActivity::class.java)
                startActivity(intent)
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    private fun updateUi() {
        TripInfo.lodgementInfo.let { list ->
            // tv_count.text = list.size.toString()
            Log.d("숙박", TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum().toString())
            tv_count.text = list.map { it.count }.sum().toString()
            tv_price.text = (TripInfo.tripTotalCost + list.map {
                it.count * it.avgPrice
            }.sum()).toDecimalFormat()

            btns.forEach { view ->
                view.isSelected = view.tag in list.map { it.type }
                view.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        if (view.isSelected) btnToggleMap[view]!!.first else btnToggleMap[view]!!.second,
                        null
                    )
                )
            }
        }
    }

    private fun String.goCategoryIntent() {
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@LodgementPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@LodgementPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@LodgementPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(
                this@LodgementPlanningActivity,
                TransportationPlanningActivity::class.java
            )
            "쇼핑" -> Intent(this@LodgementPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@LodgementPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
        }
        startActivity(categoryIntent)
    }

    fun getLodgementData() {
        val call: Call<GetLodgeData> =
            PlanningServiceImpl.planningService.getLodgement(
                "application/json",
                1
            )

        call.enqueue(
            object : Callback<GetLodgeData> {
                override fun onFailure(call: Call<GetLodgeData>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<GetLodgeData>,
                    response: Response<GetLodgeData>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {
                            val temp = response.body()!!.lodgeData.result
                            Log.d("테스트", response.body()!!.toString())
                            Log.d("테스트 temp", temp.toString())
                            if (temp.isNotEmpty()) {
                                lodgeDataArray.addAll(temp)
                            }
                            Log.d("테스트 list", lodgeDataArray.toString())
                        } else {
                            Log.d("테스트", "실패")
                        }
                    } else {
                        Log.d("테스트", "실패")
                    }
                }
            }
        )
    }
}