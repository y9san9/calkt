package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.DefaultUnaryKeys
import me.y9san9.calkt.math.UnaryKey
import me.y9san9.calkt.math.calculate.MathCalculateUnaryOperatorFunction
import me.y9san9.calkt.math.calculate.unsupportedUnaryOperator

public object UnitsMathCalculateUnaryOperator : MathCalculateUnaryOperatorFunction {
    override fun invoke(
        context: CalculateContext,
        operand: CalculateResult.Success,
        key: UnaryKey
    ): CalculateResult.Success {
        if (operand !is UnitsCalculateSuccess) context.unsupportedUnaryOperator()
        if (key !is DefaultUnaryKeys) context.unsupportedUnaryOperator()

        val number = when (key) {
            DefaultUnaryKeys.Plus -> +operand.number
            DefaultUnaryKeys.Minus -> -operand.number
        }
        return UnitsCalculateSuccess(number, operand.key)
    }
}
