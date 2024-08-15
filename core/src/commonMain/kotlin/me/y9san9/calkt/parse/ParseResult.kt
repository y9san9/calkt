package me.y9san9.calkt.parse

import me.y9san9.calkt.internal.withIndent
import me.y9san9.calkt.parse.cause.FailureCause
import me.y9san9.calkt.parse.cause.MultipleCauses
import me.y9san9.calkt.parse.cause.toConsoleOutput

public sealed interface ParseResult<out T> {
    public data class Failure(
        val state: ParserState,
        val cause: FailureCause
    ) : ParseResult<Nothing> {

        public fun toConsoleOutput(
            failureMessage: (FailureCause) -> String = FailureCause::toString
        ): String {
            return buildString {
                appendLine("ParseResult.Failure {")
                withIndent {
                    appendLine("location: ")
                    withIndent {
                        append(state.toConsolePointer())
                    }
                    appendLine()
                    appendLine("cause: ")
                    withIndent {
                        append(cause.toConsoleOutput(failureMessage))
                    }
                }

                appendLine()
                append("}")
            }
        }
    }

    public data class Success<out T>(
        val state: ParserState,
        val value: T,
    ) : ParseResult<T>
}

public fun <T> ParseResult<T>.getOrFail(context: ParseContext): T {
    return when (this) {
        is ParseResult.Failure -> context.fail(cause)
        is ParseResult.Success -> value
    }
}

public inline fun <T> ParseResult<T>.getOrElse(
    context: ParseContext,
    recover: (ParseResult.Failure) -> T
): T {
    return when (this) {
        is ParseResult.Success -> value
        is ParseResult.Failure -> elseTry(context, recover).getOrFail(context)
    }
}

public inline fun <T> ParseResult<T>.elseTry(context: ParseContext, recover: (ParseResult.Failure) -> T): ParseResult<T> {
    return when (this) {
        is ParseResult.Success -> this
        is ParseResult.Failure -> {
            when (val parsed = context.tryParse { recover(this) }) {
                is ParseResult.Failure -> ParseResult.Failure(
                    state = context.toParserState(),
                    cause = MultipleCauses.of(this.cause, parsed.cause)
                )
                is ParseResult.Success -> parsed
            }
        }
    }
}

public inline fun <T> ParseResult<T>.onSuccess(block: (T) -> Unit) {
    if (this is ParseResult.Success) block(value)
}
