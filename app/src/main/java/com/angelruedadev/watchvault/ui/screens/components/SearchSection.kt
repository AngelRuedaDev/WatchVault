package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelruedadev.watchvault.R

@Composable
fun SearchSection(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onFilterClick: () -> Unit,
    isIdsEmpty: Boolean,
    onCloseSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            query = query,
            onQueryChanged = onQueryChange,
            modifier = Modifier.weight(1f),
            onSearch = onSearch,
            onCloseSearch = onCloseSearch
        )
        FilterButton(onClick = onFilterClick, isFilterEmpty = isIdsEmpty)
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    onCloseSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .height(26.dp)
            .fillMaxWidth()
            .background(color = colorResource(R.color.lime), shape = RectangleShape)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Start,
                color = colorResource(R.color.dark_blue)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                onCloseSearch()
                keyboardController?.hide()
            }),
            cursorBrush = SolidColor(colorResource(R.color.dark_blue)),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "looking for something...?",
                        color = colorResource(R.color.dark_blue).copy(alpha = 0.75f),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
            }
        )
    }
}