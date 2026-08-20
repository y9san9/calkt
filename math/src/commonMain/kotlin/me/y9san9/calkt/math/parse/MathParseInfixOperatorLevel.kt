package me.y9san9.calkt.math.parse

public data class MathParseInfixOperatorLevel(
    public val parseInfixKey: MathParseInfixKeyFunction,
    public val associativity: MathParseInfixAssociativity = MathParseInfixAssociativity.LEFT
)
