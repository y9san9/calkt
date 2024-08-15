package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult

public fun CalculateContext.calculateMathExpression(
    calculateInfixOperator: MathCalculateInfixOperatorFunction = DefaultMathCalculateInfixOperator
): CalculateResult.Success {
    val calculate = MathCalculate(calculateInfixOperator)
    return calculate(context)
}
