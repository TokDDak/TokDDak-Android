package com.sopt.tokddak.feature.planning.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.data.Activity
import com.sopt.tokddak.data.Destination
import com.sopt.tokddak.feature.planning.ClickCallbackListener
import com.sopt.tokddak.feature.planning.lodgement.Lodgement.totalCount
import kotlinx.android.synthetic.main.activity_activites_planning.*
import kotlinx.android.synthetic.main.activity_select_category.*

class ActivitesPlanningActivity : AppCompatActivity(), View.OnClickListener {
    private val activities = mutableListOf<Activity>()
    private val activityAdapter: ActivityRvAdapter = ActivityRvAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activites_planning)

        init()
        makeDummy()
    }

    private fun init() {
        rv_activities.adapter = activityAdapter
        rv_activities.layoutManager = LinearLayoutManager(this)
    }

    private fun makeDummy() {
        activities.add(Activity("파리", 1000, null, R.drawable.img_test, false))
        activities.add(Activity("세부", 2000, null, R.drawable.img_test, false))
        activities.add(Activity("뉴욕", 3000, null, R.drawable.img_test, false))
        activityAdapter.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_toBack -> finish()
            R.id.btn_done -> {
                // 서버 통신
            }
        }
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

            holder.btnSelect.setOnClickListener {
                if (activities[position].flag) {
                    holder.setButtonInactive()
                    activities[position].flag = false
                } else {
                    holder.setButtonActive()
                    activities[position].flag = true
                }
                tv_totalCount.text = activities.filter { it.flag }.size.toString()
                tv_totalPrice.text = activities.filter { it.flag }.map { it.price }.sum().toString()
            }
        }
    }

    private inner class ActivityViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgActivity: ImageView = v.findViewById(R.id.img_activity)
        val tvName: TextView = v.findViewById(R.id.tv_activityName)
        val tvPrice: TextView = v.findViewById(R.id.tv_price)
        val btnSelect: ImageView = v.findViewById(R.id.btn_select)

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
