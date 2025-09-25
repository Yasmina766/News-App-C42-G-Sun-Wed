package com.route.newsappc42gsunwed.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.route.newsappc42gsunwed.api.ApiManager
import com.route.newsappc42gsunwed.api.model.ArticlesItemDM

class ArticlesPagingSource(val sourceId: String) : PagingSource<Int, ArticlesItemDM>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItemDM> {
        val page = params.key ?: 1
        return try {
            Log.e("TAG", "load: $sourceId")
            val response =
                ApiManager.getNewsService().getNewsBySource(sourceId = sourceId, page = page)
            val articles = response.articles ?: listOf()
            LoadResult.Page(articles, null, page + 1)
        } catch (e: Exception) {
            LoadResult.Error(Throwable(e.message))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItemDM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}