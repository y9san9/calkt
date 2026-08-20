package me.y9san9.calkt.math

import me.y9san9.calkt.Expression
import me.y9san9.calkt.annotation.ExpressionSubclass
import me.y9san9.calkt.number.PreciseNumber

@OptIn(ExpressionSubclass::class)
public sealed interface MathExpression : Expression {
    public data class Number(val number: PreciseNumber) : MathExpression {
        override fun toString(): String {
            return "$number"
        }
    }

    public data class Unary(
        val operand: Expression,
        val key: UnaryKey
    ) : MathExpression {
        override fun toString(): String {
            val operandString = when (operand) {
                is Number -> "$operand"
                else -> "($operand)"
            }
            return "$key $operandString"
        }
    }

    public data class Infix(
        val left: Expression,
        val right: Expression,
        val key: InfixKey
    ) : MathExpression {
        override fun toString(): String {
            val leftString = when (left) {
                is Number -> "$left"
                else -> "($left)"
            }
            val rightString = when (right) {
                is Number -> "$right"
                else -> "($right)"
            }
            return "$leftString $key $rightString"
        }
    }
}
