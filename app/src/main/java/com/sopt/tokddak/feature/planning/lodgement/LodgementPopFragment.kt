package com.sopt.tokddak.feature.planning.lodgement


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import com.sopt.tokddak.R
import com.sopt.tokddak.api.LodgeSample
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.Lodgement
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.WebViewActivity
import kotlinx.android.synthetic.main.activity_activites_planning.*
import kotlinx.android.synthetic.main.fragment_lodgement_pop.*
import kotlinx.android.synthetic.main.fragment_lodgement_pop.btn_done
import kotlinx.android.synthetic.main.fragment_lodgement_pop.tv_totalPrice

class LodgementPopFragment(
    var type: String,
    var avgPrice: Int,
    var url: String,
    var samples: ArrayList<LodgeSample>,
    val dismissListener: () -> Unit = {}
) : DialogFragment(),
    View.OnClickListener {

    var count: Int = 1 // default, x 버튼 누를 경우 count = 0으로 변경!
    lateinit var fmManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_lodgement_pop, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // radius 적용
        dialog?.setCanceledOnTouchOutside(false) // background touch 방지

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fmManager = activity!!.supportFragmentManager
    }

    override fun onStart() {
        super.onStart()
        init()
        setSample()
    }

    override fun onResume() {
        super.onResume()

        // dialog size setting
        val dialogWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
        val dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissListener()
    }

    fun init() {
        // 뷰 설정
        tv_type.text = type
        tv_avgPrice.text = (avgPrice).toDecimalFormat()
        tv_totalPrice.text = (avgPrice).toDecimalFormat()

        // onclick listener 등록
        btn_done.setOnClickListener(this)
        btn_minus.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        btn_close.setOnClickListener(this)
        img_moreHotel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_done -> {

                // lodgement object 정보 변경
                TripInfo.lodgementInfo += Lodgement(
                    type, count, avgPrice
                )

                // fragment 종료 --> 안될 경우 activity 함수 호출하는 방법으로 변경
                fmManager.beginTransaction().remove(this@LodgementPopFragment).commit()
                fmManager.popBackStack()
            }

            R.id.btn_minus -> {
                count = tv_count.text.toString().toInt()
                if (count != 1) {
                    count -= 1
                    tv_count.text = count.toString()

                    // total price 변경
                    tv_totalPrice.text = (avgPrice * count).toDecimalFormat()
                }
            }

            R.id.btn_plus -> {
                count = tv_count.text.toString().toInt()
                count += 1
                tv_count.text = count.toString()

                // total price 변경
                tv_totalPrice.text = (avgPrice * count).toDecimalFormat()
            }

            R.id.btn_close -> {
                // fragment 닫기
                fmManager.beginTransaction().remove(this@LodgementPopFragment).commit()
                fmManager.popBackStack()
            }

            R.id.img_moreHotel -> {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        }
    }

    fun setSample() {
        tv_firstHotel.text = samples[0].name
        tv_firstPrice.text = samples[0].cost.toDecimalFormat() + "원"

        tv_secondHotel.text = samples[1].name
        tv_secondPrice.text = samples[1].cost.toDecimalFormat() + "원"

        tv_thirdHotel.text = samples[2].name
        tv_thirdPrice.text = samples[2].cost.toDecimalFormat() + "원"
    }
}
