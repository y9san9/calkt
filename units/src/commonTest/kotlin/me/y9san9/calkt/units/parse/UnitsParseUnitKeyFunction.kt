package me.y9san9.calkt.units.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.annotation.ExpressionSubclass
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.annotation.UnitKeySubclass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UnitsParseUnitKeyFunctionTest {
    @Test
    fun testDefaultImplementationChecksForNextChar() {
        val function = create()

        val result = tryParse("prefixtest") { context ->
            function(context) as Expression
        }
        assertIs<ParseResult.Success<TestKey>>(result)
        assertEquals(TestKey, result.value)
    }

    private fun create(): UnitsParseUnitKeyFunction {
        return UnitsParseUnitKeyFunction.ofStrings(TestKey, "prefix", "prefixtest")
    }

    @OptIn(UnitKeySubclass::class, ExpressionSubclass::class)
    private data object TestKey : UnitKey, Expression
}
