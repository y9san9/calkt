package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause

public inline fun <R> ParseContext.token(block: () -> R): R {
    whitespace()
    return try {
        block()
    } finally {
        whitespace()
    }
}

public fun ParseContext.token(
    string: String,
    vararg strings: String,
    cause: () -> FailureCause,
) {
    token { consume(string, *strings, cause = cause) }
}
