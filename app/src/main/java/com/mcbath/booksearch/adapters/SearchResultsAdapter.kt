package com.mcbath.booksearch.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mcbath.booksearch.R
import com.mcbath.booksearch.models.Volume
import com.mcbath.booksearch.utils.Utils
import com.mcbath.booksearch.view.DetailFragment
import com.mcbath.booksearch.view.MainActivity
import java.util.*


class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsHolder>() {
    private var volumes: List<Volume> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return SearchResultsHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchResultsHolder, position: Int) {
        val volume = volumes[position]

        val title = volume.volumeInfo?.title

        holder.titleTextView.text = title
        holder.publishedDateTextView.text = volume.volumeInfo?.publishedDate
        if (volume.volumeInfo?.imageLinks != null) {
            val imageUrl =
                volume.volumeInfo.imageLinks.smallThumbnail?.replace("http://", "https://")
                    .toString()
            Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.thumbnailImageView)
        }
        if (volume.volumeInfo?.authors != null) {
            val utils = Utils()
            val authors = utils.stringJoin(volume.volumeInfo.authors, ", ")
            holder.authorsTextView.text = authors
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            val fragment: Fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("volume", volume)
            fragment.setArguments(bundle)
            val fm: FragmentManager = (it.context as MainActivity).supportFragmentManager
            val ft: FragmentTransaction = fm.beginTransaction()
            ft.replace(android.R.id.content, fragment)
            ft.addToBackStack("detailFragment").commit()
        })
    }

    override fun getItemCount(): Int {
        return volumes.size
    }

    fun setResults(volumes: List<Volume>) {
        this.volumes = volumes
        notifyDataSetChanged()
    }

    class SearchResultsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val authorsTextView: TextView = itemView.findViewById(R.id.authors)
        val publishedDateTextView: TextView = itemView.findViewById(R.id.publishedDate)
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnail)
    }
}