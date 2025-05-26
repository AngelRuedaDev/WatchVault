package com.angelruedadev.watchvault.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RateDialog(
    currentRating: Float,
    onDismiss: () -> Unit,
    onConfirm: (Float) -> Unit
) {
    val steps = listOf(0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5f)
    var sliderValue by remember { mutableStateOf(currentRating.coerceIn(0f, 5f)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(sliderValue)
                onDismiss()
            }) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = {
            Text("Rate: ")
        },
        text = {
            Column {
                Slider(
                    value = sliderValue,
                    onValueChange = {
                        // Encuentra el paso más cercano
                        sliderValue = steps.minByOrNull { step -> kotlin.math.abs(step - it) } ?: it
                    },
                    valueRange = 0f..5f,
                    steps = 9, // Para tener pasos entre 0 y 5 con incrementos de 0.5
                )
                Text("Rating: ${sliderValue}/5")
            }
        }
    )
}