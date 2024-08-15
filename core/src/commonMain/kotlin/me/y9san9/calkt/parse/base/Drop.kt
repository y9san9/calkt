package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext

public inline fun ParseContext.dropWhile(block: (Char) -> Boolean) {
    while (true) {
        if (position == string.length) return
        val char = string[position]
        if (!block(char)) break
        position++
    }
}

public fun ParseContext.dropFirst() {
    drop(n = 1)
}

public fun ParseContext.drop(n: Int) {
    position = (position + n).coerceAtMost(string.length)
}
