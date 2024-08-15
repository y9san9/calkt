package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.parseFirstOf

private class CombinedMathParseInfixKeyFunction(
    val functions: List<MathParseInfixKeyFunction>
) : MathParseInfixKeyFunction {
    override fun invoke(
        context: ParseContext
    ): InfixKey {
        return context.parseFirstOf(
            blocks = functions.map { function ->
                { function.invoke(context) }
            }
        )
    }
}

public operator fun MathParseInfixKeyFunction.plus(other: MathParseInfixKeyFunction): MathParseInfixKeyFunction {
    val functions = when {
        this is CombinedMathParseInfixKeyFunction &&
            other is CombinedMathParseInfixKeyFunction -> this.functions + other.functions
        this is CombinedMathParseInfixKeyFunction -> this.functions + other
        other is CombinedMathParseInfixKeyFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedMathParseInfixKeyFunction(functions)
}
