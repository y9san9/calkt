package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.getOrFail
import me.y9san9.calkt.math.UnaryKey
import me.y9san9.calkt.math.calculate.MathCalculateFailure.UnsupportedUnaryOperator

private class CombinedMathCalculateUnaryOperatorFunction(
    val functions: List<MathCalculateUnaryOperatorFunction>
) : MathCalculateUnaryOperatorFunction {
    override fun invoke(
        context: CalculateContext,
        operand: CalculateResult.Success,
        key: UnaryKey
    ): CalculateResult.Success {
        for (function in functions) {
            val result = context.tryCalculate { function(context, operand, key) }
            if (result is UnsupportedUnaryOperator) continue
            return result.getOrFail(context)
        }
        context.fail(UnsupportedUnaryOperator)
    }
}

public operator fun MathCalculateUnaryOperatorFunction.plus(
    other: MathCalculateUnaryOperatorFunction
): MathCalculateUnaryOperatorFunction {
    val functions = when {
        this is CombinedMathCalculateUnaryOperatorFunction &&
            other is CombinedMathCalculateUnaryOperatorFunction -> this.functions + other.functions
        this is CombinedMathCalculateUnaryOperatorFunction -> this.functions + other
        other is CombinedMathCalculateUnaryOperatorFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedMathCalculateUnaryOperatorFunction(functions)
}
