package com.mcbath.booksearch.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mcbath.booksearch.R
import com.mcbath.booksearch.databinding.FragmentDetailBinding
import com.mcbath.booksearch.models.Item
import com.mcbath.booksearch.utils.Utils
import com.mcbath.booksearch.viewmodels.MainViewModel


/* get the data model "volume" bundle and use it to populate the views. */

class DetailFragment : Fragment() {
    private var binding: FragmentDetailBinding? = null
    private var viewModel: MainViewModel? = null
    private lateinit var imageUrl: String
    private lateinit var title: String
    private lateinit var authors: String
    private lateinit var date: String
    private lateinit var description: String
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            (bundle.getParcelable<Parcelable>("volume") as Item).also { item = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        updateUI()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        imageUrl = item.volumeInfo?.imageLinks?.smallThumbnail?.replace("http://", "https://")
            .toString()
        title = item.volumeInfo?.title.toString()
        authors = item.volumeInfo?.authors.toString()
        date = item.volumeInfo?.publishedDate.toString()
        description = item.volumeInfo?.description.toString()

        binding?.let {
            Glide.with(requireActivity())
                .load(imageUrl)
                .placeholder(R.drawable.thumbnail_placeholder)
                .error(R.drawable.thumbnail_placeholder)
                .into(it.bookImage)
        }
        val utils = Utils()
        val authors = item.volumeInfo?.authors?.let { utils.stringJoin(it, ", ") }
        binding?.title?.text = title
        binding?.authors?.text = authors
        binding?.publishDate?.text = date
        binding?.description?.text = description

        binding?.buttonWebReader?.setOnClickListener(View.OnClickListener {
            val webLinkUrl = item.accessInfo?.webReaderLink?.replace("http://", "https://")

            val intent = Intent(requireActivity(), WebviewActivity::class.java)
            intent.putExtra("url", webLinkUrl)
            startActivity(intent);

//            binding?.webView?.setBackgroundColor(0)
//            binding?.webView?.settings?.setJavaScriptEnabled(true)
//            binding?.webView?.webViewClient  = object : WebViewClient() {
//                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                        if (webLinkUrl != null) {
//                            view?.loadUrl(webLinkUrl)
//                        }
//                        return true
//                    }
//                }
//            if (webLinkUrl != null) {
//                binding?.webView?.loadUrl(webLinkUrl)
//            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}