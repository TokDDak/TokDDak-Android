package com.sopt.tokddak.feature.planning.snack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.Snack
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.food.FoodPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_snack_planning.*
import kotlin.math.log

class SnackPlanningActivity : AppCompatActivity() {

    private val snacks = mutableListOf<Snack>()
    private val snackAdapter = SnackRvAdapter()

    var selectedCategoryList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
        setList()
    }

    override fun onResume() {
        super.onResume()

        TripInfo.tripTotalCost -= snacks.map { it.count * it.avgPrice }.sum()
        TripInfo.snackInfoClear()
        Log.d("총 금액", TripInfo.tripTotalCost.toString())
    }

    fun init(){

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()


        rv_snacks.adapter = snackAdapter
        rv_snacks.layoutManager = LinearLayoutManager(this)

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.tripTotalCost += snacks.map { it.count * it.avgPrice }.sum()
            TripInfo.snackInfo += snacks
            // Log.d("테스트", TripInfo.snackInfo.toString())

            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    fun setList() {
        // 서버 평균 가격 통신
        snacks.add(Snack("카페", 0, 10000, R.drawable.img_snacks_cafe))
        snacks.add(Snack("디저트", 0, 21000, R.drawable.img_snacks_bakery))
        snacks.add(Snack("펍 & 바", 0, 30000, R.drawable.img_snacks_pubnbar))
        snackAdapter.notifyDataSetChanged()
    }

    private fun String.goCategoryIntent() {
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@SnackPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@SnackPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@SnackPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@SnackPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@SnackPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@SnackPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
        }
        startActivity(categoryIntent)
    }

    private inner class SnackRvAdapter :
        RecyclerView.Adapter<SnackViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
            val view: View = layoutInflater.inflate(R.layout.rv_item_sanck, parent, false)
            return SnackViewHolder(view)
        }

        override fun getItemCount(): Int {
            return snacks.size
        }

        override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
            holder.bind(snacks[position])
        }
    }

    private inner class SnackViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgSnack: ImageView = v.findViewById(R.id.img_snack)
        val tvType: TextView = v.findViewById(R.id.tv_snackType)
        val tvSnackPrice: TextView = v.findViewById(R.id.tv_snackPrice)
        val tvSnackCost: TextView = v.findViewById(R.id.tv_snackCost)
        val btnPlus: Button = v.findViewById(R.id.btn_plus)
        val btnMinus: Button = v.findViewById(R.id.btn_minus)
        val tvCount: TextView = v.findViewById(R.id.tv_count)

        fun bind(data: Snack) {
            tvType.text = data.type
            tvSnackPrice.text = data.avgPrice.toDecimalFormat()
            tvSnackCost.text = 0.toDecimalFormat()
            imgSnack.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    data.image,
                    null
                )
            )

            btnPlus.setOnClickListener {
                data.count += 1
                tvCount.text = data.count.toString()
                tvSnackCost.text = (data.count * data.avgPrice).toDecimalFormat()

                tv_snackCount.text = snacks.map { it.count }.sum().toString()
                tv_totalPrice.text =
                    (TripInfo.tripTotalCost + snacks.map { it.count * it.avgPrice }.sum()).toDecimalFormat()
            }

            btnMinus.setOnClickListener {
                if (data.count != 0) {
                    data.count -= 1
                    tvCount.text = data.count.toString()
                    tvSnackCost.text = (data.count * data.avgPrice).toDecimalFormat()

                    tv_snackCount.text = snacks.map { it.count }.sum().toString()
                    tv_totalPrice.text =
                        (TripInfo.tripTotalCost + snacks.map { it.count * it.avgPrice }.sum()).toDecimalFormat()
                }
            }
        }
    }
}
