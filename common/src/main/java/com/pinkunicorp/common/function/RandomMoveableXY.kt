package com.pinkunicorp.common.function

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import kotlin.random.Random
import kotlinx.coroutines.*

class RandomMoveableXY(
    offsetX: Animatable<Float, AnimationVector1D>,
    offsetY: Animatable<Float, AnimationVector1D>,
    private val duration: Long
) : MoveableXY(offsetX, offsetY) {

//    @Composable
//    override fun action() {
//        super.action()
//        LaunchedEffect(true) {
//            while (true) {
//                val a = async {
//                    offsetX.animateTo(
//                        (Random.nextInt(180) - 90) / 100f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b = async {
//                    offsetY.animateTo(
//                        (Random.nextInt(180) - 90) / 100f,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                awaitAll(a, b)
//                delay(duration)
//            }
//        }
//    }

    override suspend fun makeMove(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
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
            awaitAll(a, b)
        }
        delay(duration)
    }

}
