package com.mcbath.booksearch.view

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mcbath.booksearch.application.BookSearchApplication
import com.mcbath.booksearch.databinding.ActivityWebviewBinding


class WebviewActivity : AppCompatActivity() {

    private var webView: WebView? = null
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webUrl:String = intent.getStringExtra("url").toString()
        Log.d("WebViewActivity", "webUrl=$webUrl")

        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.setBackgroundColor(0)

        binding.webView.webViewClient = MyWebViewClient()
        val webSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.allowContentAccess = true
        webSettings.domStorageEnabled = true
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.loadUrl(webUrl)
    }

    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            Toast.makeText( BookSearchApplication.instance, "Sorry, $description", Toast.LENGTH_SHORT).show()
            Log.d("WebViewActivity", "ERROR=$description")
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}