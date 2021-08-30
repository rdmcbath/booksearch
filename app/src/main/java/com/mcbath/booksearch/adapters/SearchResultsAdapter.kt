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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mcbath.booksearch.R
import com.mcbath.booksearch.models.Item
import com.mcbath.booksearch.utils.Utils
import com.mcbath.booksearch.view.DetailFragment
import com.mcbath.booksearch.view.MainActivity
import java.util.*


/*RecyclerViewAdapter binds the views for each position in the list. Unlike a fragment,
* ViewModel is not preserving the recyclerview data, I think because th holder gets recycled and
* recreated as a new holder, i.e., viewModel maybe by incorrect, referencing the old item.
* I'm experimenting with ways to make it work. Possibly by making the ViewHolder become a
* ViewModelStoreOwner*/

class SearchResultsAdapter() : RecyclerView.Adapter<SearchResultsAdapter.SearchResultsHolder>() {
    private var items: List<Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return SearchResultsHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchResultsHolder, position: Int) {
        val item = items[position]

        val title = item.volumeInfo?.title

        holder.titleTextView.text = title
        holder.publishedDateTextView.text = item.volumeInfo?.publishedDate
        if (item.volumeInfo?.imageLinks != null) {
            val imageUrl =
                item.volumeInfo.imageLinks.smallThumbnail?.replace("http://", "https://")
                    .toString()
            Glide.with(holder.itemView)
                .load(imageUrl)
                .into(holder.thumbnailImageView)
        }
        if (item.volumeInfo?.authors != null) {
            val utils = Utils()
            val authors = utils.stringJoin(item.volumeInfo.authors, ", ")
            holder.authorsTextView.text = authors
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            val fragment: Fragment = DetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("volume", item)
            fragment.setArguments(bundle)
            val fm: FragmentManager = (it.context as MainActivity).supportFragmentManager
            val ft: FragmentTransaction = fm.beginTransaction()
            ft.replace(android.R.id.content, fragment)
            ft.addToBackStack("detailFragment").commit()
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setResults(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun appendResults(response: List<Item>) {
        items.size + response.size
        notifyDataSetChanged()
    }

    class SearchResultsHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ViewModelStoreOwner {

        private var viewModelStore: ViewModelStore = ViewModelStore()
        override fun getViewModelStore(): ViewModelStore = viewModelStore

        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val authorsTextView: TextView = itemView.findViewById(R.id.authors)
        val publishedDateTextView: TextView = itemView.findViewById(R.id.publishedDate)
        val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnail)
    }
}