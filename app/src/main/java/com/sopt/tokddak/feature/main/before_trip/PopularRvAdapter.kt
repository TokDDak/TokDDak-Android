package com.sopt.tokddak.feature.main.before_trip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.data.Destination

class PopularRvAdapter(var ctx: Context):RecyclerView.Adapter<PopularViewHolder>(){

    var dataList = arrayListOf<Destination>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_main_popular, parent, false)

        return PopularViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(dataList.get(position))


    }
}

class PopularViewHolder(v: View) : RecyclerView.ViewHolder(v){
    val imgPlace: ImageView = v.findViewById(R.id.img_place)

    fun bind(data: Destination){
        imgPlace.setImageDrawable(ResourcesCompat.getDrawable(itemView.resources, data.background, null))

        imgPlace.setOnClickListener {
            // 여행명 설정 뷰
        }
    }
}