package com.pinkunicorp.common

import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BlackEye : CommonEye() {

    var state: Int = 0
    var manualPosition: Triple<Float, Float, Float> = Triple(0f, 0f, 0.1f)
    var specAnimation: Int? = null

    override fun getName() = "Black EYE"

    override fun getStoreId(): String {
        TODO("Not yet implemented")
    }

    override fun getSupportedStates() = listOf(State.IDLE, State.INSANE, State.MANUAL, State.SPECIAL)

    @Composable
    override fun draw() {
        val offsetX = remember {
            Animatable(0f)
        }
        val offsetY = remember {
            Animatable(0f)
        }
        val focus = remember {
            Animatable(0.2f)
        }
        LaunchedEffect(state, manualPosition, specAnimation) {
            val duration = when (state) {
                0 -> 1000L
                1 -> 100L
                2 -> 100L
                else -> 1000L
            }
            val x = manualPosition.first
            val y = manualPosition.second
            val manualFocus = manualPosition.third
            while (state == 0 || state == 1) {
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
                    val a = async {
                        offsetX.animateTo(
                            x,
                            animationSpec = tween(duration.toInt(), easing = EaseInOut)
                        )
                    }
                    val b = async {
                        offsetY.animateTo(
                            y,
                            animationSpec = tween(duration.toInt(), easing = EaseInOut)
                        )
                    }
                    val c = async {
                        focus.animateTo(
                            manualFocus,
                            animationSpec = tween(duration.toInt(), easing = EaseInOut)
                        )
                    }
                    awaitAll(a, b, c)
                }
            }
            while (state == 3) {
                when (specAnimation) {
                    0 -> {
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    -1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                    }
                    1 -> {
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    -1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                    }
                    2 -> {
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    -1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                        launch {
                            val a = async {
                                offsetX.animateTo(
                                    0f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val b = async {
                                offsetY.animateTo(
                                    -1f,
                                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                                )
                            }
                            val c = async {
                                focus.animateTo(
                                    (Random.nextInt(50) + 20) / 100f,
                                    animationSpec = tween(
                                        duration.toInt() + 500,
                                        easing = EaseInOut
                                    )
                                )
                            }
                            awaitAll(a, b, c)
                        }
                        delay(duration)
                    }
                    else -> {
                        launch {
                            offsetX.animateTo(
                                0f,
                                animationSpec = tween(duration.toInt(), easing = EaseInOut)
                            )
                            offsetY.animateTo(
                                0f,
                                animationSpec = tween(duration.toInt(), easing = EaseInOut)
                            )
                            focus.animateTo(
                                0.2f,
                                animationSpec = tween(duration.toInt() + 500, easing = EaseInOut)
                            )
                        }
                        delay(duration)
                        break
                    }
                }
            }
        }
        drawBlackEye(offsetX.value, offsetY.value, focus.value)
    }

    @Composable
    override fun drawPreview() {
        drawBlackEye(0f, 0f, 0.3f)
    }

    @Composable
    fun drawBlackEye(
        offsetX: Float,
        offsetY: Float,
        focus: Float
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize(),
            onDraw = {
                val canvasWidth = size.width
                val radius = canvasWidth / 2 - 60.dp.value
                val centerX = canvasWidth / 2
                val centerY = canvasWidth / 2
                val eyeRadius = radius / 2.5f

                val (eyePosX, eyePosY) = getNewGoalPos(
                    PointF(centerX, centerY),
                    PointF(offsetX * (radius - eyeRadius), offsetY * (radius - eyeRadius)),
                    radius,
                    eyeRadius
                )

                drawCircle(
                    center = Offset(x = centerX, y = centerY),
                    color = Color.Black,
                    radius = radius + 10.dp.value
                )
                drawCircle(
                    center = Offset(x = centerX, y = centerY),
                    color = Color.White,
                    radius = radius
                )
                drawCircle(
                    center = Offset(x = eyePosX, y = eyePosY),
                    color = Color.Black,
                    radius = eyeRadius
                )
                drawCircle(
                    center = Offset(x = eyePosX, y = eyePosY),
                    color = Color.White,
                    radius = eyeRadius - 10.dp.value
                )
                drawCircle(
                    center = Offset(x = eyePosX, y = eyePosY),
                    color = Color.Black,
                    radius = eyeRadius * focus
                )

                drawCircle(
                    center = Offset(x = centerX - 45, y = centerY - 45),
                    color = Color.LightGray,
                    radius = 30.dp.value,
                    alpha = 0.7f
                )
            })
    }
}
