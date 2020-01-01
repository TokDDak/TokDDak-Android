package com.sopt.tokddak.feature.main.before_trip


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.sopt.tokddak.R
import com.sopt.tokddak.data.Destination
import com.sopt.tokddak.feature.planning.select_category.SelectCategoryActivity
import kotlinx.android.synthetic.main.fragment_before_trip.*

/**
 * A simple [Fragment] subclass.
 */
class BeforeTripFragment(var ctx: Context) : Fragment() {

    private lateinit var popularAdapter: PopularRvAdapter

    // 생성자 구현
    /*fun newInstance(param: Parcelable): BeforeTripFragment {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_DATA, param)
        val fragment = BeforeTripFragment(ctx)
        fragment.arguments = bundle
        return fragment
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_before_trip, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        img_airplane.setOnClickListener {
            var intent: Intent = Intent(ctx, SelectCategoryActivity::class.java)
            startActivity(intent)
        }
        initPopularDestination()
        makeDummy()
    }

    private fun initPopularDestination(){
        popularAdapter = PopularRvAdapter(ctx)
        rv_popularPlace.adapter = popularAdapter
        rv_popularPlace.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun makeDummy(){
        popularAdapter.dataList.add(Destination("파리", R.drawable.btn_popular_paris))
        popularAdapter.dataList.add(Destination("세부", R.drawable.btn_popular_sebu))
        popularAdapter.dataList.add(Destination("뉴욕", R.drawable.btn_popular_newyork))
        popularAdapter.dataList.add(Destination("도쿄", R.drawable.btn_popular_tokyo))
        popularAdapter.dataList.add(Destination("다낭", R.drawable.btn_popular_danang))
        popularAdapter.dataList.add(Destination("타이페이", R.drawable.btn_popular_taipei))
        popularAdapter.notifyDataSetChanged()
    }
}
