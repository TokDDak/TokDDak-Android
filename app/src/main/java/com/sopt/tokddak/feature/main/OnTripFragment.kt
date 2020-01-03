package com.sopt.tokddak.feature.main


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sopt.tokddak.R
import com.sopt.tokddak.feature.expense.ExpenseActivity
import com.sopt.tokddak.feature.history.HistoryActivity
import kotlinx.android.synthetic.main.fragment_on_trip.*

/**
 * A simple [Fragment] subclass.
 */
class OnTripFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_on_trip, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    fun init(){
        btn_addExpense.setOnClickListener {
            val intent = Intent(activity!!, ExpenseActivity::class.java)
            startActivity(intent)
        }

        btn_moreHistory.setOnClickListener {
            val intent = Intent(activity!!, HistoryActivity::class.java)
            startActivity(intent)
        }

    }
}
