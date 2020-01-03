package com.sopt.tokddak.feature.planning.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.api.FoodData
import com.sopt.tokddak.api.GetFoodData
import com.sopt.tokddak.api.PlanningServiceImpl
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.Food
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.activity.ActivitesPlanningActivity
import com.sopt.tokddak.feature.planning.lodgement.LodgementPlanningActivity
import com.sopt.tokddak.feature.planning.result.PlanningResultActivity
import com.sopt.tokddak.feature.planning.shopping.ShoppingPlanningActivity
import com.sopt.tokddak.feature.planning.snack.SnackPlanningActivity
import com.sopt.tokddak.feature.planning.transportation.TransportationPlanningActivity
import kotlinx.android.synthetic.main.activity_food_planning.*
import kotlinx.android.synthetic.main.activity_food_planning.btn_done
import kotlinx.android.synthetic.main.activity_food_planning.img_toBack
import kotlinx.android.synthetic.main.activity_planning_result.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodPlanningActivity : AppCompatActivity() {

    private val foods = mutableListOf<Food>()
    private val foodsData = arrayListOf<FoodData>()
    private val foodAdapter: FoodRvAdapter = FoodRvAdapter()

    var selectedCategoryList: ArrayList<String> = ArrayList()
    var totalBudgetInFood = 0
    var intentBudget = 0

    lateinit var indexString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_planning)

        val intent = intent
        selectedCategoryList = intent.getStringArrayListExtra("selected category list")!!
        totalBudgetInFood = intent.getIntExtra("budget", 0)

        init()
        getFoodData()

    }

    override fun onResume() {
        super.onResume()

        // object 저장 초기화
        // TripInfo.tripTotalCost -= foods.map { it.count * it.avgPrice }.sum()
        TripInfo.foodInfoClear()
        intentBudget = totalBudgetInFood


    }

    fun init() {

        tv_totalPrice.text = totalBudgetInFood.toDecimalFormat()

        rv_foods.adapter = foodAdapter
        rv_foods.layoutManager = LinearLayoutManager(this)

        img_toBack.setOnClickListener {
            finish()
        }

        btn_done.setOnClickListener {
            intentBudget += foods.map { it.count * it.avgPrice }.sum()
            TripInfo.foodInfo += foods

            if(selectedCategoryList.isNullOrEmpty()){
                val intent = Intent(this, PlanningResultActivity::class.java)
                startActivity(intent)
            } else
                selectedCategoryList[0].goCategoryIntent()
        }
    }

    fun setList() {
        // 서버 평균 가격 통신
    }

    private fun String.goCategoryIntent() {
        var passSelectCategoryList = arrayListOf<String>()
        passSelectCategoryList.addAll(selectedCategoryList)
        passSelectCategoryList.removeAt(0)
        val categoryIntent = when (this) {
            "숙박" -> Intent(this@FoodPlanningActivity, LodgementPlanningActivity::class.java)
            "식사" -> Intent(this@FoodPlanningActivity, FoodPlanningActivity::class.java)
            "주류 및 간식" -> Intent(this@FoodPlanningActivity, SnackPlanningActivity::class.java)
            "교통" -> Intent(this@FoodPlanningActivity, TransportationPlanningActivity::class.java)
            "쇼핑" -> Intent(this@FoodPlanningActivity, ShoppingPlanningActivity::class.java)
            "액티비티" -> Intent(this@FoodPlanningActivity, ActivitesPlanningActivity::class.java)
            else -> return
        }.apply {
            putExtra("selected category list", passSelectCategoryList)
            putExtra("budget", intentBudget)
        }
        startActivity(categoryIntent)
    }

    fun getFoodData(){
        val call: Call<GetFoodData> =
            PlanningServiceImpl.planningService.getFood(
                "application/json",
                1
            )

        call.enqueue(
            object : Callback<GetFoodData> {

                override fun onFailure(call: Call<GetFoodData>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<GetFoodData>,
                    response: Response<GetFoodData>
                ) {
                    if (response.isSuccessful){
                        if(response.body()!!.status == 200){
                            Log.d("테스트", response.body()!!.toString())
                            val temp = response.body()!!.foodResult.result
                            if(temp.isNotEmpty()){
                                foodsData.addAll(temp)
                                foods.add(Food("고급음식점", 0, foodsData[2].cost, R.drawable.img_food_top))
                                foods.add(Food("일반음식점", 0, foodsData[1].cost, R.drawable.img_food_general))
                                foods.add(Food("간편식", 0, foodsData[0].cost, R.drawable.img_food_simple))
                                foodAdapter.notifyDataSetChanged()
                                Log.d("테스트 temp", temp.toString())
                                Log.d("테스트 foodsData", foodsData.toString())
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
                    (intentBudget + foods.map { it.count * it.avgPrice }.sum()).toDecimalFormat()
            }

            btnMinus.setOnClickListener {
                if (data.count != 0) {
                    data.count -= 1
                    tvCount.text = data.count.toString()
                    tvFoodCost.text = (data.count * data.avgPrice).toDecimalFormat()

                    tv_foodCount.text = foods.map { it.count }.sum().toString()
                    tv_totalPrice.text =
                        (intentBudget + foods.map { it.count * it.avgPrice }.sum()).toDecimalFormat()
                }
            }
        }
    }
}
