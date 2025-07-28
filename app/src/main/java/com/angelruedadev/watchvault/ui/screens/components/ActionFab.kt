package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelruedadev.watchvault.R

@Composable
fun ActionFabRow(
    text: String,
    onClick: () -> Unit,
    color: Color,
    painter: Painter
) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Max)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = text,
                color = colorResource(R.color.white),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(90.dp)
            )

            SmallFloatingActionButton(
                onClick = onClick,
                containerColor = color,
                shape = CircleShape
            ) {
                Icon(
                    painter = painter,
                    contentDescription = text,
                    tint = colorResource(R.color.dark_blue)
                )
            }
        }
    }
}