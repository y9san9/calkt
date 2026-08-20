package me.y9san9.calkt.number

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlin.math.min

public class PreciseNumber private constructor(
    private val bigDecimal: BigDecimal
) {
    public operator fun plus(other: PreciseNumber): PreciseNumber {
        val result = bigDecimal + other.bigDecimal
        return PreciseNumber(result)
    }

    public operator fun minus(other: PreciseNumber): PreciseNumber {
        val result = bigDecimal - other.bigDecimal
        return PreciseNumber(result)
    }

    public fun divide(other: PreciseNumber, precision: Long): PreciseNumber {
        val minExponent = -min(this.bigDecimal.exponent, other.bigDecimal.exponent).coerceAtMost(maximumValue = 0)

        val thisBigInteger = this.bigDecimal.moveDecimalPoint(places = minExponent + precision).toBigInteger()
        val otherBigInteger = other.bigDecimal.moveDecimalPoint(minExponent).toBigInteger()

        val integerResult = thisBigInteger.divrem(otherBigInteger).quotient
        val result = BigDecimal.fromBigInteger(integerResult).moveDecimalPoint(places = -precision)

        return PreciseNumber(result)
    }

    public operator fun times(other: PreciseNumber): PreciseNumber {
        val result = bigDecimal * other.bigDecimal
        return PreciseNumber(result)
    }

    /**
     * Returns the exact remainder after truncating division by [other].
     *
     * @throws ArithmeticException if [other] is zero.
     */
    public operator fun rem(other: PreciseNumber): PreciseNumber {
        if (other.isZero()) {
            throw ArithmeticException("Cannot calculate a remainder with a zero divisor")
        }

        val thisScaleExponent = bigDecimal.exponent - bigDecimal.precision + 1
        val otherScaleExponent = other.bigDecimal.exponent - other.bigDecimal.precision + 1
        val commonScaleExponent = min(thisScaleExponent, otherScaleExponent)

        val thisInteger = bigDecimal.moveDecimalPoint(-commonScaleExponent).toBigInteger()
        val otherInteger = other.bigDecimal.moveDecimalPoint(-commonScaleExponent).toBigInteger()
        val remainder = thisInteger.divrem(otherInteger).remainder
        val result = BigDecimal.fromBigInteger(remainder).moveDecimalPoint(commonScaleExponent)

        return PreciseNumber(result)
    }

    public operator fun unaryPlus(): PreciseNumber = this
    public operator fun unaryMinus(): PreciseNumber = PreciseNumber(-bigDecimal)

    override fun equals(other: Any?): Boolean {
        if (other !is PreciseNumber) return false
        return this.bigDecimal == other.bigDecimal
    }

    public fun isZero(): Boolean {
        return bigDecimal == BigDecimal.ZERO
    }

    override fun hashCode(): Int {
        return bigDecimal.hashCode()
    }
    override fun toString(): String = bigDecimal.toStringExpanded()


    public companion object {
        public fun of(int: Int): PreciseNumber {
            return PreciseNumber(int.toBigDecimal())
        }
        public fun of(long: Long): PreciseNumber {
            return PreciseNumber(long.toBigDecimal())
        }
        public fun of(string: String ): PreciseNumber {
            return PreciseNumber(string.toBigDecimal())
        }
        public fun of(float: Float): PreciseNumber {
            return PreciseNumber(float.toBigDecimal())
        }
        public fun of(double: Double): PreciseNumber {
            return PreciseNumber(double.toBigDecimal())
        }
    }
}
