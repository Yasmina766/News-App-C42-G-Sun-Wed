package com.route.newsappc42gsunwed.ui.screens.news

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.route.newsappc42gsunwed.api.model.ArticlesItemDM
import com.route.newsappc42gsunwed.api.model.SourcesItemDM
import com.route.newsappc42gsunwed.ui.theme.gray

@Composable
fun NewsScreen(
    categoryAPIId: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        SourcesTabRow(categoryAPIId, viewModel = viewModel)           // empty -> list
        Spacer(Modifier.height(8.dp))

        NewsLazyColumn(viewModel = viewModel) // empty -> listOf
    }
}

@Preview
@Composable
private fun NewsScreenPreview() {
    NewsScreen("")
}

@Composable
fun SourcesTabRow(
    categoryApiId: String,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.getSources(categoryApiId)
    }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    LaunchedEffect(viewModel.sourcesList.isNotEmpty()) {
        if (viewModel.sourcesList.isNotEmpty()) {
            viewModel.selectedSourceId.value = (viewModel.sourcesList[0].id ?: "")
            selectedIndex = 0
        }
    }
    LazyRow(modifier) {
        itemsIndexed(viewModel.sourcesList) { index, item ->
            SourcesItem(item, index, selectedIndex) { clickedIndex, sourcesItem ->
                selectedIndex = clickedIndex
                viewModel.selectedSourceId.value = (sourcesItem.id ?: "")
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
    SourcesTabRow("", viewModel = viewModel())
}

@Composable
fun NewsLazyColumn(
    viewModel: NewsViewModel,
    modifier: Modifier = Modifier
) {
    val articlesPaginatedList =
        viewModel.articlesList.collectAsLazyPagingItems().itemSnapshotList
    LazyColumn {
        items(articlesPaginatedList) {
            NewsCard(it)
        }
    }
}

@Preview
@Composable
private fun NewsLazyColumnPreview() {
    NewsLazyColumn(viewModel())
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articleItem: ArticlesItemDM?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)

    ) {
        // Image
        GlideImage(
            articleItem?.urlToImage ?: "",
            contentDescription = articleItem?.description,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        // Title
        Text(
            articleItem?.title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "By : ${articleItem?.author}",
                color = gray,
                fontWeight = FontWeight.W500,
            )
            Text(
                text = articleItem?.publishedAt ?: "",
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
        ArticlesItemDM(
            publishedAt = "21-9-2025",
            author = "BBC News",
            description = "News Description",
            title = "News Title",
            content = " News Content",
        )
    )
}


