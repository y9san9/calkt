package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext

public fun ParseContext.startsWith(string: String): Boolean {
    for (i in string.indices) {
        val char = this.string.getOrNull(index = position + i) ?: return false
        if (char != string[i]) return false
    }
    return true
}
