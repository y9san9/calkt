package me.y9san9.calkt.parse

import me.y9san9.calkt.Expression

public fun interface ParseFunction {
    public operator fun invoke(context: ParseContext): Expression
}
