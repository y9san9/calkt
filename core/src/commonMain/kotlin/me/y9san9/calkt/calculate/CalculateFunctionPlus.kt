package me.y9san9.calkt.calculate

import me.y9san9.calkt.calculate.CalculateResult.UnsupportedExpression

private class CombinedCalculateFunction(
    val functions: List<CalculateFunction>
) : CalculateFunction {
    override fun invoke(context: CalculateContext): CalculateResult.Success {
        for (function in functions) {
            val result = context.tryCalculate(function)
            if (result is UnsupportedExpression) continue
            return result.getOrFail(context)
        }
        context.unsupportedExpression()
    }
}

public operator fun CalculateFunction.plus(
    other: CalculateFunction
): CalculateFunction {
    val functions = when {
        this is CombinedCalculateFunction && other is CombinedCalculateFunction -> this.functions + other.functions
        this is CombinedCalculateFunction -> this.functions + other
        other is CombinedCalculateFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedCalculateFunction(functions)
}
