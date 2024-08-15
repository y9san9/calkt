package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateFunction
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.MathExpression

public class MathCalculate(
    private val calculateInfixOperator: MathCalculateInfixOperatorFunction
) : CalculateFunction {
    override fun invoke(context: CalculateContext): CalculateResult.Success {
        val expression = context.expression as? MathExpression ?: context.unsupportedExpression()

        return when (expression) {
            is MathExpression.Infix -> {
                val left = context.recursive(expression.left)
                val right = context.recursive(expression.right)
                return calculateInfixOperator(context, left, right, expression.key)
            }
            is MathExpression.Number -> {
                MathCalculateSuccess(expression.number)
            }
        }
    }
}
