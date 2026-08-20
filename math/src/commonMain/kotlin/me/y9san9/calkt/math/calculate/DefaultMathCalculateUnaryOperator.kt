package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.DefaultUnaryKeys
import me.y9san9.calkt.math.UnaryKey

public object DefaultMathCalculateUnaryOperator : MathCalculateUnaryOperatorFunction {
    override fun invoke(
        context: CalculateContext,
        operand: CalculateResult.Success,
        key: UnaryKey
    ): CalculateResult.Success {
        if (operand !is MathCalculateSuccess) context.unsupportedUnaryOperator()
        if (key !is DefaultUnaryKeys) context.unsupportedUnaryOperator()

        val number = when (key) {
            DefaultUnaryKeys.Plus -> +operand.number
            DefaultUnaryKeys.Minus -> -operand.number
        }
        return MathCalculateSuccess(number)
    }
}
