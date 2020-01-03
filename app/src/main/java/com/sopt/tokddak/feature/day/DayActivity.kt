package com.sopt.tokddak.feature.day


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.bottom.BottomSheetDialog
import com.sopt.tokddak.feature.main.MainActivity
import com.sopt.tokddak.feature.main.OnTripFragment
import kotlinx.android.synthetic.main.activity_day.*


class DayActivity : AppCompatActivity() {

    private lateinit var rv_outer : RecyclerView
    private lateinit var dayAdapter: DayAdapter
    private lateinit var divider : DividerItemDecoration

    private val dayData = DayData()

    private lateinit var img_bar : ImageView

    private lateinit var dayContents : ArrayList<ArrayList<String>>

    private var dayCount = 3

    private var currentDay = 0

    private lateinit var ctnPerfect : ConstraintLayout

    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        ctnPerfect = findViewById(R.id.ctn_perfect)
        init()
        outer()

        img_bar = findViewById(R.id.img_bar)
        img_bar.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(supportFragmentManager, "tokddak")
        }

        img_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        ctnPerfect.setOnClickListener {
            val intent = Intent(this, OnTripFragment::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun init(){
        dayContents = ArrayList()

//        for(i in 0 .. dayCount){
//            //day i번째에 대한 통신
//            dayContents[i].add("고급호텔")
//        }
        dayContents.add(arrayListOf("고급호텔", "일반호텔"))
        dayContents.add(arrayListOf("카페", "노잼", "간편식"))
        dayContents.add(arrayListOf("일반호텔"))

    }

    private fun outer() {
        divider = DividerItemDecoration(this, 0)
        divider.setDrawable(resources.getDrawable(R.drawable.rv_divider))

        rv_outer = findViewById(R.id.rv_outer)

        dayAdapter = DayAdapter(this, dayContents)
        rv_outer.adapter = dayAdapter
        rv_outer.addItemDecoration(divider)

        layoutManager = LinearLayoutManager(this, HORIZONTAL, false)

        rv_outer.layoutManager = layoutManager
        LinearSnapHelper().attachToRecyclerView(rv_outer)
        dayAdapter.data = dayData.getDay()
        dayAdapter.notifyDataSetChanged()
    }

    fun getContents(text : String){
        Log.v("Day", text)
        Log.v("YGYG", layoutManager.findFirstVisibleItemPosition().toString())
        val idx = layoutManager.findFirstVisibleItemPosition()
        dayContents[idx].add(text)
        dayAdapter.dayContents = dayContents
        dayAdapter.notifyDataSetChanged()
        dayAdapter.updateDay()
    }

}