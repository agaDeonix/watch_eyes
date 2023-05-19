package com.pinkunicorp.common.eyes

import android.graphics.PointF
import androidx.compose.runtime.Composable
import com.pinkunicorp.common.mode.BaseMode

abstract class CommonEye {

    enum class State {
        IDLE,
        INSANE,
        MANUAL,
        SPECIAL
    }

    data class EyeState(
        val mode: State = State.IDLE,
        val data: Map<String, Any?>? = emptyMap()
    )

    var state: EyeState = EyeState()

    private var _isPayed: Boolean = false

    abstract fun getSupportedStates(): List<State>

    abstract fun getName(): String

    abstract fun getStoreId(): String

    abstract val modes: List<BaseMode>

    fun isPayed() = _isPayed

    fun getCountAnimations(): Int {
        return 0
    }

    @Composable
    abstract fun draw()

    @Composable
    abstract fun drawPreview()

    protected fun getNewGoalPos(
        center: PointF,
        offset: PointF,
        radius: Float,
        goalRadius: Float
    ): Pair<Float, Float> {
        val radius = radius - goalRadius
        val goalX = center.x + offset.x
        val goalY = center.y + offset.y
        val angle = angleBetween2Lines(
            center.x, center.y, center.x + 10f, center.y,
            center.x, center.y, goalX, goalY
        )
        val length = length(center.x, center.y, goalX, goalY)

        if (length <= radius) {
            return Pair(goalX, goalY)
        } else {
            return Pair(
                (center.x + radius * Math.cos(angle)).toFloat(),
                (center.y - radius * Math.sin(angle)).toFloat()
            )
        }
    }

    protected fun angleBetween2Lines(
        line1X1: Float, line1Y1: Float, line1X2: Float, line1Y2: Float,
        line2X1: Float, line2Y1: Float, line2X2: Float, line2Y2: Float
    ): Double {
        val angle1 = Math.atan2(
            line1Y1.toDouble() - line1Y2.toDouble(),
            line1X1.toDouble() - line1X2.toDouble()
        )
        val angle2 = Math.atan2(
            line2Y1.toDouble() - line2Y2.toDouble(),
            line2X1.toDouble() - line2X2.toDouble()
        )
        return angle1 - angle2
    }

    protected fun length(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        val x = (x1 - x2).toDouble()
        val y = (y1 - y2).toDouble()
        return Math.sqrt(x * x + y * y)
    }
}
