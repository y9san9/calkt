package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.UnaryKey
import me.y9san9.calkt.parse.ParseContext

public interface MathParseUnaryKeyFunction {
    public operator fun invoke(context: ParseContext): UnaryKey
}
