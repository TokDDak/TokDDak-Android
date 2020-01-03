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
import com.sopt.tokddak.api.ActivityData
import com.sopt.tokddak.api.GetActivityData
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.Activity
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.result.PlanningResultActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_activites_planning.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitesPlanningActivity : AppCompatActivity() {
    private val activities = mutableListOf<Activity>()
    private val activityAdapter: ActivityRvAdapter = ActivityRvAdapter()

    var selectedCategoryList: ArrayList<String> = ArrayList()

    var activitiesData = arrayListOf<ActivityData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activites_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")!!

        init()
        makeDummy()
    }

    override fun onResume() {
        super.onResume()
        TripInfo.tripTotalCost -= TripInfo.activityInfo.map { it.price }.sum()
        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        Log.d("총 금액", TripInfo.tripTotalCost.toString())
        Log.d("총 금액 액티", activities.toString())

    }

    private fun init() {
        rv_activities.adapter = activityAdapter
        rv_activities.layoutManager = LinearLayoutManager(this)

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.activityInfo += activities.filter { it.flag }
            TripInfo.tripTotalCost += activities.filter { it.flag }.map { it.price }.sum()
            // Log.d("테스트", TripInfo.activityInfo.toString())

            Log.d("테스트", selectedCategoryList.toString())
            if (selectedCategoryList.isNullOrEmpty()) {
                // TODO: 예산 산정 완료 뷰, activity stack clear
                val intent = Intent(this, PlanningResultActivity::class.java)
                startActivity(intent)
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
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@ActivitesPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@ActivitesPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@ActivitesPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(
                this@ActivitesPlanningActivity,
                TransportationPlanningActivity::class.java
            )
            "쇼핑" -> Intent(this@ActivitesPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@ActivitesPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
        }
        startActivity(categoryIntent)
    }

    fun getActivity(){
        val call: Call<GetActivityData> =
            PlanningServiceImpl.planningService.getActivity(
                "application/json",
                1
            )

        call.enqueue(
            object : Callback<GetActivityData> {

                override fun onFailure(call: Call<GetActivityData>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<GetActivityData>,
                    response: Response<GetActivityData>
                ) {
                    if (response.isSuccessful){
                        if(response.body()!!.status == 200){
                            Log.d("테스트", "성공")
                            val temp = response.body()!!.data
                            if(temp.isNotEmpty()){
                                activitiesData.addAll(temp)
                            }
                        } else{
                            Log.d("테스트", response.body()!!.status.toString())
                        }
                    } else{
                        Log.d("테스트", response.isSuccessful.toString())
                        Log.d("테스트", response.toString())
                    }
                }
            }
        )
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
                tv_totalPrice.text =
                    (TripInfo.tripTotalCost + activities.filter { it.flag }.map { it.price }.sum()).toString()
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
