package com.sopt.tokddak.feature.history


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R

class HistoryAdapter(private val context: Context) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    var data = listOf<HistoryServerInItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_history,parent,false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class HistoryViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val txt_category : TextView = view.findViewById(R.id.txt_category)
        val txt_detail : TextView = view.findViewById(R.id.txt_detail)
        val txt_price : TextView = view.findViewById(R.id.txt_price)

        fun bind(historyItem: HistoryServerInItem){
            txt_category.text = historyItem.category.toString()
            txt_detail.text = historyItem.detail
            txt_price.text = historyItem.price.toString()
        }
    }
}