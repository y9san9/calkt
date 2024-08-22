package me.y9san9.calkt.units.parse

import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.math.parse.DefaultMathParseOperand
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.base.consume
import me.y9san9.calkt.parse.getOrThrow
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.UnitsExpression
import me.y9san9.calkt.units.annotation.UnitKeySubclass
import me.y9san9.calkt.units.parse.cause.ExpectedUnitsCause
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitsMathParseOperandTest {

    @Test
    fun testCanParseNumber() {
        val operand = create()

        val result = tryParse("1") { context ->
            operand(context)
        }.getOrThrow()

        val expected = MathExpression.Number(number = PreciseNumber.of(int = 1))

        assertEquals(expected, result)
    }

    @Test
    fun testCanParseGroup() {
        val operand = create()

        val result = tryParse("(1 unit)") { context ->
            operand(context)
        }.getOrThrow()

        val expected = UnitsExpression.Conversion(
            value = MathExpression.Number(
                number = PreciseNumber.of(int = 1)
            ),
            key = TestUnitKey
        )

        assertEquals(expected, result)
    }

    @Test
    fun testCanUnitBefore() {
        val operand = create()

        val result = tryParse("unit 1") { context ->
            operand(context)
        }.getOrThrow()

        val expected = UnitsExpression.Conversion(
            value = MathExpression.Number(
                number = PreciseNumber.of(int = 1)
            ),
            key = TestUnitKey
        )

        assertEquals(expected, result)
    }

    @Test
    fun testCanParseUnitAfter() {
        val operand = create()

        val result = tryParse("1 unit") { context ->
            operand(context)
        }.getOrThrow()

        val expected = UnitsExpression.Conversion(
            value = MathExpression.Number(
                number = PreciseNumber.of(int = 1)
            ),
            key = TestUnitKey
        )

        assertEquals(expected, result)
    }

    @Test
    fun testCanParseUnitOnly() {
        val operand = create()

        val result = tryParse("unit") { context ->
            operand(context)
        }.getOrThrow()

        val expected = UnitsExpression.Conversion(
            value = MathExpression.Number(
                number = PreciseNumber.of(int = 1)
            ),
            key = TestUnitKey
        )

        assertEquals(expected, result)
    }

    private fun create(): UnitsMathParseOperand {
        val parseUnitKey = UnitsParseUnitKeyFunction { context ->
            context.consume("unit") { ExpectedUnitsCause.of("unit") }
            TestUnitKey

        }
        val parseOperand = DefaultMathParseOperand
        return UnitsMathParseOperand(parseUnitKey, parseOperand)
    }

    @OptIn(UnitKeySubclass::class)
    private object TestUnitKey : UnitKey
}
