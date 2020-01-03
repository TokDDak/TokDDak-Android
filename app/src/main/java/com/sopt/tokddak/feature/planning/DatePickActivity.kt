package com.sopt.tokddak.feature.planning

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDateFormat
import kotlinx.android.synthetic.main.activity_date_pick.*
import java.text.SimpleDateFormat
import java.util.*

class DatePickActivity : AppCompatActivity() {

    lateinit var startDate: Date
    lateinit var endDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_pick)

        init()

        val startPicker = findViewById<DatePicker>(R.id.datePicker_start)
        val endPicker = findViewById<DatePicker>(R.id.datePicker_end)

        startPicker.setOnDateChangedListener { datePicker, year, month, day ->
            startDate = SimpleDateFormat(
                "yyyy.MM.dd",
                Locale.getDefault()
            ).parse(year.toString() + "." + (month + 1).toString() + "." + day.toString())!!
        }

        endPicker.setOnDateChangedListener { datePicker, year, month, day ->
            endDate = SimpleDateFormat(
                "yyyy.MM.dd",
                Locale.getDefault()
            ).parse(year.toString() + "." + (month + 1).toString() + "." + day.toString())!!
        }
    }

    fun init() {

        startDate = Calendar.getInstance().time
        endDate = Calendar.getInstance().time

        btn_cancel.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            Log.d("테스트", "성공")
            TripInfo.startDate = startDate
            TripInfo.endDate = endDate

            // go back
            val intent = Intent(this, TitleActivity::class.java)
            intent.putExtra("startDate", startDate.toDateFormat())
            intent.putExtra("endDate", endDate.toDateFormat())
            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }
}
