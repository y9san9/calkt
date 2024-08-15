package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.cause.FailureCause

public val expectedIntegerCause: (ParseResult.Failure) -> ExpectedInputCause = { failure ->
    ExpectedInputCause.of("Integer", failure)
}

public fun ParseContext.nextIntegerString(
    allowLeadingZeros: Boolean = true,
    cause: (ParseResult.Failure) -> FailureCause = expectedIntegerCause
): String {
    overrideCause(cause) {
        val first = removeFirstChar { fail("Unexpected EOF") }
        if (!first.isDigit()) fail("Expected first char to be digit, but was '$first'")
        if (first == '0' && !allowLeadingZeros) return "0"

        return buildString {
            append(first)
            while (true) {
                val char = context.firstCharOrNull() ?: break
                if (!char.isDigit()) break
                append(char)
                context.dropFirst()
            }
        }
    }
}
