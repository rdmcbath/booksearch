package com.mcbath.booksearch.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mcbath.booksearch.data.BookRepository
import com.mcbath.booksearch.models.VolumesResponse

/* The ViewModel will be used by the View to interact with the API via the repository to search
 for books. The View will also use the ViewModel to observe for any changes to the list of search
 results, which gets updated when the search triggered by the API is complete, which allows
 the View to retrieve the results immediately to display in the user interface.*/

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var bookRepository: BookRepository? = null
    private val volumesResponse: VolumesResponse = VolumesResponse()

    private var volumesResponseLiveData: LiveData<VolumesResponse?>? = null

    fun init() {
        bookRepository = BookRepository()
        volumesResponseLiveData = bookRepository!!.getVolumesResponseLiveData()
    }

    fun searchVolumes(keyword: String?, startIndex: Int, maxResults: Int) {
        bookRepository!!.searchVolumes(keyword, startIndex, maxResults)
    }

    fun getVolumesResponseLiveData(): LiveData<VolumesResponse?>? {
        return volumesResponseLiveData
    }
}