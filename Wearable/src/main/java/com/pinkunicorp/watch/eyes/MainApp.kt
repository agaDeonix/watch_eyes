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
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Typography
import kotlin.random.Random
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun MainApp(
    state: Int,
    manualPosition: Pair<Float, Float>
) {
    WearAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .selectableGroup(),
            verticalArrangement = Arrangement.Center
        ) {
            Circle(state, manualPosition)
        }
    }
}

@Composable
fun WearAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = wearColorPalette,
        typography = Typography,
        // For shapes, we generally recommend using the default Material Wear shapes which are
        // optimized for round and non-round devices.
        content = content
    )
}

@Composable
fun Circle(state: Int, manualPosition: Pair<Float, Float>) {
    val offsetX = remember {
        Animatable(0f)
    }
    val offsetY = remember {
        Animatable(0f)
    }
    val focus = remember {
        Animatable(0.2f)
    }
//    Log.e("WEAR", "Input X:${manualPosition.first} Y:${manualPosition.second}")
    LaunchedEffect(state, manualPosition) {
        val duration = when (state) {
            0 -> 1000L
            1 -> 100L
            2 -> 100L
            else -> 1000L
        }
        val x = manualPosition.first
        val y = manualPosition.second
        while (state != 2) {
            launch {
                val a = async {
                    offsetX.animateTo(
                        (Random.nextInt(180) - 90) / 100f,
                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
                    )
                }
                val b = async {
                    offsetY.animateTo(
                        (Random.nextInt(180) - 90) / 100f,
                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
                    )
                }
                val c = async {
                    focus.animateTo(
                        (Random.nextInt(50) + 20) / 100f,
                        animationSpec = tween(duration.toInt() + 500, easing = EaseInOut)
                    )
                }
                awaitAll(a, b, c)
            }
            delay(duration)
        }
        if (state == 2) {
//            Log.e("WEAR", "Launch X:$x Y:$y")
            launch {
                val a = async { offsetX.animateTo(x, animationSpec = tween(duration.toInt(), easing = EaseInOut)) }
                val b = async { offsetY.animateTo(y, animationSpec = tween(duration.toInt(), easing = EaseInOut)) }
                awaitAll(a, b)
            }
        }
    }
//        val image = ImageBitmap.imageResource(id = R.mipmap.sphere)
    Canvas(
        modifier = Modifier
            .fillMaxSize()
//                .background(Color.White)
        ,
        onDraw = {
//            Log.e("WEAR", "X:${offsetX.value} Y:${offsetY.value}")
            val canvasWidth = size.width
            val radius = canvasWidth / 2 - 60.dp.value
            val centerX = canvasWidth / 2
            val centerY = canvasWidth / 2
            val eyeRadius = radius / 2.5f

            val (eyePosX, eyePosY) = getNewGoalPos(
                PointF(centerX, centerY),
                PointF(offsetX.value * (radius - eyeRadius), offsetY.value * (radius - eyeRadius)),
                radius,
                eyeRadius
            )

            drawCircle(
                center = Offset(x = centerX, y = centerY),
                color = Color(0xFFB53F3F),
                radius = radius + 10.dp.value
            )
            drawCircle(
                center = Offset(x = centerX, y = centerY),
                color = Color(0xFFFFE7E7),
                radius = radius
            )
            drawCircle(
                center = Offset(x = eyePosX, y = eyePosY),
                color = Color(0xFFC77878),
                radius = eyeRadius
            )
            drawCircle(
                center = Offset(x = eyePosX, y = eyePosY),
                color = Color(0xFFEE9E9E),
                radius = eyeRadius - 10.dp.value
            )
            drawCircle(
                center = Offset(x = eyePosX, y = eyePosY),
                color = Color(0xFF962929),
                radius = eyeRadius * focus.value
            )

            drawCircle(
                center = Offset(x = centerX - 45, y = centerY - 45),
                color = androidx.compose.ui.graphics.Color.White,
                radius = 30.dp.value,
                alpha = 0.7f
            )
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

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .selectableGroup(),
            verticalArrangement = Arrangement.Center
        ) {
            Circle(0, Pair(0f, 0f))
        }
    }
}

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)
val Orange = Color(0xFFFF7B24)

internal val wearColorPalette: Colors = Colors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    secondaryVariant = Teal200,
    error = Red400,
    onPrimary = androidx.compose.ui.graphics.Color.Black,
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    onError = androidx.compose.ui.graphics.Color.Black
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
