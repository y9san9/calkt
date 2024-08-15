package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.calculate.UnitsCalculateFailure.UnitsCantBeConverted

private class CombinedUnitsConvertFunction(
    val functions: List<UnitsConvertFunction>
): UnitsConvertFunction {

    override fun invoke(
        context: CalculateContext,
        value: PreciseNumber,
        from: UnitKey,
        to: UnitKey
    ): CalculateResult {
        for (function in functions) {
            val result = function.invoke(context, value, from, to)
            if (result is UnitsCantBeConverted) continue
            return result
        }

        return UnitsCantBeConverted
    }
}

public operator fun UnitsConvertFunction.plus(other: UnitsConvertFunction): UnitsConvertFunction {
    val functions = when {
        this is CombinedUnitsConvertFunction &&
            other is CombinedUnitsConvertFunction -> this.functions + other.functions
        this is CombinedUnitsConvertFunction -> this.functions + other
        other is CombinedUnitsConvertFunction -> listOf(this) + other.functions
        else -> listOf(this, other)
    }
    return CombinedUnitsConvertFunction(functions)
}
