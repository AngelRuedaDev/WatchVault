package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.angelruedadev.watchvault.domain.model.Genre

@Composable
fun GenreFilterDialog(
    genres: List<Genre>,
    initialSelectedIds: List<Int>,
    onDismiss: () -> Unit,
    onApply: (List<Int>) -> Unit
) {
    var selectedIds by remember(initialSelectedIds) { mutableStateOf(initialSelectedIds) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = { onApply(selectedIds) }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onApply(emptyList())
                onDismiss()
            }) {
                Text("Clear")
            }
        },
        title = {
            Text("Select a genre: ")
        },
        text = {
            LazyColumn {
                items(genres) { genre ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedIds = if (selectedIds.contains(genre.id)) {
                                    selectedIds - genre.id
                                } else {
                                    selectedIds + genre.id
                                }
                            }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedIds.contains(genre.id),
                            onCheckedChange = {
                                selectedIds = if (it) {
                                    selectedIds + genre.id
                                } else {
                                    selectedIds - genre.id
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(genre.name)
                    }
                }
            }
        }
    )
}

@Composable
fun FilterButton(onClick: () -> Unit, tint: Color) {
    IconButton(onClick = onClick) {
        Icon(
            Icons.Default.Menu,
            contentDescription = "Filter Icon",
            tint = tint
        )
    }
}