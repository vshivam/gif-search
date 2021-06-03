package com.search.giphy.search.data

import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v1/gifs/search")
    suspend fun getGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): NetworkResponse<SearchResponseRaw, Unit>
}