package com.route.newsappc42gsunwed.ui.screens.news

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.route.newsappc42gsunwed.api.ApiManager
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import com.route.newsappc42gsunwed.paging.ArticlesPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val sourcesList = mutableStateListOf<SourcesItemDM>()
    val selectedSourceId = MutableStateFlow<String>("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val articlesList = selectedSourceId.flatMapLatest {
        Log.e("TAG404", it)
        if (it.isEmpty()) {
            emptyFlow()
        } else
            Pager(
                config = PagingConfig(pageSize = 10),
                1,
                pagingSourceFactory = { ArticlesPagingSource(it) },
            ).flow.cachedIn(viewModelScope)
    }


    fun getSources(categoryApiId: String) {
        ApiManager.getNewsService().getSources(categoryApiID = categoryApiId)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse?>,
                    response: Response<SourcesResponse?>
                ) {
                    if (response.isSuccessful) {
                        // Handle Success State
                        val responseBody = response.body()
                        Log.e("TAG", "onResponse status : ${responseBody?.status}")
                        Log.e("TAG", "onResponse sources : ${responseBody?.sources}")
                        sourcesList.addAll(responseBody?.sources ?: listOf())

                    } else {
                        // Handle Error State
                        Log.e("TAG", "onResponse: Error : ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<SourcesResponse?>, t: Throwable) {
                    Log.e("TAG", "onResponse: Error : ${t.message}")
                }

            })  // background thread .
    }
}
