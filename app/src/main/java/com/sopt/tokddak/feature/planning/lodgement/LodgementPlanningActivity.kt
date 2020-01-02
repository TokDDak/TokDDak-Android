package com.sopt.tokddak.feature.planning.lodgement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_lodgement_planning.*
import kotlin.collections.ArrayList

class LodgementPlanningActivity : AppCompatActivity() {
    var selectedCategoryList: ArrayList<String> = ArrayList()
    private lateinit var btns: List<ImageView>
    private lateinit var btnToggleMap: Map<View, Pair<Int, Int>>

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

    private fun init() {

        tv_price.text = TripInfo.tripTotalCost.toDecimalFormat()

        btns.forEach { view ->
            view.setOnClickListener {
                val type = view.tag.toString()
                if (!view.isSelected) {
                    //TODO: avgPrice 받아오기
                    val fm = LodgementPopFragment(type, 200000, "url") {
                        updateUi()
                    }
                    fm.show(supportFragmentManager, null)
                } else {
                    TripInfo.lodgementInfo -= TripInfo.lodgementInfo.find { it.type == type }
                        ?: return@setOnClickListener
                    updateUi()
                }

            }
        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.tripTotalCost += TripInfo.lodgementInfo.map { it.count * it.avgPrice }.sum()
            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    private fun updateUi() {
        TripInfo.lodgementInfo.let { list ->
            // tv_count.text = list.size.toString()
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
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@LodgementPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@LodgementPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@LodgementPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@LodgementPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@LodgementPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@LodgementPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }
}
