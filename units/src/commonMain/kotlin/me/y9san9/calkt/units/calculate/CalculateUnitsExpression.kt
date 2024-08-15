package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.plus
import me.y9san9.calkt.math.calculate.DefaultMathCalculateInfixOperator
import me.y9san9.calkt.math.calculate.MathCalculate
import me.y9san9.calkt.math.calculate.MathCalculateInfixOperatorFunction
import me.y9san9.calkt.math.calculate.plus

private val defaultCalculateInfixOperator = DefaultMathCalculateInfixOperator + UnitsMathCalculateInfixOperator

public fun CalculateContext.calculateUnitsExpression(
    convert: UnitsConvertFunction,
    calculateInfixOperator: MathCalculateInfixOperatorFunction = defaultCalculateInfixOperator
): CalculateResult.Success {
    val function = MathCalculate(calculateInfixOperator) + UnitsCalculate(convert)
    return function(context)
}
