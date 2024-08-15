package me.y9san9.calkt.units.parse

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.parseFirstOf
import me.y9san9.calkt.units.UnitKey

private class CombinedUnitsParseUnitKeyFunction(
    val functions: List<UnitsParseUnitKeyFunction>
) : UnitsParseUnitKeyFunction {
    override fun invoke(context: ParseContext): UnitKey {
        return context.parseFirstOf(
            blocks = functions.map { function ->
                { function.invoke(context) }
            }
        )
    }
}

public operator fun UnitsParseUnitKeyFunction.plus(other: UnitsParseUnitKeyFunction): UnitsParseUnitKeyFunction {
    val extensions = when {
        this is CombinedUnitsParseUnitKeyFunction &&
            other is CombinedUnitsParseUnitKeyFunction -> this.functions + other.functions
        this is CombinedUnitsParseUnitKeyFunction -> this.functions + other
        other is CombinedUnitsParseUnitKeyFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedUnitsParseUnitKeyFunction(extensions)
}
