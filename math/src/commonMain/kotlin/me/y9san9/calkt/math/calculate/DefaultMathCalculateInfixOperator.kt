package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.CalculateResult.DivisionByZero
import me.y9san9.calkt.math.DefaultInfixKeys
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.math.calculate.MathCalculateFailure.UnsupportedInfixOperator

public object DefaultMathCalculateInfixOperator : MathCalculateInfixOperatorFunction {
    override fun invoke(
        context: CalculateContext,
        left: CalculateResult.Success,
        right: CalculateResult.Success,
        key: InfixKey
    ): CalculateResult.Success {
        if (left !is MathCalculateSuccess) context.fail(UnsupportedInfixOperator)
        if (right !is MathCalculateSuccess) context.fail(UnsupportedInfixOperator)
        if (key !is DefaultInfixKeys) context.fail(UnsupportedInfixOperator)

        val number = when (key) {
            DefaultInfixKeys.Plus -> left.number + right.number
            DefaultInfixKeys.Minus -> left.number - right.number
            DefaultInfixKeys.Times -> left.number * right.number
            DefaultInfixKeys.Div -> {
                if (right.number.isZero()) {
                    context.fail(DivisionByZero)
                } else {
                    left.number.divide(right.number, context.precision)
                }
            }
        }

        return MathCalculateSuccess(number)
    }
}
