package com.angelruedadev.watchvault.ui


import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.*
import androidx.compose.ui.res.colorResource
import com.angelruedadev.watchvault.ui.navigation.Navigation
import com.angelruedadev.watchvault.ui.theme.WatchVaultTheme
import dagger.hilt.android.AndroidEntryPoint
import com.angelruedadev.watchvault.R


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            WatchVaultTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.transparent),// aquí también transparente
                ) {
                    Navigation()
                }
            }
        }
    }
}
