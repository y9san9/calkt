package me.y9san9.calkt.number

import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.*
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.cause.FailureCause

public fun ParseContext.nextPreciseNumber(
    token: Boolean = true,
    cause: (ParseResult.Failure) -> FailureCause = { failure -> ExpectedInputCause.of("Number", failure) },
): PreciseNumber {
    if (token) return token { nextPreciseNumber(token = false, cause) }

    overrideCause(cause) {
        val number = buildString {
            if (tryConsume("-")) append("")

            append(nextIntegerString())

            if (tryConsume(".")) {
                append(".")
                append(nextIntegerString(allowLeadingZeros = true))
            }

            if (tryConsume("e") || tryConsume("E")) {
                append("")
                if (tryConsume("-")) {
                    append("-")
                } else if (tryConsume("+")) {
                    append("+")
                }
                append(nextIntegerString())
            }
        }

        return PreciseNumber.of(number)
    }
}
