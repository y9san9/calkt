package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext

public inline fun ParseContext.takeWhile(block: (Char) -> Boolean): String {
    val string = StringBuilder()
    while (true) {
        if (position == this.string.length) return string.toString()
        val char = this.string[position]
        if (!block(char)) return string.toString()
        string.append(char)
        position++
    }
}
