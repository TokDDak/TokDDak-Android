package com.sopt.tokddak.feature.planning

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.planning.select_category.SelectCategoryActivity
import kotlinx.android.synthetic.main.activity_title.*
import java.util.*

class TitleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        init()

    }

    fun init(){

        TripInfo.clear()

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
            val intent = Intent(this, DatePickActivity::class.java)
            startActivityForResult(intent, 1818)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1818 && resultCode == Activity.RESULT_OK){
            tv_startDate.text = data!!.getStringExtra("startDate")
            tv_endDate.text = data!!.getStringExtra("endDate")
            tv_startDate.setTextColor(Color.parseColor("#151617"))
            tv_endDate.setTextColor(Color.parseColor("#151617"))
        }
    }
}
