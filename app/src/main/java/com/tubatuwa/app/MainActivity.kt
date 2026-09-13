package com.tubatuwa.app

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    // The blog this app wraps. Change this one line to reuse the app for a different site.
    private val siteUrl = "https://tubatuwa.blogspot.com/"
    private val siteHost = "tubatuwa.blogspot.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        swipeRefresh = findViewById(R.id.swipe_refresh)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                val url = request.url
                // Keep the blog itself inside the app; send anything else
                // (ads, external links, other domains) out to the real browser.
                return if (url.host?.contains(siteHost) == true ||
                    url.host?.contains("blogger.com") == true ||
                    url.host?.contains("blogspot.com") == true
                ) {
                    false
                } else {
                    startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, url))
                    true
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                swipeRefresh.isRefreshing = false
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: android.webkit.WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                if (request.isForMainFrame) {
                    Toast.makeText(
                        this@MainActivity,
                        "সংযোগ পাওয়া যাচ্ছে না। ইন্টারনেট চেক করুন।",
                        Toast.LENGTH_LONG
                    ).show()
                    swipeRefresh.isRefreshing = false
                }
            }
        }

        swipeRefresh.setOnRefreshListener { webView.reload() }

        webView.loadUrl(siteUrl)
    }

    // Let the Android back button navigate WebView history instead of closing the app.
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
