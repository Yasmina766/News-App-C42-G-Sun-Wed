package com.route.newsappc42gsunwed.ui.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.route.newsappc42gsunwed.R
import com.route.newsappc42gsunwed.model.CategoryDM
import com.route.newsappc42gsunwed.ui.routes.NewsDestination


@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.good_morning_here_is_some_news_for_you),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
        )
        CategoriesList(navController)
    }
}

@Composable
fun CategoryItem(
    item: CategoryDM,
    index: Int,
    onCategoryClick: (CategoryDM) -> Unit,
    modifier: Modifier = Modifier
) {
    // Left Item
    Card(
        modifier = modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            onCategoryClick(item)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
            contentColor = MaterialTheme.colorScheme.background
        )
    ) {
        if (index % 2 == 0) {
            EvenCategoryItem(item)
        } else {
            OddCategoryItem(item)
        }
    }
    //  right item
}

@Composable
fun EvenCategoryItem(item: CategoryDM, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(item.image ?: R.drawable.news_app_logo),
            contentDescription = stringResource(R.string.category_image),
            modifier = Modifier
                .fillMaxWidth(0.4F)
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.weight(1F))
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = 36.dp)
        ) {
            Text(stringResource(item.title ?: R.string.home), fontSize = 42.sp)
            Spacer(Modifier.padding(16.dp))
            Box(modifier = Modifier) {
                Text(
                    stringResource(R.string.view_all), modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer, shape = CircleShape
                        )
                        .padding(
                            start = 16.dp, end = 48.dp, top = 8.dp, bottom = 8.dp
                        ),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onBackground

                )
                Image(
                    painterResource(R.drawable.arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background, CircleShape,
                        )
                        .size(36.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun ColumnScope.OddCategoryItem(item: CategoryDM, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(horizontal = 36.dp)
        ) {
            Text(stringResource(item.title ?: R.string.home), fontSize = 42.sp)
            Spacer(Modifier.padding(16.dp))
            Box(modifier = Modifier) {
                Text(
                    stringResource(R.string.view_all), modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer, shape = CircleShape
                        )
                        .padding(
                            start = 16.dp, end = 48.dp, top = 8.dp, bottom = 8.dp
                        ),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onBackground

                )
                Image(
                    painterResource(R.drawable.arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background, CircleShape,
                        )
                        .size(36.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
        Image(
            painter = painterResource(item.image ?: R.drawable.news_app_logo),
            contentDescription = stringResource(R.string.category_image),
            modifier = Modifier
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.weight(1F))

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryItemPreviewLeft() {
    CategoryItem(CategoryDM.getCategoriesList()[0], index = 0, onCategoryClick = {

    })
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreviewRight() {
    CategoryItem(CategoryDM.getCategoriesList()[0], index = 1, onCategoryClick = {})
}

@Composable
fun CategoriesList(navHostController: NavHostController, modifier: Modifier = Modifier) {
    val categories = CategoryDM.getCategoriesList()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryItem(item, index, onCategoryClick = {
                navHostController.navigate(NewsDestination(it.apiId ?: ""))
            })
        }
    }
}
