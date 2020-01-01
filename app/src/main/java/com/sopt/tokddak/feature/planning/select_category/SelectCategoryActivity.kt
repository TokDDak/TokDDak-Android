package com.sopt.tokddak.feature.planning.select_category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.planning.TitleActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.activity_select_category.btn_done
import kotlinx.android.synthetic.main.activity_select_category.img_toBack
import kotlinx.android.synthetic.main.activity_select_category.tv_count

class SelectCategoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var categoryAdapter: CategoryRvAdapter
    var selectedCategoryList = ArrayList<String>()

    private val callbackListener = (object :
        ClickCallbackListener {
        override fun callBack(count: Int) {
            tv_count.text = count.toString() + "단계 선택 완료"
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
        // onclick listener
        img_toBack.setOnClickListener(this)
        btn_done.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_toBack -> finish()
            R.id.btn_done -> {
                getSelectedItem()

                if (selectedCategoryList.isEmpty()) {
                    //TODO: 아이템이 비었을 때 처리
                    return
                }
                selectedCategoryList[0].goCategoryIntent()
                // selected list 확인 후 0번째 index activity 넘기기
                when (selectedCategoryList[0]) {
                    "숙박" -> {
                        selectedCategoryList.removeAt(0) // 넘어갈 activity는 selected category에서 제거 후 putextra

                        val intent = Intent(this, LodgementPlanningActivity::class.java)
                        intent.putExtra("selected category list", selectedCategoryList)
                        startActivity(intent)
                    }
                    "식사" -> {
                        selectedCategoryList.removeAt(0)
                        val intent = Intent(this, TitleActivity::class.java)
                        startActivity(intent)
                    }
                    "주류 및 간식" -> {
                        selectedCategoryList.removeAt(0)
                        val intent = Intent(this, TitleActivity::class.java)
                        startActivity(intent)
                    }
                    "교통" -> {
                        selectedCategoryList.removeAt(0)
                        val intent = Intent(this, TitleActivity::class.java)
                        startActivity(intent)
                    }
                    "쇼핑" -> {
                        selectedCategoryList.removeAt(0)
                        val intent = Intent(this, TitleActivity::class.java)
                        startActivity(intent)
                    }
                    "액티비티" -> {
                        selectedCategoryList.removeAt(0)
                        val intent = Intent(this, TitleActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun String.goCategoryIntent() {
        val categoryIntent = when(this) {
            "액티비티" -> Intent(this@SelectCategoryActivity, TitleActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }
}
