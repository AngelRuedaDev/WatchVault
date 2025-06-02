package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.angelruedadev.watchvault.R
import com.angelruedadev.watchvault.domain.model.Genre

@Composable
fun GenreFilterDialog(
    genres: List<Genre>,
    initialSelectedIds: List<Int>,
    onDismiss: () -> Unit,
    onApply: (List<Int>) -> Unit,
    onCloseSearch: () -> Unit
) {
    var selectedIds by remember(initialSelectedIds) { mutableStateOf(initialSelectedIds) }

    Dialog(onDismissRequest = {
        onDismiss()
        onCloseSearch()
    }) {
        Surface(
            color = Color(0xD91C1F27),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .heightIn(min = 300.dp, max = 500.dp) // altura controlada
            ) {
                // Title
                Row(Modifier.padding(bottom = 12.dp)) {
                    Text(
                        text = "select ",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.white),
                        fontSize = 24.sp
                    )
                    Text(
                        text = "genre(s)",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.lime),
                        fontSize = 24.sp
                    )
                }

                // Lista scrollable limitada
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(genres) { genre ->
                        val isSelected = selectedIds.contains(genre.id)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 6.dp)
                                .clickable {
                                    selectedIds = if (isSelected) {
                                        selectedIds - genre.id
                                    } else {
                                        selectedIds + genre.id
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SquareCheckbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    selectedIds = if (it) {
                                        selectedIds + genre.id
                                    } else {
                                        selectedIds - genre.id
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = genre.name,
                                color = Color.White,
                                fontSize = 15.sp,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botones
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .border(1.dp, colorResource(R.color.lime))
                            .clickable {
                                onApply(emptyList())
                                onDismiss()
                                onCloseSearch()
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "clear",
                            color = colorResource(R.color.lime),
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(colorResource(R.color.lime))
                            .clickable {
                                onApply(selectedIds)
                                onCloseSearch()
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "apply",
                            color = colorResource(R.color.dark_blue),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(onClick: () -> Unit, isFilterEmpty: Boolean) {
    if (isFilterEmpty){
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_btn_line),
                contentDescription = "Filter Icon",
                tint = colorResource(R.color.lime)
            )
        }
    }else {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_btn),
                contentDescription = "Filter Icon",
                tint = colorResource(R.color.lime)
            )
        }
    }
}

@Composable
fun SquareCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp = 18.dp,
    checkedColor: Color = colorResource(R.color.lime),
    borderColor: Color = colorResource(R.color.lime),
    checkmarkColor: Color = colorResource(R.color.dark_blue)
) {
    Box(
        modifier = Modifier
            .size(size)
            .border(1.dp, borderColor, RectangleShape)
            .background(
                color = if (checked) checkedColor else Color.Transparent,
                shape = RectangleShape
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checkmark",
                tint = checkmarkColor,
                modifier = Modifier.size(size / 1.5f)
            )
        }
    }
}