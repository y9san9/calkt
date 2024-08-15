package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateFunction
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.units.UnitsExpression

public class UnitsCalculate(
    private val convert: UnitsConvertFunction
) : CalculateFunction {

    override fun invoke(
        context: CalculateContext
    ): CalculateResult.Success {
        val expression = context.expression as? UnitsExpression ?: context.unsupportedExpression()

        return when (expression) {
            is UnitsExpression.Conversion -> when (val result = context.recursive(expression.value)) {
                is MathCalculateSuccess -> UnitsCalculateSuccess(result.number, expression.key)
                is UnitsCalculateSuccess -> {
                    if (result.key == expression.key) return result

                    val converted = convert(context, result.number, result.key, expression.key) as? MathCalculateSuccess
                        ?: context.unsupportedExpression()

                    UnitsCalculateSuccess(converted.number, expression.key)
                }
                else -> context.unsupportedExpression()
            }
        }
    }
}
