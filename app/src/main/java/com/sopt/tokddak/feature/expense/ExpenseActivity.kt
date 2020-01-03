package com.sopt.tokddak.feature.expense


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import com.sopt.tokddak.api.ServiceImpl
import kotlinx.android.synthetic.main.activity_expense.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//숙박 식사 간식 교통 쇼핑 액티비티
class ExpenseActivity : AppCompatActivity() {
    private var type = ""
    private lateinit var btns: List<View>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        btn_checked.isEnabled = false
        checking()
        Log.v("YGYG", tv_extense_input.text.isNotEmpty().toString())

        tv_extense_input.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                setCheckButtonState()
            }
        )

        btn_checked.setOnClickListener {
            val cost = tv_extense_input.text.toString().toIntOrNull()
            val detail = tv_detail_input.toString()
            Log.v("YGYG", "In2")


            if (cost == null) {
                //TODO: 코스트 입력하라고 토스트 띄우고
                Toast.makeText(this, "금액을 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                //TODO: 서버로 데이터를 보내줌! 보내줄 때, 체킹된 버튼 확인해서 그에 대한 데이터를 보내야함
                val typeInt = mappingTypeIntString(type)

                sendData(cost, typeInt, detail)
            }
        }

        btn_back_black.setOnClickListener {
            finish()
        }

        btn_checked.setOnClickListener {
            // 통신 함수
            finish()
        }


    }

    private fun sendData(cost: Int, category: Int, content: String) {
        Log.v("YGYG", "In")

        val postExpense = ServiceImpl.service.setExpense(PostExpense(1, category, content, 1, cost))
        postExpense.enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.v("YGYG", t.toString())

            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ExpenseActivity, "성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.v("YGYG", response.errorBody().toString())

                }
            }

        })

//        setExpense.setExpense().enqueue(object : Callback<ExpenseServerItem>{
//            override fun onFailure(call: Call<ExpenseServerItem>, t: Throwable) {
//                Log.e("서버 보내기 오류", "t = $t")
//            }
//
//            override fun onResponse(
//                call: Call<ExpenseServerItem>,
//                response: Response<ExpenseServerItem>
//            ) {
//                if(response.isSuccessful){
//                    val sendData = response.body()!!
//                }
//            }
//        })
    }

    private fun checking() {
        btns = listOf<View>(
            btn_stay, btn_food, btn_snacks,
            btn_traffic, btn_shopping, btn_activity
        )

        for (btn in btns) {
            btn.setOnClickListener {
                type = btn.tag.toString()
                btns.forEach { it.isEnabled = true }
                btn.isEnabled = false
                setCheckButtonState()
            }
        }
    }

    private fun setCheckButtonState() {
        btn_checked.isEnabled =
            type.isNotEmpty() &&
                    tv_extense_input.text.isNotEmpty()

        Log.v("YGYG", tv_extense_input.text.isNotEmpty().toString())
    }

    private fun mappingTypeIntString(type: String): Int {
        when (type) {
            "숙박" -> return 1
            "식사" -> return 2
            "간식/주류" -> return 3
            "교통" -> return 4
            "쇼핑" -> return 5
            "액티비티" -> return 6
        }
        return 0
    }
}