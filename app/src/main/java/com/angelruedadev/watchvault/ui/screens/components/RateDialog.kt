package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.angelruedadev.watchvault.R
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateDialog(
    currentRating: Float,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    val steps = listOf(0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f)
    var sliderValue by remember { mutableStateOf(currentRating.coerceIn(0f, 5f)) }
    var sliderWidth by remember { mutableStateOf(0f) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xD91C1F27), shape = RectangleShape)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("add your ")
                        }
                        withStyle(
                            SpanStyle(
                                color = colorResource(R.color.lime),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("rating")
                        }
                    },
                    modifier = Modifier.align(Alignment.Start),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(42.dp))

                // Slider y burbuja
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { layoutCoordinates ->
                            sliderWidth = layoutCoordinates.size.width.toFloat()
                        },
                    contentAlignment = Alignment.TopStart
                ) {
                    // Slider
                    Slider(
                        value = sliderValue,
                        onValueChange = {
                            sliderValue =
                                steps.minByOrNull { step -> kotlin.math.abs(step - it) } ?: it
                        },
                        valueRange = 0f..5f,
                        steps = 9,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Transparent,
                            activeTrackColor = colorResource(R.color.lime),
                            inactiveTickColor = colorResource(R.color.dark_blue)
                        ),
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .border(1.dp, Color(0xFFCBFF00), shape = RectangleShape)
                                    .background(Color.Transparent)
                                    .align(Alignment.Center) // <- Esto alinea el thumb al centro vertical
                            )
                        }
                    )

                    // Cálculo dinámico para centrar la burbuja sobre el thumb
                    val bubbleOffset: Dp = with(LocalDensity.current) {
                        val thumbPercent = sliderValue / 5f
                        val horizontalPadding = 24.dp.toPx()
                        val availableWidth = sliderWidth - horizontalPadding * 2
                        val thumbX = horizontalPadding + (availableWidth * thumbPercent)
                        (thumbX - 30.dp.toPx()).coerceIn(0f, sliderWidth).toDp() // centro burbuja
                    }

                    // Burbuja flotante
                    Box(
                        modifier = Modifier
                            .offset(x = bubbleOffset, y = (-15).dp)
                            .background(colorResource(R.color.lime), RectangleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = String.format("%.1f ★", sliderValue),
                            color = colorResource(R.color.dark_blue),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Discard button
                    OutlinedButton(
                        onClick = onDismiss,
                        border = BorderStroke(1.dp, colorResource(R.color.lime)),
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape
                    ) {
                        Text(
                            "discard",
                            color = colorResource(R.color.lime),
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp

                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Apply button
                    Button(
                        onClick = {
                            onConfirm(sliderValue)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.lime)),
                        modifier = Modifier.weight(1f),
                        shape = RectangleShape
                    ) {
                        Text(
                            "apply",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}