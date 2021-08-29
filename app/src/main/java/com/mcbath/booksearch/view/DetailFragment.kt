package com.mcbath.booksearch.view

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mcbath.booksearch.R
import com.mcbath.booksearch.databinding.FragmentDetailBinding
import com.mcbath.booksearch.models.Volume
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
    private lateinit var volume: Volume
    private var webLinkUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            (bundle.getParcelable<Parcelable>("volume") as Volume).also { volume = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel!!.init()
        viewModel!!.getWebReaderLink()
        viewModel!!.getVolumesResponseLiveData()!!.observe(viewLifecycleOwner, { volumesResponse ->
            viewModel!!.getWebReaderLink()
            if (volumesResponse != null) {
                webLinkUrl = volumesResponse.webReaderLink.toString()

            }
        })

        updateUI()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        imageUrl = volume.volumeInfo?.imageLinks?.smallThumbnail?.replace("http://", "https://")
            .toString()
        title = volume.volumeInfo?.title.toString()
        authors = volume.volumeInfo?.authors.toString()
        date = volume.volumeInfo?.publishedDate.toString()
        description = volume.volumeInfo?.subtitle.toString()

        binding?.let {
            Glide.with(requireActivity())
                .load(imageUrl)
                .placeholder(R.drawable.thumbnail_placeholder)
                .error(R.drawable.thumbnail_placeholder)
                .into(it.bookImage)
        }
        val utils = Utils()
        val authors = volume.volumeInfo?.authors?.let { utils.stringJoin(it, ", ") }
        binding?.title?.text = title
        binding?.authors?.text = authors
        binding?.publishDate?.text = date
        binding?.description?.text = description
        binding?.buttonWebReader?.setOnClickListener(View.OnClickListener {
            binding?.bookDetailLayout?.visibility = View.GONE
            binding?.webViewContainer?.visibility = View.VISIBLE
            binding?.webView?.setBackgroundColor(0)
            binding?.webView?.webChromeClient
            binding?.webView?.loadUrl(webLinkUrl)
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}