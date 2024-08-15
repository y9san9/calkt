package me.y9san9.calkt.units

import me.y9san9.calkt.Expression
import me.y9san9.calkt.annotation.ExpressionSubclass
import me.y9san9.calkt.math.MathExpression

@OptIn(ExpressionSubclass::class)
public sealed interface UnitsExpression : Expression {
    public data class Conversion(
        val value: Expression,
        val key: UnitKey
    ) : UnitsExpression {
        public fun optimize(): Conversion {
            return when {
                value !is Conversion -> this
                value.key == key -> value
                else -> this
            }
        }

        override fun toString(): String {
            return when (value) {
                is MathExpression.Number -> "$value $key"
                else -> "($value).convert($key)"
            }
        }
    }
}
