package com.route.newsappc42gsunwed.api

import com.route.newsappc42gsunwed.api.model.NewsResponse
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("sources")
    fun getSources(
        @Query("category") categoryApiID: String? = null
    ): Call<SourcesResponse>

    @GET("everything")
    suspend fun getNewsBySource(
        @Query("sources") sourceId: String? = null,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10
    ): NewsResponse
}
