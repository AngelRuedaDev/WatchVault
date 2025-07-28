package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelruedadev.watchvault.R

@Composable
fun GenreItem(name: String) {
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.lime))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = name.lowercase(),
            color = colorResource(R.color.dark_blue),
            style = MaterialTheme.typography.bodySmall,
            fontSize = 15.sp,
        )
    }
}