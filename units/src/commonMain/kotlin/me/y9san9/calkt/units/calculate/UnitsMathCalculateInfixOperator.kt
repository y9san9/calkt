package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.DefaultInfixKeys
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.math.calculate.MathCalculateInfixOperatorFunction
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.math.calculate.plus
import me.y9san9.calkt.math.calculate.unsupportedInfixOperator
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.UnitsExpression

public object UnitsMathCalculateInfixOperator : MathCalculateInfixOperatorFunction {
    private val delegate = Sum(
        supportedOperators = setOf(DefaultInfixKeys.Plus, DefaultInfixKeys.Minus)
    ) + Prod(
        supportedOperators = setOf(DefaultInfixKeys.Times, DefaultInfixKeys.Div)
    )

    override fun invoke(
        context: CalculateContext,
        left: CalculateResult.Success,
        right: CalculateResult.Success,
        key: InfixKey
    ): CalculateResult.Success {
        return delegate(context, left, right, key)
    }

    private class Sum(
        private val supportedOperators: Set<InfixKey>
    ) : MathCalculateInfixOperatorFunction {
        override fun invoke(
            context: CalculateContext,
            left: CalculateResult.Success,
            right: CalculateResult.Success,
            key: InfixKey
        ): CalculateResult.Success {
            if (key !in supportedOperators) context.unsupportedInfixOperator()

            val leftNumber = extractNumber(left) ?: context.unsupportedInfixOperator()
            val leftUnits = extractUnits(left)
            val rightNumber = extractNumber(right) ?: context.unsupportedInfixOperator()
            val rightUnits = extractUnits(right)

            // If none of operands have units, it's not a task for this function
            val commonUnits = leftUnits ?: rightUnits ?: context.unsupportedInfixOperator()

            val leftConverted = convertUnits(context, leftNumber, leftUnits, commonUnits)
            if (leftConverted !is UnitsCalculateSuccess) context.unsupportedInfixOperator()

            val rightConverted = convertUnits(context, rightNumber, rightUnits, commonUnits)
            if (rightConverted !is UnitsCalculateSuccess) context.unsupportedInfixOperator()

            val mathExpression = MathExpression.Infix(
                left = MathExpression.Number(leftConverted.number),
                right = MathExpression.Number(rightConverted.number),
                key = key
            )
            val result = context.recursive(mathExpression) as? MathCalculateSuccess
                ?: context.unsupportedInfixOperator()

            return UnitsCalculateSuccess(result.number, commonUnits)
        }

        private fun extractUnits(result: CalculateResult): UnitKey? {
            if (result is UnitsCalculateSuccess) return result.key
            return null
        }

        private fun convertUnits(
            context: CalculateContext,
            number: PreciseNumber,
            from: UnitKey?,
            to: UnitKey
        ): CalculateResult {
            if (from == null) return UnitsCalculateSuccess(number, to)
            val fromExpression = UnitsExpression.Conversion(MathExpression.Number(number), from)
            val toExpression = UnitsExpression.Conversion(fromExpression, to)
            return context.recursive(toExpression)
        }
    }

    public class Prod(
        private val supportedOperators: Set<InfixKey>
    ) : MathCalculateInfixOperatorFunction {

        override fun invoke(
            context: CalculateContext,
            left: CalculateResult.Success,
            right: CalculateResult.Success,
            key: InfixKey
        ): CalculateResult.Success {
            if (key !in supportedOperators) context.unsupportedInfixOperator()

            val units = extractUnits(left, right)
                ?: context.unsupportedInfixOperator()

            val leftNumber = extractNumber(left)?.let(MathExpression::Number)
                ?: context.unsupportedInfixOperator()

            val rightNumber = extractNumber(right)?.let(MathExpression::Number)
                ?: context.unsupportedInfixOperator()

            val mathExpression = MathExpression.Infix(leftNumber, rightNumber, key)

            val result = context.recursive(mathExpression) as? MathCalculateSuccess
                ?: context.unsupportedInfixOperator()

            return UnitsCalculateSuccess(result.number, units)
        }

        private fun extractUnits(
            left: CalculateResult,
            right: CalculateResult
        ): UnitKey? {
            // Both operands cannot have units if multiplying
            if (left is UnitsCalculateSuccess && right is UnitsCalculateSuccess) return null
            if (left is UnitsCalculateSuccess) return left.key
            if (right is UnitsCalculateSuccess) return right.key
            return null
        }
    }

    private fun extractNumber(result: CalculateResult): PreciseNumber? {
        if (result is UnitsCalculateSuccess) return result.number
        if (result is MathCalculateSuccess) return result.number
        return null
    }

}
