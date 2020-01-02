package com.sopt.tokddak.feature.planning.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.planning.Activity
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_activites_planning.*

class ActivitesPlanningActivity : AppCompatActivity() {
    private val activities = mutableListOf<Activity>()
    private val activityAdapter: ActivityRvAdapter = ActivityRvAdapter()

    var selectedCategoryList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activites_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
        makeDummy()
    }

    private fun init() {
        rv_activities.adapter = activityAdapter
        rv_activities.layoutManager = LinearLayoutManager(this)

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.activityInfo += activities.filter { it.flag }
            // Log.d("테스트", TripInfo.activityInfo.toString())

            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    private fun makeDummy() {
        activities.add(Activity("파리", 1000, null, R.drawable.img_test, false, null, "ㅎㅎㅎ"))
        activities.add(Activity("세부", 2000, null, R.drawable.img_test, false, null, "ㅋㅋㅋ"))
        activities.add(Activity("뉴욕", 3000, null, R.drawable.img_test, false, null, "ㅗㅗㅗ"))
        activityAdapter.notifyDataSetChanged()
    }

    private fun String.goCategoryIntent() {
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@ActivitesPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@ActivitesPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@ActivitesPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@ActivitesPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@ActivitesPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@ActivitesPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }

    private inner class ActivityRvAdapter :
        RecyclerView.Adapter<ActivityViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
            val view: View = layoutInflater.inflate(R.layout.rv_item_activities, parent, false)
            return ActivityViewHolder(view)
        }

        override fun getItemCount(): Int {
            return activities.size
        }

        override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
            holder.bind(activities[position])
        }
    }

    private inner class ActivityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgActivity: ImageView = v.findViewById(R.id.img_activity)
        val tvName: TextView = v.findViewById(R.id.tv_activityName)
        val tvPrice: TextView = v.findViewById(R.id.tv_price)
        val btnSelect: ImageView = v.findViewById(R.id.btn_select)
        val ctnActivity: ConstraintLayout = v.findViewById(R.id.ctn_activity)

        fun bind(data: Activity) {
            tvName.text = data.name
            tvPrice.text = data.price.toString()
            imgActivity.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    data.actImg,
                    null
                )
            )

            btnSelect.setOnClickListener {
                if (data.flag) {
                    setButtonInactive()
                    data.flag = false
                } else {
                    setButtonActive()
                    data.flag = true
                }
                tv_totalCount.text = activities.filter { it.flag }.size.toString()
                tv_totalPrice.text = activities.filter { it.flag }.map { it.price }.sum().toString()
            }

            ctnActivity.setOnClickListener {
                val fm = ActivityDetailFragment(data.name, data.price, data.detailInfo)
                fm.show(supportFragmentManager, null)
            }
        }

        fun setButtonActive() {
            btnSelect.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.btn_select_active,
                    null
                )
            )
        }

        fun setButtonInactive() {
            btnSelect.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    R.drawable.btn_select,
                    null
                )
            )
        }
    }
}
