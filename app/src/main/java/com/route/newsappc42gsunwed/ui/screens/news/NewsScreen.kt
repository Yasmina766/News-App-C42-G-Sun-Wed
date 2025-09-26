package com.route.newsappc42gsunwed.ui.screens.news


import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.route.newsappc42gsunwed.R
import com.route.newsappc42gsunwed.api.ApiManager
import com.route.newsappc42gsunwed.api.model.ArticlesItem
import com.route.newsappc42gsunwed.api.model.NewsResponse
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.api.model.SourcesResponse
import com.route.newsappc42gsunwed.ui.components.NewsToolbar
import com.route.newsappc42gsunwed.ui.theme.NewsAppC42GSunWedTheme
import com.route.newsappc42gsunwed.ui.theme.gray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(categoryAPIId: String,isSearching: Boolean, searchQuery: String, modifier: Modifier = Modifier) {
    var selectedArticle by remember {
        mutableStateOf<ArticlesItem?>(null)
    }
        Column(modifier = modifier.fillMaxSize()) {
            val newsList = remember { mutableStateListOf<ArticlesItem>() }
            SourcesTabRow(categoryAPIId) {
                newsList.clear()
                newsList.addAll(it)
            }
            Spacer(Modifier.height(8.dp))
            val filteredNews = newsList.filter{
               if(isSearching&&searchQuery.isNotBlank())
               {
                   it.title?.contains(searchQuery, ignoreCase = true) == true
               } else {
                   true
               }
            }
            NewsLazyColumn(filteredNews){ clickedArticle ->
                selectedArticle = clickedArticle
            }
        }
    selectedArticle?.let { article ->
        NewsBottomSheet(articleItem = article, onClose = { selectedArticle = null })
    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    NewsScreen("", isSearching = false, searchQuery = "")
}

@Composable
fun SourcesTabRow(
    categoryApiId: String,
    modifier: Modifier = Modifier,
    onNewsListUpdated: (List<ArticlesItem>) -> Unit
) {
    val sources = remember {
        mutableStateListOf<SourcesItemDM>()
    }
    var selectedIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(categoryApiId) {
        getSources(categoryApiId) {fetchedSources ->
            sources.clear()
            sources.addAll(fetchedSources)
            if(fetchedSources.isNotEmpty())
            {
                selectedIndex = 0
                getNewsBySource (fetchedSources[0].id ?: "") { initialArticles ->
                    onNewsListUpdated(initialArticles)

                }
            } else {
                onNewsListUpdated(emptyList())
            }

        }
    }
    LazyRow(modifier) {
        itemsIndexed(sources) { index, item ->
            SourcesItem(item, index, selectedIndex) { clickedIndex, sourcesItem ->
                selectedIndex = clickedIndex
                getNewsBySource(sourcesItem.id ?: "") { articles ->
                    onNewsListUpdated(articles)
                }
            }
        }
    }
}

@Composable
fun SourcesItem(
    sourcesItemDM: SourcesItemDM,
    index: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onSourceClickListener: (Int, SourcesItemDM) -> Unit
) {
    if (index == selectedIndex)
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.Underline,
            modifier = modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    else
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onBackground,
            textDecoration = TextDecoration.None,
            modifier = modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )

}

@Preview(showBackground = true)
@Composable
private fun SourcesItemPreview() {
    SourcesItem(
        sourcesItemDM = SourcesItemDM(
            country = "EG",
            name = "Al-Jazeera",
            description = "a news sources for general information",
            language = "Arabic",
            id = "1",
            category = "General",
        ),
        index = 1,
        selectedIndex = 1,
    ) { index, sourcesItem -> }
}

@Preview
@Composable
private fun SourcesTabRowPreview() {
    SourcesTabRow("") {}
}

@Composable
fun NewsLazyColumn(newsList: List<ArticlesItem>, modifier: Modifier = Modifier,
                   onArticleClick: (ArticlesItem) -> Unit) {
    LazyColumn {
        items(newsList) {
            NewsCard(it,  onClick = { onArticleClick(it) })
        }
    }
}

@Preview
@Composable
private fun NewsLazyColumnPreview() {
    NewsLazyColumn(listOf(), onArticleClick = {})
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articleItem: ArticlesItem, modifier: Modifier = Modifier,onClick:()->Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        // Image
        GlideImage(
            articleItem.urlToImage ?: "",
            contentDescription = articleItem.description,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        // Title
        Text(
            articleItem.title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "By : ${articleItem.author}",
                color = gray,
                fontWeight = FontWeight.W500,
            )
            Text(
                text = articleItem.publishedAt ?: "",
                color = gray,
                fontWeight = FontWeight.W500,
            )

        }

    }
}

@Preview
@Composable
private fun NewsCardPreview() {
    NewsCard(
        ArticlesItem(
            publishedAt = "21-9-2025",
            author = "BBC News",
            description = "News Description",
            title = "News Title",
            content = " News Content",
        ), onClick = {}
    )
}

fun getSources(categoryApiId: String, onResponse: (List<SourcesItemDM>) -> Unit) {
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
                    onResponse(responseBody?.sources ?: listOf())

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

fun getNewsBySource(sourceId: String, onResponse: (List<ArticlesItem>) -> Unit) {
    ApiManager.getNewsService().getNewsBySource(sourceId = sourceId)
        .enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse?>,
                response: Response<NewsResponse?>
            ) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.articles ?: listOf())
                } else {
                    Log.e("TAG", "onResponse: on Error")
                }
            }

            override fun onFailure(
                call: Call<NewsResponse?>,
                t: Throwable
            ) {
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewsBottomSheet(articleItem: ArticlesItem,onClose:()->Unit,modifier: Modifier = Modifier) {
    ModalBottomSheet(
        onDismissRequest = { onClose() },
        containerColor = MaterialTheme.colorScheme.background
    ){
        Column(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                articleItem.urlToImage ?: "",
                contentDescription = articleItem.description,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(articleItem.title ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground)
            val context = LocalContext.current
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, articleItem.url?.toUri())
                context.startActivity(intent)
            },modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.95f)
                .height(55.dp)
                .align(alignment = Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("View Full Articel", fontSize = 18.sp, fontWeight = FontWeight.W700,
                    fontStyle = FontStyle.Normal)
            }
        }
    }
}

@Preview
@Composable
private fun NewsBottomSheetPreview() {
    NewsAppC42GSunWedTheme(darkTheme = false){
        NewsBottomSheet( ArticlesItem(
            publishedAt = "21-9-2025",
            author = "BBC News",
            description = "News Description",
            title = "News Title",
            content = " News Content",
        ), onClose = {})
    }

}

@Composable
fun NewsSearchBar(query:String,
                  changedQuery:(String)->Unit,
                  deleteClick:()->Unit,
                  onCloseSearch:()->Unit,
                  modifier: Modifier = Modifier) {
    TextField(
        value = query,
        onValueChange = changedQuery,
        placeholder = {Text("Search", fontSize = 20.sp, fontWeight = FontWeight.W500)},
        leadingIcon = {
            IconButton(onClick = onCloseSearch) {
                Icon(painter = painterResource(R.drawable.back_arrow),
                    contentDescription = "Search_Icon",
                    modifier = Modifier.size(25.dp), tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        trailingIcon = {
            if(query.isNotEmpty())
            {
                IconButton(onClick = deleteClick) {
                    Icon(painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "Delete_icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(15.dp)
                        )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(30),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 35.dp, start = 8.dp, end = 8.dp).border(3.dp, color = MaterialTheme.colorScheme.background,shape = RoundedCornerShape(30)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Preview()
@Composable
private fun NewsSearchBarPreview() {
        NewsSearchBar(query = "", changedQuery = {""}, deleteClick = {} , onCloseSearch = {})
}
