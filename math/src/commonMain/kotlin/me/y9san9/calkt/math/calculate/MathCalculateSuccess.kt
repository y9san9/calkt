package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.annotation.CalculateSubclass
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.number.PreciseNumber

@OptIn(CalculateSubclass::class)
public data class MathCalculateSuccess(val number: PreciseNumber) : CalculateResult.Success
