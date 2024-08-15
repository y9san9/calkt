package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.getOrFail
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.math.calculate.MathCalculateFailure.UnsupportedInfixOperator

private class CombinedMathCalculateInfixOperatorFunction(
    val functions: List<MathCalculateInfixOperatorFunction>
) : MathCalculateInfixOperatorFunction {

    override fun invoke(
        context: CalculateContext,
        left: CalculateResult.Success,
        right: CalculateResult.Success,
        key: InfixKey
    ): CalculateResult.Success {
        for (function in functions) {
            val result = context.tryCalculate { function(context, left, right, key) }
            if (result is UnsupportedInfixOperator) continue
            return result.getOrFail(context)
        }
        context.fail(UnsupportedInfixOperator)
    }
}

public operator fun MathCalculateInfixOperatorFunction.plus(
    other: MathCalculateInfixOperatorFunction
): MathCalculateInfixOperatorFunction {
    val functions = when {
        this is CombinedMathCalculateInfixOperatorFunction &&
            other is CombinedMathCalculateInfixOperatorFunction -> this.functions + other.functions
        this is CombinedMathCalculateInfixOperatorFunction -> this.functions + other
        other is CombinedMathCalculateInfixOperatorFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedMathCalculateInfixOperatorFunction(functions)
}
