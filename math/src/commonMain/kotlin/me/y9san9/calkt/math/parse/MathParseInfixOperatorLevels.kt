package me.y9san9.calkt.math.parse

public data class MathParseInfixOperatorLevels(
    public val list: List<MathParseInfixOperatorLevel>
) {
    public constructor(vararg levels: MathParseInfixOperatorLevel) : this(levels.toList())
}
