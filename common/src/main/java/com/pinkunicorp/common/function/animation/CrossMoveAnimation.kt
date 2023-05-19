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
) : BaseAnimation() {

    private val moveable = object : MoveableXY(offsetX, offsetY) {
        private var duration = 1000L

        override suspend fun makeMove(coroutineScope: CoroutineScope) {
            while (true) {
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

    }
}
