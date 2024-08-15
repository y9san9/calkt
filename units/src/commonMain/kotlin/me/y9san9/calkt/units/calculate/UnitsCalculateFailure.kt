package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.annotation.CalculateSubclass
import me.y9san9.calkt.calculate.CalculateResult

@OptIn(CalculateSubclass::class)
public sealed interface UnitsCalculateFailure : CalculateResult.Failure {
    public data object UnsupportedConversion : UnitsCalculateFailure
    public data object UnitsCantBeConverted : UnitsCalculateFailure
}
