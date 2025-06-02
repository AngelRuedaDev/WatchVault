package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angelruedadev.watchvault.R

@Composable
fun TitleSection(title: String, onSearchClick: () -> Unit, isFiltered: Boolean){
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White)) {
            if (isFiltered){
                append("filtered ")
            }else{
                append("all our ")
            }
        }
        withStyle(style = SpanStyle(color = colorResource(R.color.lime))) {
            append(title)
        }
    }

    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.weight(1f))

        if (isFiltered){
            Icon(
                painter  = painterResource(id = R.drawable.ic_search_applied),
                contentDescription = "search Icon",
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 8.dp).size(25.dp).clickable { onSearchClick() }
            )
        }else{
            Icon(
                painter  = painterResource(id = R.drawable.ic_search),
                contentDescription = "search Icon",
                tint = colorResource(R.color.white),
                modifier = Modifier.padding(end = 8.dp).size(25.dp).clickable { onSearchClick() }
            )
        }

    }

}