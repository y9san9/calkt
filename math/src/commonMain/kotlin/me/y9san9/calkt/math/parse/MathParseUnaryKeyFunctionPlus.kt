package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.UnaryKey
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.parseFirstOf

private class CombinedMathParseUnaryKeyFunction(
    val functions: List<MathParseUnaryKeyFunction>
) : MathParseUnaryKeyFunction {
    override fun invoke(context: ParseContext): UnaryKey {
        return context.parseFirstOf(
            blocks = functions.map { function ->
                { function.invoke(context) }
            }
        )
    }
}

public operator fun MathParseUnaryKeyFunction.plus(
    other: MathParseUnaryKeyFunction
): MathParseUnaryKeyFunction {
    val functions = when {
        this is CombinedMathParseUnaryKeyFunction &&
            other is CombinedMathParseUnaryKeyFunction -> this.functions + other.functions
        this is CombinedMathParseUnaryKeyFunction -> this.functions + other
        other is CombinedMathParseUnaryKeyFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedMathParseUnaryKeyFunction(functions)
}
