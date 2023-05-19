package com.pinkunicorp.common.mode

import com.pinkunicorp.common.function.animation.BaseAnimation

class AnimationsMode(val animations: List<BaseAnimation>): BaseMode() {
    override val name: String
        get() = "Animations"
}
