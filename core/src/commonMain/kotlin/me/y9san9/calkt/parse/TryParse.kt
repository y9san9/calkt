package me.y9san9.calkt.parse

import me.y9san9.calkt.Expression

public fun tryParse(
    string: String,
    failOnRemaining: Boolean = true,
    function: ParseFunction
): ParseResult<Expression> {
    return tryParse(string, failOnRemaining, function, function)
}

public fun tryParse(
    string: String,
    failOnRemaining: Boolean = true,
    function: ParseFunction,
    recursive: ParseFunction
): ParseResult<Expression> {
    val context = ParseContext(string, recursive)
    return try {
        val result = function(context)
        if (failOnRemaining && context.position != string.length) {
            context.failWithNonTerminalCauses()
        }
        ParseResult.Success(context.toParserState(), result)
    } catch (failure: ParseContext.FailureException) {
        ParseResult.Failure(context.toParserState(), failure.failureCause)
    }
}

public inline fun <T> ParseContext.tryParse(block: () -> T): ParseResult<T> {
    val positionBackup = position
    var exception = false

    return try {
        val result = block()
        ParseResult.Success(context.toParserState(), result)
    } catch (failure: ParseContext.FailureException) {
        exception = true
        ParseResult.Failure(context.toParserState(), failure.failureCause)
    } finally {
        if (exception) {
            this.position = positionBackup
        }
    }
}
