package com.pinkunicorp.common.function

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope

open abstract class MoveableXY(val offsetX: Animatable<Float, AnimationVector1D>, val offsetY: Animatable<Float, AnimationVector1D>) {

//    lateinit var offsetX: Animatable<Float, AnimationVector1D>
//    lateinit var offsetY: Animatable<Float, AnimationVector1D>

//    @Composable
//    fun init() {
//        offsetX = remember {
//            Animatable(0f)
//        }
//        offsetY = remember {
//            Animatable(0f)
//        }
//    }

    abstract suspend fun makeMove(coroutineScope: CoroutineScope)

//    @Composable
//    open fun action() {
//        init()
//    }
}
