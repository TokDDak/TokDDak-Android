package com.sopt.tokddak.feature.planning.result


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide

import com.sopt.tokddak.R
import com.sopt.tokddak.api.*
import com.sopt.tokddak.feature.day.DayActivity
import com.sopt.tokddak.feature.planning.TripInfo
import com.sopt.tokddak.feature.planning.WebViewActivity
import kotlinx.android.synthetic.main.fragment_result_pop.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class ResultPopFragment : DialogFragment() {

    lateinit var fmManager: FragmentManager

    val lodgeData = mutableListOf<RequestLodgeData>()
    val activityData = mutableListOf<RequestActivityData>()
    val snackData = mutableListOf<RequestSnackData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_result_pop, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // radius 적용
        dialog?.setCanceledOnTouchOutside(false) // background touch 방지

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fmManager = activity!!.supportFragmentManager
    }

    override fun onStart() {
        super.onStart()
        init()
    }

    override fun onResume() {
        super.onResume()

        // dialog size setting
        val dialogWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
        val dialogHeight = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(dialogWidth, dialogHeight)
    }

    fun init(){
        btn_close.setOnClickListener {
            fmManager.beginTransaction().remove(this@ResultPopFragment).commit()
            fmManager.popBackStack()
        }

        btn_done.setOnClickListener{
            val intent = Intent(activity, DayActivity::class.java)
            startActivity(intent)
            activity!!.finish()

            fmManager.beginTransaction().remove(this@ResultPopFragment).commit()
            fmManager.popBackStack()

            makeServerData()
            postLodgeData()
            postActData()
            postSnackData()
        }
    }


    private fun makeServerData(){
        // 숙박 정보 서버 데이터화
        TripInfo.lodgementInfo.forEach {
            lodgeData.add(RequestLodgeData(it.type, it.avgPrice, it.count))
        }

        TripInfo.activityInfo.forEach {
            activityData.add(RequestActivityData(it.name, it.cost))
        }

        TripInfo.snackInfo.filter { it.count != 0 }.forEach {
            snackData.add(RequestSnackData(it.type, it.avgPrice, it.count))
        }
    }

    private fun postLodgeData(){
        val call: Call<PostRequestLodgeState> =
            PlanningServiceImpl.planningService.requestLodge(
                "application/json",
                1,
                PostRequestLodgeData(lodgeData)
            )

        call.enqueue(
            object : Callback<PostRequestLodgeState> {

                override fun onFailure(call: Call<PostRequestLodgeState>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<PostRequestLodgeState>,
                    response: Response<PostRequestLodgeState>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {

                        }
                    }
                }
            }
        )
    }

    private fun postActData(){
        val call: Call<PostREquestActivityState> =
            PlanningServiceImpl.planningService.requestActivity(
                "application/json",
                1,
                PostRequestActivityData(activityData)
            )

        call.enqueue(
            object : Callback<PostREquestActivityState> {

                override fun onFailure(call: Call<PostREquestActivityState>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<PostREquestActivityState>,
                    response: Response<PostREquestActivityState>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {

                        }
                    }
                }
            }
        )
    }

    private fun postSnackData(){
        val call: Call<PostRequestSnackState> =
            PlanningServiceImpl.planningService.requestSnack(
                "application/json",
                1,
                PostRequestSnackData(snackData)
            )

        call.enqueue(
            object : Callback<PostRequestSnackState> {

                override fun onFailure(call: Call<PostRequestSnackState>, t: Throwable) {
                    Log.e(this::class.java.name, "network error : $t")
                }

                override fun onResponse(
                    call: Call<PostRequestSnackState>,
                    response: Response<PostRequestSnackState>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == 200) {

                        }
                    }
                }
            }
        )
    }


}
