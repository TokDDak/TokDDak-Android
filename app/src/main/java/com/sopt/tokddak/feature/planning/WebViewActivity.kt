package com.sopt.tokddak.feature.planning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sopt.tokddak.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    private var url: String? = null
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        webView = findViewById(R.id.webview)

        val intent = intent
        url = intent.getStringExtra("url")

        init()
        webView.loadUrl(url)
    }

    fun init() {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = (object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
