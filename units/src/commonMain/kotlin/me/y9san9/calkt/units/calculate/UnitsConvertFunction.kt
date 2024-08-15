package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.math.calculate.MathCalculateSuccess

public fun interface UnitsConvertFunction {
    /**
     * @returns either [MathCalculateSuccess] with number in it, or [UnitsCalculateFailure.UnitsCantBeConverted]
     */
    public operator fun invoke(
        context: CalculateContext,
        value: PreciseNumber,
        from: UnitKey,
        to: UnitKey
    ): CalculateResult
}
