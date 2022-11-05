package com.pinkunicorp.common

import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pinkunicorp.common.function.ManualMoveableXY
import com.pinkunicorp.common.function.MoveableXY
import com.pinkunicorp.common.function.RandomMoveableXY
import com.pinkunicorp.common.function.animation.CrossMoveAnimation
import com.pinkunicorp.common.function.animation.HorizontalMoveAnimation
import com.pinkunicorp.common.function.animation.VerticalMoveAnimation
import kotlinx.coroutines.delay

class BlackEye : CommonEye() {

    override fun getName() = "Black EYE"

    override fun getStoreId(): String {
        TODO("Not yet implemented")
    }

    override fun getSupportedStates() =
        listOf(State.IDLE, State.INSANE, State.MANUAL, State.SPECIAL)

    @Composable
    override fun draw() {
        val focus = remember {
            Animatable(0.2f)
        }
        val offsetX = remember {
            Animatable(0f)
        }
        val offsetY = remember {
            Animatable(0f)
        }
        LaunchedEffect(state, manualPosition, specAnimation) {
            val moveableXY = when (state) {
                0 -> RandomMoveableXY(offsetX, offsetY, 1000L)
                1 -> RandomMoveableXY(offsetX, offsetY, 100L)
//                2 -> ManualMoveableXY(offsetX, offsetY, 100L, manualPosition.first, manualPosition.second)
                2 -> RandomMoveableXY(offsetX, offsetY, 1000L)
                3 -> when (specAnimation) {
                    0 -> HorizontalMoveAnimation(offsetX, offsetY)
                    1 -> VerticalMoveAnimation(offsetX, offsetY)
                    2 -> CrossMoveAnimation(offsetX, offsetY)
                    else -> HorizontalMoveAnimation(offsetX, offsetY)
                }
                else -> null
            }
//            moveableXY?.makeMove(this)
            while (true) {
                moveableXY?.makeMove(this)
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
