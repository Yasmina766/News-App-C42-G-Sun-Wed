package com.route.newsappc42gsunwed.splash

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.route.newsappc42gsunwed.MainActivity
import com.route.newsappc42gsunwed.ui.theme.NewsAppC42GSunWedTheme
import com.route.newsappc42gsunwed.R

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // API ()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3_000)
        setContent {
            NewsAppC42GSunWedTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    SplashContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SplashContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier)
        Image(
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.news_app_logo else R.drawable.news_app_logo_light),
            contentDescription = stringResource(
                R.string.news_app_logo_image
            ),
            modifier = Modifier.fillMaxHeight(0.3F),
            contentScale = ContentScale.Crop

        )
        Image(
            painter = painterResource(if (isSystemInDarkTheme()) R.drawable.signature else R.drawable.signature_light),
            contentDescription = stringResource(R.string.app_development_signature),
            modifier = Modifier.fillMaxWidth(0.5F),
            contentScale = ContentScale.Crop

        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SplashContentPreview() {
    SplashContent()
}

