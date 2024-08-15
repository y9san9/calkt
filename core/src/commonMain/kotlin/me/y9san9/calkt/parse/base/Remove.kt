package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause

public inline fun ParseContext.removeFirstChar(cause: () -> FailureCause): Char {
    return removeFirstCharOrNull() ?: fail(cause())
}

public fun ParseContext.removeFirstCharOrNull(): Char? {
    return try {
        firstCharOrNull()
    } finally {
        dropFirst()
    }
}
