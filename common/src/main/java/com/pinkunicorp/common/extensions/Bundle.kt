package com.pinkunicorp.common.extensions

import android.os.Bundle

fun Bundle.toMap(): Map<String, Any?> {
    val map: MutableMap<String, Any?> = HashMap()

    val ks: Set<String> = keySet()
    val iterator = ks.iterator()
    while (iterator.hasNext()) {
        val key = iterator.next()
        map[key] = getString(key)
    }

    return map
}
