package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.parseFirstOf

private class CombinedMathParseOperandFunction(
    val functions: List<MathParseOperandFunction>
) : MathParseOperandFunction {
    override fun invoke(
        context: ParseContext
    ): Expression {
        return context.parseFirstOf(
            blocks = functions.map { function ->
                { function.invoke(context) }
            }
        )
    }
}

public fun MathParseOperandFunction.plus(other: MathParseOperandFunction): MathParseOperandFunction {
    val functions = when {
        this is CombinedMathParseOperandFunction &&
            other is CombinedMathParseOperandFunction -> this.functions + other.functions
        this is CombinedMathParseOperandFunction -> this.functions + other
        other is CombinedMathParseOperandFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedMathParseOperandFunction(functions)
}
