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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sopt.tokddak.R
import com.sopt.tokddak.api.GetActivityData
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.ActivityData
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
    // private val activities = mutableListOf<ActivityData>()

    var selectedCategoryList: ArrayList<String> = ArrayList()

    var activitiesData = mutableListOf<ActivityData>()

    private lateinit var activityAdapter: ActivityRvAdapter

    var totalBudgetInAct = 0
    var intentBudget = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activites_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")!!
        totalBudgetInAct = intent.getIntExtra("budget", totalBudgetInAct)

        getActivity()
        init()
    }

    override fun onResume() {
        super.onResume()
        // TripInfo.tripTotalCost -= TripInfo.activityInfo.map { it.cost }.sum()
        intentBudget = totalBudgetInAct

    }

    private fun init() {
        tv_totalPrice.text = totalBudgetInAct.toDecimalFormat()

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.activityInfo += activitiesData.filter { it.flag }
            intentBudget += activitiesData.filter { it.flag }.map { it.cost }.sum()

            if (selectedCategoryList.isNullOrEmpty()) {
                val intent = Intent(this, PlanningResultActivity::class.java)
                intent.putExtra("budget", intentBudget)
                startActivity(intent)
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
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
            putExtra("budget", intentBudget)
        }
        startActivity(categoryIntent)
    }

    private fun getActivity(){
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
                            val temp = response.body()!!.data
                            if(temp.isNotEmpty()){
                                activitiesData.addAll(temp)

                                activityAdapter = ActivityRvAdapter()
                                rv_activities.adapter = activityAdapter
                                rv_activities.layoutManager = LinearLayoutManager(this@ActivitesPlanningActivity)
                                activityAdapter.notifyDataSetChanged()
                            }
                        } else{
                        }
                    } else{
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
            return activitiesData.size
        }

        override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
            holder.bind(activitiesData[position])
        }
    }

    private inner class ActivityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgActivity: ImageView = v.findViewById(R.id.img_activity)
        val tvName: TextView = v.findViewById(R.id.tv_activityName)
        val tvPrice: TextView = v.findViewById(R.id.tv_price)
        val btnSelect: ImageView = v.findViewById(R.id.btn_select)
        val ctnActivity: ConstraintLayout = v.findViewById(R.id.ctn_activity)

        fun bind(data: ActivityData) {
            tvName.text = data.name
            tvPrice.text = data.cost.toDecimalFormat() + "원"
            Glide.with(this@ActivitesPlanningActivity).load(data.img).transform(RoundedCorners(3)).into(imgActivity)
            imgActivity.setBackgroundResource(R.drawable.bg_popular)

            btnSelect.setOnClickListener {
                if (data.flag) {
                    setButtonInactive()
                    data.flag = false
                } else {
                    setButtonActive()
                    data.flag = true
                }
                tv_totalCount.text = activitiesData.filter { it.flag }.size.toString()
                tv_totalPrice.text =
                    (intentBudget + activitiesData.filter { it.flag }.map { it.cost }.sum()).toString()
            }

            ctnActivity.setOnClickListener {
                val fm = ActivityDetailFragment(data.name, data.cost, data.content, data.url_kl, data.url_mrt)
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
