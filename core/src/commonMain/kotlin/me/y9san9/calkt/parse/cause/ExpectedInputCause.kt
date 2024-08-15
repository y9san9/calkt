package me.y9san9.calkt.parse.cause

import me.y9san9.calkt.parse.ParseResult

public interface ExpectedInputCause : MessageCause {
    public val expected: String

    public companion object {
        public fun of(
            expected: String,
            failure: ParseResult.Failure? = null
        ): ExpectedInputCause {
            return object : ExpectedInputCause {
                override val expected = expected
                override val string = "Expected $expected"
                override val failure = failure
                override fun toString() = string
            }
        }
    }
}
