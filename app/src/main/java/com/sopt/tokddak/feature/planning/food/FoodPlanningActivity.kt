package com.sopt.tokddak.feature.planning.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.Food
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_food_planning.*

class FoodPlanningActivity : AppCompatActivity() {

    private val foods = mutableListOf<Food>()
    private val foodAdapter: FoodRvAdapter = FoodRvAdapter()

    var selectedCategoryList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")

        init()
        setList()
    }

    fun init() {

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        rv_foods.adapter = foodAdapter
        rv_foods.layoutManager = LinearLayoutManager(this)

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            TripInfo.tripTotalCost += foods.map { it.count * it.avgPrice }.sum()
            TripInfo.foodInfo += foods

            if(selectedCategoryList.isNullOrEmpty()){
                // TODO: 예산 산정 완료 뷰, activity stack clear
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    fun setList() {
        // 서버 평균 가격 통신
        foods.add(Food("고급음식점", 0, 10000, R.drawable.img_food_top))
        foods.add(Food("일반음식점", 0, 21000, R.drawable.img_food_general))
        foods.add(Food("간편식", 0, 30000, R.drawable.img_food_simple))
        foodAdapter.notifyDataSetChanged()
    }

    private fun String.goCategoryIntent() {
        selectedCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@FoodPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@FoodPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@FoodPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@FoodPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@FoodPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@FoodPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", selectedCategoryList)
        }
        startActivity(categoryIntent)
    }

    private inner class FoodRvAdapter :
        RecyclerView.Adapter<FoodViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
            val view: View = layoutInflater.inflate(R.layout.rv_item_food, parent, false)
            return FoodViewHolder(view)
        }

        override fun getItemCount(): Int {
            return foods.size
        }

        override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
            holder.bind(foods[position])
        }
    }

    private inner class FoodViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgFood: ImageView = v.findViewById(R.id.img_food)
        val tvType: TextView = v.findViewById(R.id.tv_foodType)
        val tvFoodPrice: TextView = v.findViewById(R.id.tv_foodPrice)
        val tvFoodCost: TextView = v.findViewById(R.id.tv_foodCost)
        val btnPlus: Button = v.findViewById(R.id.btn_plus)
        val btnMinus: Button = v.findViewById(R.id.btn_minus)
        val tvCount: TextView = v.findViewById(R.id.tv_count)

        fun bind(data: Food) {
            tvType.text = data.type
            tvFoodPrice.text = data.avgPrice.toDecimalFormat()
            tvFoodCost.text = 0.toDecimalFormat()
            imgFood.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    data.image,
                    null
                )
            )

            btnPlus.setOnClickListener {
                data.count += 1
                tvCount.text = data.count.toString()
                tvFoodCost.text = (data.count * data.avgPrice).toDecimalFormat()

                tv_foodCount.text = foods.map { it.count }.sum().toString()
                tv_totalPrice.text =
                    (TripInfo.tripTotalCost + foods.map { it.count * it.avgPrice }.sum()).toString()
            }

            btnMinus.setOnClickListener {
                if (data.count != 0) {
                    data.count -= 1
                    tvCount.text = data.count.toString()
                    tvFoodCost.text = (data.count * data.avgPrice).toDecimalFormat()

                    tv_foodCount.text = foods.map { it.count }.sum().toString()
                    tv_totalPrice.text =
                        (TripInfo.tripTotalCost + foods.map { it.count * it.avgPrice }.sum()).toString()
                }
            }
        }
    }
}
