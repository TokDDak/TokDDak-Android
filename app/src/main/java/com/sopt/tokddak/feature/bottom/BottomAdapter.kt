package com.sopt.tokddak.feature.bottom


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R



class BottomAdapter (private val context : Context) : RecyclerView.Adapter<BottomAdapter.BottomViewHolder>() {

    var data = listOf<BottomItem>()

    private lateinit var itemClickListener:View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_inbottom,parent,false)
        view.findViewById<Button>(R.id.btn_bottom_item).setOnClickListener(itemClickListener)
        return BottomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setItemClick(l : View.OnClickListener){
        itemClickListener = l
    }

    inner class BottomViewHolder (view : View) : RecyclerView.ViewHolder(view){

        val btn_bottom_item : Button = view.findViewById(R.id.btn_bottom_item)

        fun bind(bottomItem: BottomItem) {
            btn_bottom_item.text = bottomItem.name
        }
    }
}