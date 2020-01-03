package com.sopt.tokddak.feature.planning.result


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

import com.sopt.tokddak.R
import com.sopt.tokddak.feature.planning.WebViewActivity
import kotlinx.android.synthetic.main.fragment_result_pop.*

/**
 * A simple [Fragment] subclass.
 */
class ResultPopFragment : DialogFragment() {

    lateinit var fmManager: FragmentManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_result_pop, container, false)

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
    }

    override fun onResume() {
        super.onResume()

        // dialog size setting
        val dialogWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
        val dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    fun init(){
        btn_close.setOnClickListener {
            fmManager.beginTransaction().remove(this@ResultPopFragment).commit()
            fmManager.popBackStack()
        }

        btn_done.setOnClickListener{
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            activity!!.finish()


            fmManager.beginTransaction().remove(this@ResultPopFragment).commit()
            fmManager.popBackStack()
        }
    }


}
