package com.sopt.tokddak.feature.planning.activity


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.WebViewActivity
import kotlinx.android.synthetic.main.fragment_activity_detail.*
import kotlinx.android.synthetic.main.rv_item_activities.tv_activityName
import kotlinx.android.synthetic.main.rv_item_activities.tv_price

/**
 * A simple [Fragment] subclass.
 */
class ActivityDetailFragment(
    val name: String,
    val price: Int,
    val detailInfo: String,
    val url_klook: String,
    val url_mrt: String
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_activity_detail, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // radius 적용
        dialog?.setCanceledOnTouchOutside(false) // background touch 방지

        return view
    }

    override fun onResume() {
        super.onResume()

        // dialog size setting
        val dialogWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
        val dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    private fun init() {
        tv_activityName.text = name
        tv_price.text = price.toDecimalFormat()
        tv_detailInfo.text = detailInfo

        btn_done.setOnClickListener {
            val fmManager: FragmentManager = activity!!.supportFragmentManager
            fmManager.beginTransaction().remove(this@ActivityDetailFragment).commit()
            fmManager.popBackStack()
        }

        btn_goKlook.setOnClickListener {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", url_klook)
            startActivity(intent)
        }

        btn_goRealTrip.setOnClickListener {
            Log.d("테스트", url_mrt.toString())
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", url_mrt)
            startActivity(intent)
        }
    }

}
