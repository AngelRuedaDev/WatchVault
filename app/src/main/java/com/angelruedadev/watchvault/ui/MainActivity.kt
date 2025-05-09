package com.angelruedadev.watchvault.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.angelruedadev.watchvault.BuildConfig
import com.angelruedadev.watchvault.ui.navigation.Navigation
import com.angelruedadev.watchvault.ui.theme.WatchVaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val apiKey = BuildConfig.TMDB_API_KEY
        val token = BuildConfig.TMDB_ACCESS_TOKEN

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WatchVaultTheme {
                Navigation()
            }
        }
    }
}
