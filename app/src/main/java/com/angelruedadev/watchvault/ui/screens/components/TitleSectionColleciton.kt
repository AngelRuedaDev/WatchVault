package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelruedadev.watchvault.R

@Composable
fun TitleSectionCollection(title: String, onSortSelected: (String) -> Unit,){
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.weight(1f))

        Box {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Sort Options",
                tint = colorResource(R.color.dark_blue),
                modifier = Modifier.size(30.dp).clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Title ↑") },
                    onClick = {
                        onSortSelected("titleAsc")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Title ↓") },
                    onClick = {
                        onSortSelected("titleDesc")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rating ↑") },
                    onClick = {
                        onSortSelected("ratingAsc")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Rating ↓") },
                    onClick = {
                        onSortSelected("ratingDesc")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Default") },
                    onClick = {
                        onSortSelected("default")
                        expanded = false
                    }
                )
            }
        }
    }
}