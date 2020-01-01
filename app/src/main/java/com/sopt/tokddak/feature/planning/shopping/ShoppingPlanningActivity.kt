package com.sopt.tokddak.feature.planning.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.sopt.tokddak.R
import com.sopt.tokddak.common.toDecimalFormat
import com.sopt.tokddak.feature.planning.TripInfo
import kotlinx.android.synthetic.main.activity_shopping_planning.*

class ShoppingPlanningActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_planning)

        init()
    }

    fun init() {

        tv_totalPrice.text = TripInfo.tripTotalCost.toDecimalFormat()

        btn_done.isEnabled = false
        edt_shoppingCost.addTextChangedListener(
            onTextChanged = { _, _, _, _ ->
                setButtonState()
            }
        )

        btn_done.setOnClickListener {
            val shoppingCost = edt_shoppingCost.toString().toInt()
            TripInfo.shoppingInfo = shoppingCost
            TripInfo.tripTotalCost += shoppingCost
        }

        img_toBack.setOnClickListener {
            finish()
        }
    }

    private fun setButtonState() {
        btn_done.isEnabled = edt_shoppingCost.text.toString() != ""
    }
}
