package com.mcbath.booksearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcbath.booksearch.models.VolumesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Saving and loading app data*/

class BookRepository {
    private val bookSearchService: ApiService
    private val volumesResponseLiveData: MutableLiveData<VolumesResponse> = MutableLiveData()

    companion object {
        private const val BOOK_API_BASE_URL = "https://www.googleapis.com/"
    }

    /*Retrofit2 is used to build the client used to interact with the Google Books API using the
    ApiService interface.A HttpLoggingInterceptor on the client logs the body
    of the interactions with the API to allow for improved debugging.*/

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        bookSearchService = Retrofit.Builder()
            .baseUrl(BOOK_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

/*  This BookRepository includes a method searchVolumes(String, Int, Int). This method
    uses the client created by Retrofit2 to make an API request and handle the API response in the
    case of success or failure. We don't need the api key right now because the current requests aren't
    asking for any private data which would require authorization by Google. We are passing in the
    index parameter and maxResults (20) which will allow us to show 20 new results at a time*/

    fun searchVolumes(keyword: String?, startIndex: Int, maxResults: Int) {
        bookSearchService.searchVolumes(keyword, startIndex, maxResults)
            ?.enqueue(object : Callback<VolumesResponse?> {
                override fun onResponse(
                    call: Call<VolumesResponse?>?,
                    response: Response<VolumesResponse?>
                ) {
                    if (response.body() != null) {
                        volumesResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<VolumesResponse?>?, t: Throwable?) {
                    volumesResponseLiveData.postValue(null)
                }
            })
    }

    fun getVolumesResponseLiveData(): LiveData<VolumesResponse?> {
        return volumesResponseLiveData
    }
}