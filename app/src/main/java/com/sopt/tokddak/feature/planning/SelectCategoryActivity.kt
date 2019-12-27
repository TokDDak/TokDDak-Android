package com.sopt.tokddak.feature.planning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.fragment_before_trip.*

class SelectCategoryActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryRvAdapter
    var selectedCategoryList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        categoryAdapter = CategoryRvAdapter(this)
        rv_cateogry.adapter = categoryAdapter
        rv_cateogry.layoutManager = LinearLayoutManager(this)

        btn_done.setOnClickListener {
            getSelectedItem()

            val intent = Intent(this, PlanningActivity::class.java)
            intent.putExtra("selected category list", selectedCategoryList)
            startActivity(intent)
        }


        img_toBack.setOnClickListener {
            var temp = ""
            getSelectedItem()
            for(category in selectedCategoryList) {
                temp += category
            }
            Toast.makeText(this, temp, Toast.LENGTH_SHORT).show()
        }

    }

    fun getSelectedItem() {
        // arraylist 초기화
        selectedCategoryList.clear()

        // adapter의 flagList에서 true인 카테고리 리스트 생성 (intent로 넘기기)
        for (idx in 0..5) {
            if (categoryAdapter.flagList[idx] == true) {
                selectedCategoryList.add(categoryAdapter.categoryList[idx])
            }
        }
    }
}
