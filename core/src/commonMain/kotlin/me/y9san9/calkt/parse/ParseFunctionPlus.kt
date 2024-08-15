package me.y9san9.calkt.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.base.parseFirstOf

private class CombinedParseFunction(val functions: List<ParseFunction>) : ParseFunction {
    override fun invoke(context: ParseContext): Expression {
        return context.parseFirstOf(
            blocks = functions.map { function ->
                { function(context) }
            }
        )
    }
}

private val ParseFunction.functions: List<ParseFunction> get() = when (this) {
    is CombinedParseFunction -> this.functions
    else -> listOf(this)
}

public operator fun ParseFunction.plus(other: ParseFunction): ParseFunction {
    return CombinedParseFunction(functions = this.functions + other.functions)
}
