package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.UnaryKey

public interface MathCalculateUnaryOperatorFunction {
    public operator fun invoke(
        context: CalculateContext,
        operand: CalculateResult.Success,
        key: UnaryKey
    ): CalculateResult.Success
}
