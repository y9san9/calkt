package me.y9san9.calkt.units.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.math.parse.MathParseOperandFunction
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.*
import me.y9san9.calkt.parse.base.overrideCause
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.units.UnitsExpression

public class UnitsMathParseOperand(
    private val parseUnitKey: UnitsParseUnitKeyFunction,
    private val parseOperand: MathParseOperandFunction
) : MathParseOperandFunction {
    override fun invoke(context: ParseContext): Expression {
        context.tryParse {
            context.overrideCause(
                { failure -> ExpectedInputCause.of("Unit Before Number", failure = failure) }
            ) {
                val unitKey = parseUnitKey(context)
                val operand = parseOperand(context)
                return UnitsExpression.Conversion(operand, unitKey)
            }
        }.getOrElse(context) { failure ->
            context.pushNonTerminalCause(failure.cause)
        }

        context.tryParse {
            val operand = parseOperand(context)

            context.tryParse {
                context.overrideCause(
                    { failure -> ExpectedInputCause.of("Unit After Number", failure = failure) }
                ) {
                    val unitKey = parseUnitKey(context)
                    return UnitsExpression.Conversion(operand, unitKey)
                }
            }.getOrElse(context) { secondFailure ->
                context.pushNonTerminalCause(secondFailure.cause)
            }

            return operand
        }.getOrElse(context) { failure ->
            context.pushNonTerminalCause(failure.cause)
        }

        context.tryParse {
            val unitKey = parseUnitKey(context)

            return UnitsExpression.Conversion(
                value = MathExpression.Number(PreciseNumber.Companion.of(1)),
                key = unitKey
            )
        }

        context.failWithNonTerminalCauses()
    }
}
