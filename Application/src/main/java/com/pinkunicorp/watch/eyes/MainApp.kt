/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pinkunicorp.watch.eyes

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


enum class State {
    IDLE,
    INSANE,
    MANUAL,
    SPECIAL
}

/**
 * The UI affording the actions the user can take, along with a list of the events and the image
 * to be sent to the wearable devices.
 */
@Composable
fun MainApp(
    onStateClick: (state: State) -> Unit,
    onStartWearableActivityClick: () -> Unit,
    onPositionChange: (x: Float, y: Float, focus: Float) -> Unit,
    onStartAnimation: (number: Int) -> Unit
) {
    var currentState by remember {
        mutableStateOf(State.IDLE)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentState = State.IDLE
                    onStateClick(State.IDLE)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (currentState == State.IDLE) Color.Green else Color.Gray),
                modifier = Modifier.padding(15.dp)
            ) {
                Text(text = "Idl")
            }
            Button(
                onClick = {
                    currentState = State.INSANE
                    onStateClick(State.INSANE)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (currentState == State.INSANE) Color.Green else Color.Gray),
                modifier = Modifier.padding(15.dp)
            ) {
                Text(text = "Insn")
            }
            Button(
                onClick = {
                    currentState = State.MANUAL
                    onStateClick(State.MANUAL)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (currentState == State.MANUAL) Color.Green else Color.Gray),
                modifier = Modifier.padding(15.dp)
            ) {
                Text(text = "Mnl")
            }
            Button(
                onClick = {
                    currentState = State.SPECIAL
                    onStateClick(State.SPECIAL)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = if (currentState == State.SPECIAL) Color.Green else Color.Gray),
                modifier = Modifier.padding(15.dp)
            ) {
                Text(text = "Spcl")
            }
        }
        when(currentState) {
            State.MANUAL -> {
                ManualController(onPositionChange)
            }
            State.SPECIAL -> {
                SpecialController(onStartAnimation)
            }
            else -> {
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onStartWearableActivityClick() },
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = "OPEN")
        }
    }
}

@Composable
private fun SpecialController(onStartAnimation: (number: Int) -> Unit) {
    Column {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Animations"
        )
        Button(
            onClick = { onStartAnimation(0) },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 1")
        }
        Button(
            onClick = { onStartAnimation(1) },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 2")
        }
        Button(
            onClick = { onStartAnimation(2) },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 3")
        }
    }
}

@Composable
private fun ManualController(onPositionChange: (x: Float, y: Float, focus: Float) -> Unit) {
    var goalPosX by remember {
        mutableStateOf(0f)
    }
    var goalPosY by remember {
        mutableStateOf(0f)
    }
    var posX = 0f
    var posY = 0f
    var focusValue by remember { mutableStateOf(0.2f) }
    Box(
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxWidth()
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    goalPosX += dragAmount.x
                    goalPosY += dragAmount.y
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize(),
            onDraw = {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val radius = canvasWidth / 2 - 50.dp.value
                val centerX = canvasWidth / 2
                val centerY = canvasWidth / 2

                val goalRadius = 120.dp.value

                val (newGoalPosX, newGoalPosY) = getNewGoalPos(
                    PointF(centerX, centerY),
                    PointF(goalPosX, goalPosY),
                    radius,
                    goalRadius
                )
                goalPosX = newGoalPosX - centerX
                goalPosY = newGoalPosY - centerY

                posX = goalPosX / (radius - goalRadius)
                posY = goalPosY / (radius - goalRadius)
                Log.e("APP", "X: $posX Y: $posY Focus: $focusValue")
                onPositionChange(posX, posY, focusValue)

                drawCircle(
                    center = Offset(x = centerX, y = centerY),
                    color = Color(0xFFB53F3F),
                    radius = (radius + 10.dp.value)
                )
                drawCircle(
                    center = Offset(x = centerX, y = centerY),
                    color = Color(0xFFFFE7E7),
                    radius = (radius)
                )
                drawCircle(
                    center = Offset(x = newGoalPosX, y = newGoalPosY),
                    color = Color(0xFFB53F3F),
                    radius = (goalRadius)
                )
            })
    }
    Slider(value = focusValue, onValueChange = {
        focusValue = it
        onPositionChange(posX, posY, focusValue) })
}

private fun getNewGoalPos(
    center: PointF,
    offset: PointF,
    radius: Float,
    goalRadius: Float
): Pair<Float, Float> {
    val radius = radius - goalRadius
    val goalX = center.x + offset.x
    val goalY = center.y + offset.y
    val angle = angleBetween2Lines(
        center.x, center.y, center.x + 10f, center.y,
        center.x, center.y, goalX, goalY
    )
    val length = length(center.x, center.y, goalX, goalY)

    if (length <= radius) {
        return Pair(goalX, goalY)
    } else {
        return Pair(
            (center.x + radius * Math.cos(angle)).toFloat(),
            (center.y - radius * Math.sin(angle)).toFloat()
        )
    }
}

private fun angleBetween2Lines(
    line1X1: Float, line1Y1: Float, line1X2: Float, line1Y2: Float,
    line2X1: Float, line2Y1: Float, line2X2: Float, line2Y2: Float
): Double {
    val angle1 = Math.atan2(
        line1Y1.toDouble() - line1Y2.toDouble(),
        line1X1.toDouble() - line1X2.toDouble()
    )
    val angle2 = Math.atan2(
        line2Y1.toDouble() - line2Y2.toDouble(),
        line2X1.toDouble() - line2X2.toDouble()
    )
    return angle1 - angle2
}

private fun length(x1: Float, y1: Float, x2: Float, y2: Float): Double {
    val x = (x1 - x2).toDouble()
    val y = (y1 - y2).toDouble()
    return Math.sqrt(x * x + y * y)
}

@Preview
@Composable
fun MainAppPreview() {
    MainApp(
        onStateClick = {},
        onStartWearableActivityClick = {},
        { x, y, focus -> },
        {}
    )
}
