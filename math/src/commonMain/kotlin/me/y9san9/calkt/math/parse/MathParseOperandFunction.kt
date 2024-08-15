package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext

public fun interface MathParseOperandFunction {
    public operator fun invoke(context: ParseContext): Expression
}
