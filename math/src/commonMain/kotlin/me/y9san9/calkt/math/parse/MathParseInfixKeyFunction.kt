package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.parse.ParseContext

public interface MathParseInfixKeyFunction {
    public operator fun invoke(context: ParseContext): InfixKey
}
