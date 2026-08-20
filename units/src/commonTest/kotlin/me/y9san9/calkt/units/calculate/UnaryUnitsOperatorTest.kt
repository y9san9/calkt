package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.tryCalculate
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.base.consume
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.getOrThrow
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.annotation.UnitKeySubclass
import me.y9san9.calkt.units.parse.UnitsParseUnitKeyFunction
import me.y9san9.calkt.units.parse.parseUnitsExpression
import kotlin.test.Test
import kotlin.test.assertEquals

class UnaryUnitsOperatorTest {
    @Test
    fun testUnaryMinusPreservesUnit() {
        val parseUnitKey = UnitsParseUnitKeyFunction { context ->
            context.consume("unit") { ExpectedInputCause.of("unit") }
            TestUnitKey
        }
        val parsed = tryParse("-3 unit") { context ->
            context.parseUnitsExpression(parseUnitKey)
        }.getOrThrow()

        val calculated = tryCalculate(parsed, precision = 12) { context ->
            context.calculateUnitsExpression(
                convert = UnitsConvertFunction { _, value, _, _ ->
                    MathCalculateSuccess(value)
                }
            )
        }

        assertEquals(
            UnitsCalculateSuccess(PreciseNumber.of(-3), TestUnitKey),
            calculated
        )
    }

    @OptIn(UnitKeySubclass::class)
    private data object TestUnitKey : UnitKey
}
