package com.sopt.tokddak.feature.day


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sopt.tokddak.R
import com.sopt.tokddak.feature.inner.InnerAdapter
import com.sopt.tokddak.feature.inner.InnerData

class DayAdapter(private val context : Context, var dayContents : ArrayList<ArrayList<String>>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>(){

    private val innerData = InnerData()
    var data = listOf<DayItem>()
    lateinit var innerAdapter : InnerAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_day,parent,false)
        return DayViewHolder(view)
    }

    override fun getItemCount(): Int = dayContents.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
//        holder.bind(data[position])
        holder.bind(dayContents[position])
    }

    inner class DayViewHolder(view: View) : RecyclerView.ViewHolder (view){
        val ctnRvDay : View = view.findViewById(R.id.ctnRvDay)
        val txt_dayNo : TextView = view.findViewById(R.id.txt_dayNo)
        val rv_inner : RecyclerView = view.findViewById(R.id.rv_inner)

//        fun bind(data : DayItem){
//            txt_dayNo.text = data.day
//
//            innerAdapter = InnerAdapter(context)
//            rv_inner.adapter = innerAdapter
//            rv_inner.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
//            rv_inner.setHasFixedSize(true)
//
//            //val holder = rv_inner.findViewHolderForAdapterPosition(position) as InnerAdapter.InnerViewHolder
//
//            innerAdapter.data = innerData.getInner()
//            innerAdapter.notifyDataSetChanged()
//        }

        fun bind(dayContents : ArrayList<String>){
            innerAdapter = InnerAdapter(context, dayContents)
            rv_inner.adapter = innerAdapter
            rv_inner.layoutManager = LinearLayoutManager(context)
            rv_inner.setHasFixedSize(true)

            //val holder = rv_inner.findViewHolderForAdapterPosition(position) as InnerAdapter.InnerViewHolder
//
//            innerAdapter.data = innerData.getInner()
//            innerAdapter.notifyDataSetChanged()
        }
    }

    fun updateDay(){
        innerAdapter.notifyDataChange()
    }

//    fun updateDay(idx : Int, dayContents : ArrayList<String>){
//        Log.v("YGYG", innerAdapter.dayContents.toString())
//        innerAdapter.notifyDataSetChanged()
//    }
}