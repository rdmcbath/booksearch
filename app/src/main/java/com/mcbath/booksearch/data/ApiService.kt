package com.mcbath.booksearch.data

import com.mcbath.booksearch.models.VolumesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/books/v1/volumes")
    fun searchVolumes(
        @Query("q") query: String?,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): Call<VolumesResponse?>?
}