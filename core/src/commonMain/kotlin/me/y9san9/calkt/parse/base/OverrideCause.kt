package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause
import me.y9san9.calkt.parse.tryParse

public inline fun <T> ParseContext.overrideCause(
    cause: (ParseResult.Failure) -> FailureCause,
    block: () -> T
): T {
    return when (val result = tryParse(block)) {
        is ParseResult.Failure -> fail(cause(result))
        is ParseResult.Success -> result.value
    }
}
