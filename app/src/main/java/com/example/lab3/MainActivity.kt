package com.example.lab3

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lab3.ui.theme.Lab3Theme
import java.time.LocalTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyApp(modifier: Modifier = Modifier){
//    var pickedTime by remember { mutableStateOf(LocalTime.now()) }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.Center
        ) {
        item {
            var number by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            OutlinedTextField(
                value = number,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        number = newValue
                    }},
                label = { Text("Number") },
                placeholder = {Text("Enter some number")},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            )
        }
        item {
            var isPressed by remember { mutableStateOf(false) }
            Button(
                onClick = { isPressed = !isPressed },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPressed) Color.DarkGray else Color.Unspecified
                ),
            ) {
                Text("Click Me")
            }
        }
        item {
            var isPressed by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.pointerInteropFilter { event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                isPressed = true
                                true
                            }
                            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                isPressed = false
                                true
                            }
                            else -> false
                        }
                    }
                    ) {
                    Text("Click Me")
                }
                Text(text = if (isPressed) "Нажата" else "Отпущена")
            }
        }

        item{
            var counter by remember { mutableStateOf(0) }
            Button(
                onClick = { counter++ }
            ) {
                Text("Counter: ${counter}")
            }
        }

        item { Task5() }
        item { Task6() }
        item { Task7() }
        item { Task8() }
        item {
            var sliderPosition by remember { mutableFloatStateOf(0f)}
//            var sliderPositionHour by remember { mutableFloatStateOf(0f)}
//            var sliderPositionMinutes by remember { mutableFloatStateOf(0f)}

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it
//                                    pickedTime = LocalTime.of(it.toInt(),
//                                        pickedTime.minute)
                                    },
                    valueRange = 0f..100f
                )
                Text(text = "${(sliderPosition).toInt()}")

//                Slider(
//                    value = sliderPositionMinutes,
//                    onValueChange = { sliderPositionMinutes = it
//                        pickedTime = LocalTime.of(pickedTime.hour,
//                            it.toInt())},
//                    valueRange = 0f..59f
//                )
//                Text(text = "${(sliderPositionMinutes).toInt()}")
            }
        }
    }
}