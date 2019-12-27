package com.sopt.tokddak.feature.planning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import com.sopt.tokddak.R
import java.util.ArrayList

class PlanningActivity : AppCompatActivity() {

    var selectedList: ArrayList<String>? = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planning)

        // type mismatch 해결
        val intent = getIntent()
        selectedList = intent.getStringArrayListExtra("selected category list")
    }


}
