package com.pinkunicorp.common.function

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import kotlinx.coroutines.*

class ManualMoveableXY(
    offsetX: Animatable<Float, AnimationVector1D>,
    offsetY: Animatable<Float, AnimationVector1D>,
    private val duration: Long,
    val targetX: Float,
    val targetY: Float
) : MoveableXY(offsetX, offsetY) {

//    @Composable
//    override fun action() {
//        super.action()
//        val x = manualPosition.first
//        val y = manualPosition.second
//        val manualFocus = manualPosition.third
//        LaunchedEffect(true) {
//            while (true) {
//                val a = async {
//                    offsetX.animateTo(
//                        x,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val b = async {
//                    offsetY.animateTo(
//                        y,
//                        animationSpec = tween(duration.toInt(), easing = EaseInOut)
//                    )
//                }
//                val c = async {
////                focus.animateTo(
////                    manualFocus,
////                    animationSpec = tween(duration.toInt(), easing = EaseInOut)
////                )
//                }
//                awaitAll(a, b, c)
//                delay(duration)
//            }
//        }
//    }

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
