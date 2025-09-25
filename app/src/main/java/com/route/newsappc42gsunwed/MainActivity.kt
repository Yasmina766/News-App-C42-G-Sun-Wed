package com.route.newsappc42gsunwed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.route.newsappc42gsunwed.ui.components.NewsToolbar
import com.route.newsappc42gsunwed.ui.routes.CategoriesDestination
import com.route.newsappc42gsunwed.ui.routes.NewsDestination
import com.route.newsappc42gsunwed.ui.screens.categories.CategoriesScreen
import com.route.newsappc42gsunwed.ui.screens.news.NewsScreen
import com.route.newsappc42gsunwed.ui.theme.NewsAppC42GSunWedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // android 15 -> Forced to Enable Edge-to-edge
        setContent {
            // 1- MVVM
            // 2- Pagination (android paging 3 )
            NewsAppC42GSunWedTheme {
                LaunchedEffect(Unit) {
//                    getSources()
                }
                val homeText = stringResource(R.string.home)
                val title = remember {
                    mutableStateOf(homeText)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    topBar = {
                        NewsToolbar(title = title.value, onSearchClick = {}, onMenuButtonClick = {})
                    },
                ) { innerPadding ->
                    // Navigation Component ->
                    // 1- Nav Host (Jetpack Compose and XML) => FrameLayout (XML)
                    // 2- Nav Graph ()
                    NavHost(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding()
                        ),
                        navController = navController,
                        startDestination = CategoriesDestination
                    ) {
                        composable<CategoriesDestination> {
                            CategoriesScreen(navController = navController)
                        }
                        composable<NewsDestination> {
                            val destination = it.toRoute<NewsDestination>()
                            NewsScreen(destination.categoryAPIId ?: "")
                        }
                    }
                }
            }
        }
    }
}

