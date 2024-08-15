package me.y9san9.calkt.units.parse

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.base.word
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.units.UnitKey

public fun interface UnitsParseUnitKeyFunction {
    public operator fun invoke(context: ParseContext): UnitKey

    public companion object {
        public fun ofWords(
            key: UnitKey,
            vararg words: String
        ): UnitsParseUnitKeyFunction = UnitsParseUnitKeyFunction { context ->
            context.token {
                context.word(*words) {
                    ExpectedInputCause.of("$key: One of ${words.joinToString()}")
                }
            }
            key
        }
    }
}
