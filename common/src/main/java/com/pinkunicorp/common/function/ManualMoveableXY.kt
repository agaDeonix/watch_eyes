package com.pinkunicorp.common.function

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import kotlin.random.Random
import kotlinx.coroutines.*

class ManualMoveableXY(
    offsetX: Animatable<Float, AnimationVector1D>,
    offsetY: Animatable<Float, AnimationVector1D>,
    private val duration: Long,
    private val targetX: Float,
    private val targetY: Float
) : MoveableXY(offsetX, offsetY) {

    override suspend fun makeMove(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            val a = async {
                offsetX.animateTo(
                    targetX,
                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                )
            }
            val b = async {
                offsetY.animateTo(
                    targetY,
                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
                )
            }
            awaitAll(a, b)
        }
        delay(duration)
    }

}
