package me.y9san9.calkt.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.cause.FailureCause
import me.y9san9.calkt.parse.cause.MessageCause
import me.y9san9.calkt.parse.cause.MultipleCauses

public class ParseContext(
    public val string: String,
    public val parseFunction: ParseFunction
) {
    public val nonTerminalCauses: MutableList<FailureCause> = mutableListOf()
    public fun pushNonTerminalCause(cause: FailureCause) {
        nonTerminalCauses += cause
    }
    public fun clearNonTerminalCauses() {
        nonTerminalCauses.clear()
    }

    public fun failWithNonTerminalCauses(): Nothing {
        if (nonTerminalCauses.isEmpty()) error("Can't fail because there is no non-terminal causes")
        fail(MultipleCauses.of(causes = nonTerminalCauses.toTypedArray()))
    }

    public fun recursive(): Expression {
        return parseFunction(context)
    }

    public var position: Int = 0
    public val context: ParseContext get() = this

    public fun fail(message: String): Nothing = fail(MessageCause.of(message))
    public fun fail(cause: FailureCause): Nothing = throw FailureException(cause)

    public class FailureException(
        public val failureCause: FailureCause
    ) : Throwable()
}

public fun ParseContext.toParserState(): ParserState {
    return ParserState(string, position)
}
