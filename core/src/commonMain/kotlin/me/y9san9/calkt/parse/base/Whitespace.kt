package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext

public fun ParseContext.whitespace() {
    dropWhile { char -> char.isWhitespace() }
}
