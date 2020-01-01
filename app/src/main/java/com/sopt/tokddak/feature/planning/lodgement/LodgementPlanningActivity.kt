package com.sopt.tokddak.feature.planning.lodgement

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import kotlinx.android.synthetic.main.activity_lodgement_planning.*
import kotlin.collections.ArrayList

class LodgementPlanningActivity : AppCompatActivity() {
    var selectedCategoryList: ArrayList<String>? = ArrayList()
    private lateinit var btns: List<ImageView>
    private lateinit var btnToggleMap: Map<View, Pair<Int, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lodgement_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        if(selectedCategoryList.isNullOrEmpty()){
            // stack clear
        }

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
        //onclick listener
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
    }

    private fun updateUi() {
        TripInfo.lodgementInfo.let { list ->
            // tv_count.text = list.size.toString()
            tv_count.text = list.map { it.count }.sum().toString()
            tv_price.text = list.map {
                it.count * it.avgPrice
            }.sum().toDecimalFormat()

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
}
