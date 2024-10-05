package com.example.lab3

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task6(){
    var pickedTime by remember { mutableStateOf(LocalTime.now()) }
    var timeDialogController by  remember { mutableStateOf(false) }
    var sliderPositionMinutes by remember { mutableFloatStateOf(0f) }
    var sliderPositionHour by remember { mutableFloatStateOf(0f)}


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {timeDialogController = true}
        ) {
            Text("Pick Time")
        }
        Text("${pickedTime.hour}:${pickedTime.minute}")


        if(timeDialogController){
            val timePickerState = rememberTimePickerState(initialHour = pickedTime.hour,
                initialMinute = pickedTime.minute, is24Hour = true)

            TimePickerDialog(
                onDismiss = {timeDialogController = false},
                onConfirm = {
                    pickedTime = LocalTime.of(timePickerState.hour,
                        timePickerState.minute)
                    Log.i("Time", "$pickedTime")
                    timeDialogController = false
                }
            ){
                TimePicker(state = timePickerState)
            }
        }

        Slider(
            value = sliderPositionHour,
            onValueChange = { sliderPositionHour = it
                pickedTime = LocalTime.of(it.toInt(),
                    pickedTime.minute)},
            valueRange = 0f..23f
        )

        Slider(
            value = sliderPositionMinutes,
            onValueChange = { sliderPositionMinutes = it
                pickedTime = LocalTime.of(pickedTime.hour,
                    it.toInt())},
            valueRange = 0f..59f
        )
    }
}


@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = {onDismiss()}
            ) { Text("Cancel") }
        },
        confirmButton = {
            TextButton(onClick = {onConfirm()}) {
                Text("Ok")
            }
        },
        text = {content()}
    )
}