package com.sopt.tokddak.feature.inner


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R

class InnerAdapter (private val context : Context, var dayContents : ArrayList<String>) : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>(){

    var data = listOf<InnerItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_inner,parent,false)
        return InnerViewHolder(view)
    }

    override fun getItemCount(): Int = dayContents.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        return holder.bind(dayContents[position])
    }

    inner class InnerViewHolder (view : View) : RecyclerView.ViewHolder(view){//, BottomAdapter.BottomListener
    val ctn_inner : View = view.findViewById(R.id.ctn_inner)
        val txt_category : TextView = view.findViewById(R.id.txt_category)
        val img_category : ImageView = view.findViewById(R.id.img_category)


        fun bind(innerItem : String){
            txt_category.text = innerItem
        }
    }

    fun notifyDataChange(){

    }

}