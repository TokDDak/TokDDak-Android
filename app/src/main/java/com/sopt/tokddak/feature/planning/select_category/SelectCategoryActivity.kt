package com.sopt.tokddak.feature.planning.select_category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.planning.TitleActivity
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.activity_select_category.btn_done
import kotlinx.android.synthetic.main.activity_select_category.img_toBack
import kotlinx.android.synthetic.main.activity_select_category.tv_count

class SelectCategoryActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryRvAdapter
    var selectedCategoryList = ArrayList<String>()

    private val callbackListener = (object :
        ClickCallbackListener {
        override fun callBack(count: Int) {
            tv_count.text = count.toString() + "단계 선택 완료"
            btn_done.isEnabled = (count != 0) // selectCategory 항상 notnull
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        init()

        categoryAdapter = CategoryRvAdapter(
            this,
            callbackListener
        )
        rv_cateogry.adapter = categoryAdapter
        rv_cateogry.layoutManager = LinearLayoutManager(this)

    }

    fun init() {

        btn_done.isEnabled = true

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            getSelectedItem()

            selectedCategoryList[0].goCategoryIntent()
        }
    }

    private fun getSelectedItem() {
        selectedCategoryList.clear()

        for (idx in 0..5) {
            if (categoryAdapter.flagList[idx]) {
                selectedCategoryList.add(categoryAdapter.categoryList[idx])
            }
        }
    }

    private fun String.goCategoryIntent() {
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@SelectCategoryActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@SelectCategoryActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@SelectCategoryActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@SelectCategoryActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@SelectCategoryActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@SelectCategoryActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }
}
