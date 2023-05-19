package com.pinkunicorp.common.eyes

import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pinkunicorp.common.function.ManualMoveableXY
import com.pinkunicorp.common.function.RandomMoveableXY
import com.pinkunicorp.common.function.animation.CrossMoveAnimation
import com.pinkunicorp.common.function.animation.HorizontalMoveAnimation
import com.pinkunicorp.common.function.animation.VerticalMoveAnimation
import com.pinkunicorp.common.mode.MoveWithFocusMode

class BlackEye : CommonEye() {

    override fun getName() = "Black EYE"

    override fun getStoreId(): String {
        TODO("Not yet implemented")
    }

    override val modes = listOf(
        MoveWithFocusMode(),
//        Animations(
//            listOf(
//                CrossMoveAnimation()
//            )
//        )
    )

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

        LaunchedEffect(state) {
            val moveableXY = when (state.mode) {
                State.IDLE -> RandomMoveableXY(offsetX, offsetY, 1000L)
                State.INSANE -> RandomMoveableXY(offsetX, offsetY, 100L)
                State.MANUAL -> {
                    val targetX = (state.data?.get("targetX") as? Float) ?: 0f
                    val targetY = (state.data?.get("targetY") as? Float) ?: 0f
                    ManualMoveableXY(offsetX, offsetY, 500L, targetX, targetY)
                }
                State.SPECIAL -> {
                    when ((state.data?.get("animation") as? Int) ?: 0) {
                        0 -> HorizontalMoveAnimation(offsetX, offsetY)
                        1 -> VerticalMoveAnimation(offsetX, offsetY)
                        2 -> CrossMoveAnimation(offsetX, offsetY)
                        else -> HorizontalMoveAnimation(offsetX, offsetY)
                    }
                }
            }
//            moveableXY.makeMove(this)
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
