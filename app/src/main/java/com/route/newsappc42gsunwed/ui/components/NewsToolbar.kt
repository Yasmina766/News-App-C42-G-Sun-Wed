package com.route.newsappc42gsunwed.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.newsappc42gsunwed.R
import com.route.newsappc42gsunwed.ui.theme.black


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(
    modifier: Modifier = Modifier, title: String, onMenuButtonClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(title)
        },
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.ic_menu),
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 2.dp)
                    .width(24.dp)
                    .clickable {
                        onMenuButtonClick()
                    },
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.open_side_menu_icon),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        actions = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.icon_search),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .width(24.dp)
                    .clickable {
                        onSearchClick()
                    },
                contentScale = ContentScale.Crop, // scaleType => fitXY
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    )
}

@Preview()
@Composable
private fun NewsToolbarPreview() {
    NewsToolbar(title = "Home", onMenuButtonClick = {}, onSearchClick = {})
}

@Preview(backgroundColor = 0xFF171717)
@Composable
private fun NewsDarkToolbarPreview() {
    NewsToolbar(title = "Home", onMenuButtonClick = {}, onSearchClick = {})
}