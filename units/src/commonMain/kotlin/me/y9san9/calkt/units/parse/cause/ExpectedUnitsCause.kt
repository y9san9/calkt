package me.y9san9.calkt.units.parse.cause

import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.cause.ExpectedInputCause

public interface ExpectedUnitsCause : ExpectedInputCause {
    public val expectedList: List<String>

    public companion object {
        public fun of(
            vararg expected: String,
            failure: ParseResult.Failure? = null
        ): ExpectedUnitsCause {
            return object : ExpectedUnitsCause, ExpectedInputCause by ExpectedInputCause.of(
                expected = "Unit: ${expected.joinToString()}",
                failure = failure
            ) {
                override val expectedList: List<String> = expected.toList()
                override fun toString() = string
            }
        }
    }
}
