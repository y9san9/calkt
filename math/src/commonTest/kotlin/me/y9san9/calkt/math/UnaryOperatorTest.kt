package me.y9san9.calkt.math

import me.y9san9.calkt.calculate.tryCalculate
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.math.calculate.calculateMathExpression
import me.y9san9.calkt.math.parse.DefaultMathParseOperand
import me.y9san9.calkt.math.parse.parseMathExpression
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.getOrThrow
import me.y9san9.calkt.parse.tryParse
import kotlin.test.Test
import kotlin.test.assertEquals

class UnaryOperatorTest {
    @Test
    fun testUnaryMinus() {
        assertCalculation("-3 + 5", expected = 2)
    }

    @Test
    fun testUnaryPlus() {
        assertCalculation("+3 + 5", expected = 8)
    }

    @Test
    fun testUnaryOperatorBeforeGroup() {
        assertCalculation("-(3 + 5) * 2", expected = -16)
    }

    @Test
    fun testChainedUnaryOperators() {
        assertCalculation("--3", expected = 3)
        assertCalculation("+-3", expected = -3)
    }

    @Test
    fun testSignedNumberOperandKeepsMinus() {
        val parsed = tryParse("-3") { context ->
            DefaultMathParseOperand(context)
        }.getOrThrow()

        assertEquals(
            MathExpression.Number(PreciseNumber.of(-3)),
            parsed
        )
    }

    private fun assertCalculation(expression: String, expected: Int) {
        val parsed = tryParse(expression) { context ->
            context.parseMathExpression()
        }.getOrThrow()

        val calculated = tryCalculate(parsed, precision = 12) { context ->
            context.calculateMathExpression()
        }

        assertEquals(
            MathCalculateSuccess(PreciseNumber.of(expected)),
            calculated
        )
    }
}
