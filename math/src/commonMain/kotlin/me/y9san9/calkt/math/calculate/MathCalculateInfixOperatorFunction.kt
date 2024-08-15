package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.InfixKey

public fun interface MathCalculateInfixOperatorFunction {
    public operator fun invoke(
        context: CalculateContext,
        left: CalculateResult.Success,
        right: CalculateResult.Success,
        key: InfixKey
    ): CalculateResult.Success
}
