package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause

public fun ParseContext.tryConsume(vararg strings: String): Boolean {
    for (string in strings) {
        if (startsWith(string)) {
            drop(string.length)
            return true
        }
    }
    return false
}

public inline fun ParseContext.consume(
    vararg strings: String,
    cause: () -> FailureCause
) {
    if (!tryConsume(*strings)) fail(cause())
}
