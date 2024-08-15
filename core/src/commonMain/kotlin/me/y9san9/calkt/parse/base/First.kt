package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause

public inline fun ParseContext.firstChar(
    cause: () -> FailureCause
): Char {
    return firstCharOrNull() ?: fail(cause())
}

public fun ParseContext.firstCharOrNull(): Char? {
    return string.getOrNull(position)
}
