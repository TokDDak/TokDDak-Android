package com.sopt.tokddak.feature.planning.select_category

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R

class CategoryRvAdapter(var ctx: Context, callbackListener: ClickCallbackListener) : RecyclerView.Adapter<CategoryViewHolder>() {

    var categoryList = mutableListOf("숙박", "식사", "주류 및 간식", "교통", "쇼핑", "액티비티")
    var flagList = mutableListOf(true, true, true, true, true, true)

    val callbackListener: ClickCallbackListener by lazy { callbackListener }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_plan_category_active, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList.get(position))

        // 선택 시 boolean 값 리스트에 계속 저장 --> 더 좋은 방법 없나..
        holder.ctnCategory.setOnClickListener {
            var flag = true

            holder.ctnCategory.setOnClickListener {
                if (flag == true) {
                    // 뷰 변경
                    holder.tvCategory.setTextColor(Color.parseColor("#d5d5d5"))
                    holder.ctnCategory.setBackgroundResource(R.drawable.bg_category_inactive)
                    holder.imgCategory.setImageResource(R.drawable.btn_plus_category)

                    flag = false
                } else {
                    // 뷰 변경
                    holder.tvCategory.setTextColor(Color.parseColor("#151617"))
                    holder.ctnCategory.setBackgroundResource(R.drawable.bg_category)
                    holder.imgCategory.setImageResource(R.drawable.btn_minus)

                    flag = true
                }
                flagList[position] = flag

                // flagList의 true 갯수로 선택 버튼 text 설정
                var count = 0
                for(flag in flagList){
                    if(flag == true)
                        count++
                }
                callbackListener.callBack(count)
            }
        }
    }
}

class CategoryViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val tvCategory: TextView = v.findViewById(R.id.tv_category)
    val ctnCategory: ConstraintLayout = v.findViewById(R.id.ctn_category)
    val imgCategory: ImageView = v.findViewById(R.id.img_button)

    fun bind(category: String) {
        tvCategory.text = category
    }

    /*fun checkSelected() {
        var flag: Boolean = true

        ctnCategory.setOnClickListener {
            if (flag == true) {
                // 뷰 변경
                tvCategory.setTextColor(Color.parseColor("#d5d5d5"))
                ctnCategory.setBackgroundResource(R.drawable.bg_category_inactive)
                imgCategory.setImageResource(R.drawable.btn_plus_category)

                flag = false
            } else {
                // 뷰 변경
                tvCategory.setTextColor(Color.parseColor("#138bff"))
                ctnCategory.setBackgroundResource(R.drawable.bg_category)
                imgCategory.setImageResource(R.drawable.btn_minus)

                flag = true
            }


        }
    }*/
}