package com.sopt.tokddak.feature.bottom


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.day.DayActivity

class BottomSheetDialog : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var rv_category : RecyclerView
    private lateinit var bottomAdapter: BottomAdapter

    private val StayData = com.sopt.tokddak.feature.bottom.data_collection.StayData()
    private val FoodData = com.sopt.tokddak.feature.bottom.data_collection.FoodData()
    private val SnacksData = com.sopt.tokddak.feature.bottom.data_collection.SnacksData()
    private val ActivityData = com.sopt.tokddak.feature.bottom.data_collection.ActivityData()

    private lateinit var ctn_stay : ConstraintLayout
    private lateinit var ctn_food : ConstraintLayout
    private lateinit var ctn_snacks : ConstraintLayout
    private lateinit var ctn_activity : ConstraintLayout

    private var type = 0
    var contents = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.bottom_sheet, container, false)

        init(v)
        //v.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnCheckingSync(v)
        return v
    }

    override fun onStart() {
        super.onStart()

    }

    private fun init(view : View){
        rv_category = view.findViewById(R.id.rv_category)
        bottomAdapter = BottomAdapter(context!!)

        rv_category.adapter = bottomAdapter
        rv_category.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)

        bottomAdapter.data = StayData.getCategory()

        bottomAdapter.notifyDataSetChanged()
        bottomAdapter.setItemClick(this)
    }

    private fun btnCheckingSync(view : View){
        //TODO 현재 안드내에 있는 더미데이터 말고 서버 통신 후 결과 가져오기, 4개의 클릭리스너 모두.
        ctn_stay = view.findViewById(R.id.ctn_stay)
        ctn_stay.setOnClickListener {
            bottomAdapter = BottomAdapter(context!!)

            rv_category.adapter = bottomAdapter
            rv_category.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)

            bottomAdapter.data = StayData.getCategory()

            bottomAdapter.notifyDataSetChanged()
            type = 0
            bottomAdapter.setItemClick(this)
        }

        ctn_food = view.findViewById(R.id.ctn_food)
        ctn_food.setOnClickListener {
            bottomAdapter = BottomAdapter(context!!)

            rv_category.adapter = bottomAdapter
            rv_category.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)

            bottomAdapter.data = FoodData.getCategory()

            bottomAdapter.notifyDataSetChanged()
            type = 1

            bottomAdapter.setItemClick(this)
        }

        ctn_snacks = view.findViewById(R.id.ctn_snacks)
        ctn_snacks.setOnClickListener {
            bottomAdapter = BottomAdapter(context!!)

            rv_category.adapter = bottomAdapter
            rv_category.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)

            bottomAdapter.data = SnacksData.getCategory()

            bottomAdapter.notifyDataSetChanged()

            type = 2
            bottomAdapter.setItemClick(this)
        }

        ctn_activity = view.findViewById(R.id.ctn_activity)
        ctn_activity.setOnClickListener {
            bottomAdapter = BottomAdapter(context!!)

            rv_category.adapter = bottomAdapter
            rv_category.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)

            bottomAdapter.data = ActivityData.getCategory()

            bottomAdapter.notifyDataSetChanged()

            type = 3
            bottomAdapter.setItemClick(this)
        }
    }

    override fun onClick(v: View?) {
        Log.v("Bottm", "Come")

        when(v!!){
            v.findViewById<Button>(R.id.btn_bottom_item) ->{
                val idx = rv_category.getChildAdapterPosition(v.parent as View)
                when(type){
                    0->{
                        contents = StayData.getCategory()[idx].name
                    }
                    1->{
                        contents = FoodData.getCategory()[idx].name

                    }
                    2->{
                        contents = SnacksData.getCategory()[idx].name

                    }
                    3->{
                        contents = ActivityData.getCategory()[idx].name

                    }
                }
                Log.v("Bottm", contents)
                (activity as DayActivity).getContents(contents)
                //Toast.makeText(context, contents, Toast.LENGTH_SHORT).show()
            }
        }

    }
}