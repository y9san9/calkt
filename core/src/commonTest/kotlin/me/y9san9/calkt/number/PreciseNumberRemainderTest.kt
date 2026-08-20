package me.y9san9.calkt.number

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PreciseNumberRemainderTest {
    @Test
    fun calculatesIntegerRemainder() {
        assertEquals(PreciseNumber.of(2), PreciseNumber.of(10) % PreciseNumber.of(4))
    }

    @Test
    fun calculatesDecimalRemainderExactly() {
        assertEquals(PreciseNumber.of("1.5"), PreciseNumber.of("5.5") % PreciseNumber.of(2))
        assertEquals(PreciseNumber.of("0.1"), PreciseNumber.of(1) % PreciseNumber.of("0.3"))
        assertEquals(PreciseNumber.of("0.5"), PreciseNumber.of("0.5") % PreciseNumber.of(2))
    }

    @Test
    fun calculatesRemainderForDifferentPositiveExponents() {
        assertEquals(PreciseNumber.of(200), PreciseNumber.of(1200) % PreciseNumber.of(500))
        assertEquals(PreciseNumber.of("0.1"), PreciseNumber.of("1e3") % PreciseNumber.of("3e-1"))
    }

    @Test
    fun remainderHasDividendSign() {
        assertEquals(PreciseNumber.of(-2), PreciseNumber.of(-10) % PreciseNumber.of(4))
        assertEquals(PreciseNumber.of(2), PreciseNumber.of(10) % PreciseNumber.of(-4))
        assertEquals(PreciseNumber.of(-2), PreciseNumber.of(-10) % PreciseNumber.of(-4))
    }

    @Test
    fun exactDivisionHasZeroRemainder() {
        assertEquals(PreciseNumber.of(0), PreciseNumber.of("5.5") % PreciseNumber.of("0.5"))
    }

    @Test
    fun zeroDivisorThrowsArithmeticException() {
        assertFailsWith<ArithmeticException> {
            PreciseNumber.of(10) % PreciseNumber.of("0.0")
        }
    }
}
