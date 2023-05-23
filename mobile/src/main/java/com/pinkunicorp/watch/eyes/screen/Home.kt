package com.pinkunicorp.watch.eyes.screen

import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.pinkunicorp.common.eyes.BlackEye
import com.pinkunicorp.common.eyes.CommonEye

/**
 * The UI affording the actions the user can take, along with a list of the events and the image
 * to be sent to the wearable devices.
 */
@Composable
fun Home(
    onStateChanged: (state: CommonEye.EyeState) -> Unit,
    onStartWearableActivityClick: () -> Unit,
    onShowLibraryClick: () -> Unit,
    currentEye: CommonEye
) {
    var currentState by remember {
        mutableStateOf(currentEye.getSupportedStates().first())
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            currentEye.getSupportedStates().forEach {
                Button(
                    onClick = {
                        currentState = it
                        onStateChanged(CommonEye.EyeState(mode = it))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (currentState == it) Color.Blue else Color.Gray),
                    modifier = Modifier.padding(15.dp)
                ) {
                    StateName(it)
                }
            }
        }
        when (currentState) {
            CommonEye.State.MANUAL -> {
                ManualController(onStateChanged)
            }
            CommonEye.State.SPECIAL -> {
                SpecialController(onStateChanged)
            }
            else -> {
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = { onStartWearableActivityClick() },
                modifier = Modifier
                    .padding(15.dp)
                    .size(75.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Text(
                    text = "OPEN"
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp)
            ) {
                Button(
                    onClick = { onShowLibraryClick() },
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(20.dp)
                        .align(Alignment.CenterEnd),
                ) {
                    Text(
                        text = "other"
                    )
                }
            }
        }
    }
}

@Composable
fun StateName(state: CommonEye.State) {
    Text(
        text = when (state) {
            CommonEye.State.IDLE -> "Idl"
            CommonEye.State.INSANE -> "Insn"
            CommonEye.State.MANUAL -> "Mnl"
            CommonEye.State.SPECIAL -> "Spcl"
        }
    )
}

@Composable
private fun SpecialController(onStartAnimation: (state: CommonEye.EyeState) -> Unit) {
    Column {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Animations"
        )
        Button(
            onClick = {
                onStartAnimation(
                    CommonEye.EyeState(
                        mode = CommonEye.State.SPECIAL,
                        data = mapOf("animation" to 0)
                    )
                )
            },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 1")
        }
        Button(
            onClick = { onStartAnimation(
                CommonEye.EyeState(
                    mode = CommonEye.State.SPECIAL,
                    data = mapOf("animation" to 1)
                )
            ) },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 2")
        }
        Button(
            onClick = { onStartAnimation(
                CommonEye.EyeState(
                    mode = CommonEye.State.SPECIAL,
                    data = mapOf("animation" to 2)
                )
            ) },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        ) {
            Text(text = "Animation 3")
        }
    }
}

@Composable
private fun ManualController(onPositionChange: (state: CommonEye.EyeState) -> Unit) {
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
                onPositionChange(
                    CommonEye.EyeState(
                        mode = CommonEye.State.MANUAL,
                        data = mapOf("targetX" to posX, "targetY" to posY)
                    )
                )

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
//        onPositionChange(posX, posY, focusValue)//FIXME need rework
    })
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
fun HomePreview() {
    Home(
        onStateChanged = {},
        onStartWearableActivityClick = {},
        {},
        BlackEye()
    )
}
