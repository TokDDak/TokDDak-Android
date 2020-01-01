package com.sopt.tokddak.feature.planning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_title.*

class TitleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        // initialize
        init()

    }

    fun init(){

        // bring to front
        ctn_dateSetter.bringToFront()
        img_arrow.bringToFront()

        btn_done.isEnabled = false
        edt_tripTitle.addTextChangedListener(
            onTextChanged = {_, _, _, _ ->
                setButtonState()
            }
        )

        img_toBack.setOnClickListener {
            finish()
        }

        ctn_dateSetter.setOnClickListener {
            // date picker activity
        }

        btn_done.setOnClickListener {
            TripInfo.title = edt_tripTitle.text.toString()
            // TODO: picker 화면에서 object 설정, tv 설정

            // select category activity
            val intent = Intent(this, SelectCategoryActivity::class.java)
            startActivity(intent)
        }

        tv_destination.text = TripInfo.destination
    }

    private fun setButtonState(){
        btn_done.isEnabled = edt_tripTitle.text.toString() != ""
    }
}
