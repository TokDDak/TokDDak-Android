package com.sopt.tokddak.feature.planning.transportation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import kotlinx.android.synthetic.main.activity_transportation_planning.*

class TransportationPlanningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transportation_planning)

        init()
    }

    fun init() {

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        btn_cancel.isGone = true
        btn_done.isEnabled = false
        edt_transCost.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                setButtonState()
                setViewState()
            }
        )

        btn_done.setOnClickListener {
            val transCost = edt_transCost.toString().toInt()
            TripInfo.shoppingInfo = transCost
            TripInfo.tripTotalCost += transCost
        }

        img_toBack.setOnClickListener {
            finish()
        }

        btn_cancel.setOnClickListener {
            edt_transCost.text.clear()
        }
    }

    private fun setButtonState() {
        btn_done.isEnabled = edt_transCost.text.toString() != ""
    }

    private fun setViewState(){
        tv_unit.isGone = edt_transCost.text.toString() != ""
        btn_cancel.isGone = edt_transCost.text.toString() == ""
    }
}
