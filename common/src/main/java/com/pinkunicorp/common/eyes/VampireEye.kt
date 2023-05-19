package com.pinkunicorp.common.eyes

import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pinkunicorp.common.mode.AnimationsMode
import com.pinkunicorp.common.mode.MoveWithFocusMode

class VampireEye : CommonEye() {

    override fun getName() = "Vampire EYE"

    override fun getStoreId(): String {
        TODO("Not yet implemented")
    }

    override val modes = listOf(
        MoveWithFocusMode(),
//        AnimationsMode()
    )

    override fun getSupportedStates() = listOf(State.IDLE, State.MANUAL, State.SPECIAL)

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
        drawVampireEye(offsetX.value, offsetY.value, focus.value)
    }

    @Composable
    override fun drawPreview() {
        drawVampireEye(0f, 0f, 0.1f)
    }

    @Composable
    fun drawVampireEye(
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
                val eyeRadius = radius / 2f

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
                    color = Color.Red,
                    radius = eyeRadius - 10.dp.value
                )

                val width = eyeRadius.dp.toPx() * focus
                val height = eyeRadius * 1.2f
                drawOval(
                    color = Color.Black,
                    size = Size(width = width, height = height),
                    topLeft = Offset(x = eyePosX - width / 2, y = eyePosY - height / 2)
                )
//            drawCircle(
//                center = Offset(x = eyePosX, y = eyePosY),
//                color = Color.Black,
//                radius = eyeRadius * focus
//            )

                drawCircle(
                    center = Offset(x = centerX - 45, y = centerY - 45),
                    color = Color.LightGray,
                    radius = 30.dp.value,
                    alpha = 0.7f
                )
            })
    }
}
