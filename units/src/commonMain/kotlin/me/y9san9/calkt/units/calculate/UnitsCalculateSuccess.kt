package me.y9san9.calkt.units.calculate

import me.y9san9.calkt.annotation.CalculateSubclass
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.units.UnitKey

@OptIn(CalculateSubclass::class)
public data class UnitsCalculateSuccess(
    val number: PreciseNumber,
    val key: UnitKey
) : CalculateResult.Success
