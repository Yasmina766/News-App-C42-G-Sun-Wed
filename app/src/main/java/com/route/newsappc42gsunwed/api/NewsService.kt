package com.route.newsappc42gsunwed.api

import com.route.newsappc42gsunwed.api.model.NewsResponse
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("sources")
    fun getSources(
        @Query("apiKey") apiKey: String = "c027443ca9624422bfbe9b160b9ec11a",
        @Query("category") categoryApiID: String? = null
    ): Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySource(
        @Query("apiKey") apiKey: String = "c027443ca9624422bfbe9b160b9ec11a",
        @Query("sources") sourceId: String? = null
    ): Call<NewsResponse>
}
