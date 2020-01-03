package com.sopt.tokddak.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // toolbar 설정
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pagerAdapter = MainPagerAdapter(supportFragmentManager, this@MainActivity)
        val viewPager = findViewById<ViewPager>(R.id.ctn_fragment)
        viewPager.adapter = pagerAdapter

        val tab = findViewById<TabLayout>(R.id.tab_main)
        tab.setupWithViewPager(viewPager)

    }
}
