package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.angelruedadev.watchvault.R

@Composable
fun TitleSectionCollection(
    title: AnnotatedString,
    onSortSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf("default") }

    val dropdownItems = listOf(
        "Title ↑" to "titleAsc",
        "Title ↓" to "titleDesc",
        "Rating ↑" to "ratingAsc",
        "Rating ↓" to "ratingDesc",
        "Default" to "default"
    )

    var iconOffset by remember { mutableStateOf(IntOffset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp, lineHeight = 30.sp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        val windowPos = coordinates.windowPosition()
                        iconOffset = IntOffset(windowPos.x.toInt(), (windowPos.y + coordinates.size.height).toInt())
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "Sort Options",
                    tint = colorResource(R.color.lime),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { expanded = true }
                )
            }
        }

        if (expanded) {
            Popup(
                offset = iconOffset,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),

            ) {
                Card(
                    modifier = Modifier
                        .width(150.dp)
                        .border(1.dp, colorResource(R.color.lime))
                        .background(colorResource(R.color.dark_blue)),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RectangleShape
                ) {
                    Column {
                        dropdownItems.forEachIndexed { index, (label, key) ->
                            val isSelected = key == selectedSort
                            val backgroundColor = if (isSelected) colorResource(R.color.lime) else colorResource(R.color.dark_blue)
                            val textColor = if (isSelected) colorResource(R.color.dark_blue) else colorResource(R.color.lime)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor, RectangleShape)
                                    .clickable {
                                        selectedSort = key
                                        onSortSelected(key)
                                        expanded = false
                                    }
                                    .height(40.dp)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = label,
                                    color = textColor,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            if (index < dropdownItems.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.lime))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


fun LayoutCoordinates.windowPosition(): Offset {
    val position = this.localToWindow(Offset.Zero)
    return Offset(position.x, position.y)
}

