package com.sopt.tokddak.feature.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sopt.tokddak.feature.main.before_trip.BeforeTripFragment

class MainPagerAdapter(fm: FragmentManager, var ctx: Context) : FragmentPagerAdapter(fm) {

    val MAX_PAGE = 2

    override fun getCount(): Int {
        return MAX_PAGE
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> BeforeTripFragment(ctx)
            1 -> OnTripFragment()
            else -> error("pager adapter error")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when (position) {
            0 -> "여행 전"
            1 -> "여행 중"
            else -> error("pager adapter error")
        }

        return title
    }
}