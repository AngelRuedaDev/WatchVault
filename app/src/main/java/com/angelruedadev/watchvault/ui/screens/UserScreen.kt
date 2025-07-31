package com.angelruedadev.watchvault.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.ui.navigation.AppScreens

@Composable
fun UserScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark_blue))
            .padding(WindowInsets.systemBars.only(WindowInsetsSides.Top).asPaddingValues())
    ) {
        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            val titleText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White)) {
                    append("my ")
                }
                withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
                    append("watchvault")
                }
            }

            Text(
                text = titleText,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            CollectionSection("movies", navController)
            Spacer(Modifier.padding(15.dp))
            CollectionSection("TV series", navController)
        }
    }
}

@Composable
fun CollectionSection(title: String, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        CollectionItemRow(
            text = "liked",
            painter = painterResource(id = R.drawable.ic_like),
            onClick = {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Likes")
            }
        )

        CollectionItemRow(
            text = "watchlist",
            painter = painterResource(id = R.drawable.ic_watchlist),
            onClick = {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Watchlist")
            }
        )

        CollectionItemRow(
            text = "rated",
            painter = painterResource(id = R.drawable.ic_star),
            onClick = {
                navController.navigate(route = AppScreens.CollectionScreen.route + "/${title}Rated")
            }
        )
    }
}

@Composable
fun CollectionItemRow(
    text: String,
    onClick: () -> Unit,
    painter: Painter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(0.dp))
            .border(
                width = 1.dp,
                color = colorResource(R.color.lime),
                shape = RoundedCornerShape(0.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = colorResource(R.color.lime),
                style = MaterialTheme.typography.bodyMedium
            )

            Icon(
                painter = painter,
                contentDescription = "$text icon",
                tint = colorResource(R.color.lime)
            )
        }
    }
}
