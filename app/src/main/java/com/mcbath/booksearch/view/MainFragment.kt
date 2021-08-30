package com.mcbath.booksearch.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcbath.booksearch.adapters.SearchResultsAdapter
import com.mcbath.booksearch.databinding.FragmentMainBinding
import com.mcbath.booksearch.viewmodels.MainViewModel

/*
 - Create the SearchResultsAdapter which will start with no data
 - Wire up the MainFragment with the MainViewModel and initialising it
   (which creates the repository from the ViewModel)
 - Observe the LiveData used for handling the Google Book API search results from the
   MainFragment to automatically trigger an update to the data inside the
   SearchResultsAdapter when the LiveData is updated as the search is completed
 - Set up the RecyclerView for showing the search results
 - Set up the TextInputEditTexts to capture the keyword search term and author for performing a search
 - Set up the search Button to trigger the search using a method exposed by the MainViewModel which
   uses the repository to trigger an API request using Retrofit2 then update the response into the LiveData

   NOTE: I did not have time to clear out the list when the user clears the edit text field */

class MainFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var adapter: SearchResultsAdapter? = null
    private var binding: FragmentMainBinding? = null
    private var keyword = ""
    private var beginAgainIndex = 1
    private var maxResults = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        adapter = SearchResultsAdapter()
        binding!!.searchResultsRv.layoutManager = LinearLayoutManager(context)
        binding!!.searchResultsRv.adapter = adapter
        binding!!.searchButton.setOnClickListener {
            searchVolumes(1, maxResults)
            it.hideKeyboard()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel!!.init()
        viewModel!!.getVolumesResponseLiveData()!!.observe(viewLifecycleOwner, { volumesResponse ->
            if (volumesResponse != null) {
                adapter!!.setResults(volumesResponse.items!!)
                if (adapter!!.itemCount > 1) {
                    binding!!.moreButton.setVisibility(View.VISIBLE)
                }
                binding!!.moreButton.setOnClickListener {
                    searchVolumes(beginAgainIndex, maxResults)
                    adapter!!.appendResults(volumesResponse.items!!)
                    it.hideKeyboard()
                }
            }
        })
    }

    /* Search Button triggers the search using a method exposed by MainViewModel which uses the
       repository to trigger an API request using Retrofit2 then updates the response
       into the LiveData */
    private fun searchVolumes(startIndex: Int, maxResults: Int) {
        keyword = binding!!.searchTermKeyword.editableText.toString()
        beginAgainIndex = startIndex + maxResults

        Log.d(TAG, "startIndex=$startIndex, beginAgainIndex=$beginAgainIndex")

        viewModel!!.searchVolumes(keyword, startIndex, maxResults)
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
        val TAG = MainFragment::class.qualifiedName
    }
}