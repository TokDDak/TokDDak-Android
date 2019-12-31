package com.sopt.tokddak.feature.planning.lodgement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_lodgement_planning.*
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class LodgementPlanningActivity : AppCompatActivity(), View.OnClickListener {

    var selectedCategoryList: ArrayList<String>? = ArrayList()
    var isChecked = arrayListOf<Boolean>(false, false, false, false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lodgement_planning)

        val intent = getIntent()
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
    }

    fun init() {
        //onclick listener
        img_toBack.setOnClickListener(this)
        img_highest.setOnClickListener(this)
        btn_done.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_toBack -> {
                finish()
            }
            R.id.img_highest -> {

                if (isChecked[0] == false) {
                    // 선택되어 있지 않을 경우
                    isChecked[0] = true

                    // activity view 변경
                    img_highest.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.make_btn_stay_top_active,
                            null
                        )
                    )

                    // 서버 통신 (avgPrice, example list, url 받아오기) --> 함수
                    // fragment 생성 시 넘기기 --> 함수

                    // dialog fragment 시작
                    val fm = LodgementPopFragment("최고급 호텔", 200000, "url")
                    fm.show(supportFragmentManager, "highest hotel dialog")
                } else {
                    // 선택되어 있을 경우
                    isChecked[0] = false

                    //activity view 변경
                    img_highest.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.make_btn_stay_top,
                            null
                        )
                    )

                    // 서버에 넘길 데이터 변경
                    setTotal(0)
                }
            }

            R.id.img_high -> {

            }

            R.id.btn_done -> {
                // 서버에 숙박 정보 전달 (함수로 구현,,)
            }
        }
    }

    // 금액 표기 변환
    fun decimalFormat(input: Int): String {
        val formatter = DecimalFormat("###,###")
        return formatter.format(input)
    }

    fun setTotal(idx: Int){
        val deletedData = Lodgement.userData[idx]!!
        Lodgement.totalCount -= deletedData
        Lodgement.totalPrice -= (deletedData * 200000)
        Lodgement.userData.remove(idx)

        tv_count.text = Lodgement.totalCount.toString()
        tv_price.text = decimalFormat(Lodgement.totalPrice)
    }


    // 모듈화 -> 서버에서 받은 정보 리스트에서 꺼내 쓰기 (avg, url 등), idx 이용
    fun whenSelected(idx: Int, selectedItem: ImageView){
        if (isChecked[0] == false) {
            // 선택되어 있지 않을 경우
            isChecked[0] = true

            // activity view 변경
            img_highest.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.make_btn_stay_top_active,
                    null
                )
            )

            // 서버 통신 (avgPrice, example list, url 받아오기) --> 함수
            // fragment 생성 시 넘기기 --> 함수

            // dialog fragment 시작
            val fm = LodgementPopFragment("최고급 호텔", 200000, "url")
            fm.show(supportFragmentManager, "highest hotel dialog")
        } else {
            // 선택되어 있을 경우
            isChecked[0] = false

            //activity view 변경
            img_highest.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.make_btn_stay_top,
                    null
                )
            )

            // 서버에 넘길 데이터 변경
            setTotal(0)
        }
    }
}
