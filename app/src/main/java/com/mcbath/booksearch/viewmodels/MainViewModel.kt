package com.mcbath.booksearch.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mcbath.booksearch.data.BookRepository
import com.mcbath.booksearch.models.Item
import com.mcbath.booksearch.models.VolumesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/* The ViewModel will be used by the View to interact with the API via the repository to search
 for books. The View will also use the ViewModel to observe for any changes to the list of search
 results, which gets updated when the search triggered by the API is complete, which allows
 the View to retrieve the results immediately to display in the user interface.*/

/*ViewModel objects can contain LifecycleObservers, such as LiveData objects. However ViewModel
objects must never observe changes to lifecycle-aware observables, such as LiveData objects. If
the ViewModel needs the Application context, for example to find a system service, it can extend
the AndroidViewModel class and have a constructor that receives the Application in the constructor,
since Application class extends Context.*/

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var bookRepository: BookRepository? = null
    private var itemList: List<Item> = ArrayList()
    private var volumesResponseLiveData: LiveData<VolumesResponse?>? = null

    //for checking internet connection, we can inform the UI about the state of network connection
    private val _connection = MutableLiveData<Boolean>()
    val connection: LiveData<Boolean> by lazy { _connection }

    fun init() {
        bookRepository = BookRepository()
        volumesResponseLiveData = bookRepository?.getVolumesResponseLiveData()
    }

    fun searchVolumes(keyword: String?, startIndex: Int, maxResults: Int) {
        bookRepository?.searchVolumes(keyword, startIndex, maxResults)
    }

    fun getVolumesResponseLiveData(): LiveData<VolumesResponse?>? {
        return volumesResponseLiveData
    }

    fun SetDataList(list: List<Item>) {
        itemList = list
    }

    // coroutine to check the network state
    fun checkInternetConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Connect to Google DNS to check for connection
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                socket.connect(socketAddress, timeoutMs)
                socket.close()

                _connection.postValue(true)
            }
            catch(ex: IOException) {
                _connection.postValue(false)
            }
        }
    }
}