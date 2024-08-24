package me.y9san9.calkt.units.parse

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.*
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.units.UnitKey

public fun interface UnitsParseUnitKeyFunction {
    public operator fun invoke(context: ParseContext): UnitKey

    public companion object {
        public fun ofStrings(
            key: UnitKey,
            vararg strings: String
        ): UnitsParseUnitKeyFunction = UnitsParseUnitKeyFunction { context ->
            context.token {
                for (string in strings) {
                    if (context.startsWith(string)) {
                        val nextChar = context.string.getOrNull(index = context.position + string.length)
                        if (nextChar?.isLetter() == true) continue
                        context.drop(string.length)
                        return@UnitsParseUnitKeyFunction key
                    }
                }
            }
            context.fail(ExpectedInputCause.of("$key: One of ${strings.joinToString()}"))
        }
    }
}
