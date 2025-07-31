package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.angelruedadev.watchvault.R

@Composable
fun RatingStars(rating: Float, modifier: Modifier = Modifier, starSize: Dp = 18.dp) {
    if (rating > 0f) {
        val fullStars = rating.toInt()
        val hasHalfStar = (rating - fullStars) >= 0.25f && (rating - fullStars) < 0.75f

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(fullStars) {
                Icon(painterResource(id = R.drawable.ic_star_filled), contentDescription = "star icon", tint = colorResource(R.color.dark_blue), modifier = Modifier.size(starSize))
            }
            if (hasHalfStar) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_half_star), contentDescription = "half star icon", tint = colorResource(
                        R.color.dark_blue), modifier = Modifier.size(starSize))
            }
        }
    }
}