package com.pinkunicorp.common.function.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import com.pinkunicorp.common.function.MoveableXY
import kotlinx.coroutines.*

class CrossMoveAnimation(
    offsetX: Animatable<Float, AnimationVector1D>,
    offsetY: Animatable<Float, AnimationVector1D>
) : MoveableXY(offsetX, offsetY) {

    private var duration = 1000L

//    @Composable
//    override fun action() {
//        super.action()
//        LaunchedEffect(true) {
//            while (true) {
//                val a = async {
//                    offsetX.animateTo(
//                        1f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b = async {
//                    offsetY.animateTo(
//                        0f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                awaitAll(a, b)
//                delay(duration)
//                val a1 = async {
//                    offsetX.animateTo(
//                        0f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b1 = async {
//                    offsetY.animateTo(
//                        1f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//
//                awaitAll(a1, b1)
//                delay(duration)
//                val a2 = async {
//                    offsetX.animateTo(
//                        -1f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b2 = async {
//                    offsetY.animateTo(
//                        0f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                awaitAll(a2, b2)
//
//                delay(duration)
//                val a3 = async {
//                    offsetX.animateTo(
//                        0f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b3 = async {
//                    offsetY.animateTo(
//                        -1f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                awaitAll(a3, b3)
//
//                delay(duration)
//            }
//        }
//    }

    override suspend fun makeMove(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
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
//                                focus.animateTo(
//                                    (Random.nextInt(50) + 20) / 100f,
//                                    animationSpec = tween(
//                                        duration.toInt() + 500,
//                                        easing = EaseInOut
//                                    )
//                                )
            }
            awaitAll(a, b, c)
        }
        delay(duration)
        coroutineScope.launch {
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
//                                focus.animateTo(
//                                    (Random.nextInt(50) + 20) / 100f,
//                                    animationSpec = tween(
//                                        duration.toInt() + 500,
//                                        easing = EaseInOut
//                                    )
//                                )
            }
            awaitAll(a, b, c)
        }
        delay(duration)
        coroutineScope.launch {
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
//                                focus.animateTo(
//                                    (Random.nextInt(50) + 20) / 100f,
//                                    animationSpec = tween(
//                                        duration.toInt() + 500,
//                                        easing = EaseInOut
//                                    )
//                                )
            }
            awaitAll(a, b, c)
        }
        delay(duration)
        coroutineScope.launch {
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
//                                focus.animateTo(
//                                    (Random.nextInt(50) + 20) / 100f,
//                                    animationSpec = tween(
//                                        duration.toInt() + 500,
//                                        easing = EaseInOut
//                                    )
//                                )
            }
            awaitAll(a, b, c)
        }
        delay(duration)
    }
}
