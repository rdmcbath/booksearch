package com.mcbath.booksearch.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mcbath.booksearch.R
import com.mcbath.booksearch.databinding.FragmentDetailBinding
import com.mcbath.booksearch.models.Volume

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private lateinit var imageUrl: String
    private lateinit var title: String
    private lateinit var authors: String
    private lateinit var date: String
    private lateinit var description: String
    private lateinit var volume: Volume

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

        updateUI()

        return view
    }

    private fun updateUI() {
        imageUrl = volume.volumeInfo?.imageLinks?.smallThumbnail.toString()
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

        binding?.title?.text = title
        binding?.authors?.text = authors
        binding?.publishDate?.text = date
        binding?.description?.text = description
        binding?.buttonPreview?.setOnClickListener(View.OnClickListener {

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(volume: Volume): DetailFragment {
            val fragment = DetailFragment()

            val bundle = Bundle().apply {
                putParcelable("volume", volume)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}