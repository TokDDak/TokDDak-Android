package com.sopt.tokddak.feature.planning.lodgement


import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.fragment_lodgement_pop.*
import org.w3c.dom.Text
import java.text.DecimalFormat

class LodgementPopFragment(var type: String, var avgPrice: Int, var url: String) : DialogFragment(),
    View.OnClickListener {

    var count: Int = 1 // default, x 버튼 누를 경우 count = 0으로 변경!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_lodgement_pop, container, false)

        // val dialog: Dialog? = dialog
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // radius 적용
        dialog?.setCanceledOnTouchOutside(false) // background touch 방지

        return view
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onResume() {
        super.onResume()

        // dialog size setting
        val dialogWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
        val dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    fun init() {
        // 뷰 설정
        tv_type.text = type
        tv_avgPrice.text = decimalFormat(avgPrice)
        tv_totalPrice.text = decimalFormat(avgPrice)

        // onclick listener 등록
        btn_done.setOnClickListener(this)
        btn_minus.setOnClickListener(this)
        btn_plus.setOnClickListener(this)
        img_moreHotel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_done -> {

                // lodgement object 정보 변경
                Lodgement.totalCount += count
                Lodgement.totalPrice += (count * avgPrice)
                Lodgement.userData[0] = count
                Log.d("map", Lodgement.userData.toString())

                // activity view 변경
                setActivityView()

                // fragment 종료 --> 안될 경우 activity 함수 호출하는 방법으로 변경
                val fmManager: FragmentManager = activity!!.supportFragmentManager
                fmManager.beginTransaction().remove(this@LodgementPopFragment).commit()
                fmManager.popBackStack()
            }

            R.id.btn_minus -> {
                count = tv_count.text.toString().toInt()
                if( count != 1){
                    count -= 1
                    tv_count.text = count.toString()

                    // total price 변경
                    tv_totalPrice.text = decimalFormat(avgPrice * count)
                }
            }

            R.id.btn_plus -> {
                count = tv_count.text.toString().toInt()
                count += 1
                tv_count.text = count.toString()

                // total price 변경
                tv_totalPrice.text = decimalFormat(avgPrice * count)
            }
        }
    }

    // 금액 표기 변환
    fun decimalFormat(input: Int): String {
        val formatter = DecimalFormat("###,###")
        return formatter.format(input)
    }

    // !질문!
    fun setActivityView(){
        val tvActivityCount: TextView = activity!!.findViewById(R.id.tv_count)
        tvActivityCount.text = Lodgement.totalCount.toString()
        val tvActivityPrice: TextView = activity!!.findViewById<TextView>(R.id.tv_price)
        tvActivityPrice.text = decimalFormat(Lodgement.totalPrice)
    }

    /*// onCreateView에서 fragment의 dialog로 설정하는듯? 필요없을거같지만 일단 갖고잇어봅시다!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity?.layoutInflater?.inflate(R.layout.fragment_lodgement_pop, null)
        view?.findViewById<TextView>(R.id.tv_type)?.setOnClickListener {
            dismissDialog()
            Log.d("fragment button test", "성공")
        }
        builder.setView(view)

        return super.onCreateDialog(savedInstanceState)

    }

    private fun dismissDialog() {
        this.dismiss()
    }*/

    // constructor
    /*companion object {
        private const val ARG_DIALOG_MAIN_MSG = "dialog_main_msg"
        fun newinstance(mainMsg: String) = LodgementPopFragment().apply {
            arguments = bundleOf(
                ARG_DIALOG_MAIN_MSG to mainMsg
            )
        }
    }*/
}
