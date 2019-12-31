package com.sopt.tokddak.feature.planning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        // bring to front
        ctn_dateSetter.bringToFront()
        img_arrow.bringToFront()

        // initialize
        init()

    }

    fun init(){
        // onclick listener
        img_toBack.setOnClickListener(this)
        ctn_dateSetter.setOnClickListener(this)
        btn_done.setOnClickListener(this)

        // tv_destination (intent 받기)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.img_toBack -> finish()
            R.id.ctn_dateSetter -> {
                // date picker activity
            }
            R.id.btn_done -> {
                // select category activity
            }
        }
    }
}
