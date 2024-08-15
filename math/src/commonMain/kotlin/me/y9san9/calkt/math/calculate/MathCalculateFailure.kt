package me.y9san9.calkt.math.calculate

import me.y9san9.calkt.annotation.CalculateSubclass
import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult

@OptIn(CalculateSubclass::class)
public sealed interface MathCalculateFailure : CalculateResult.Failure {
    public data object UnsupportedInfixOperator : MathCalculateFailure
}

public fun CalculateContext.unsupportedInfixOperator(): Nothing {
    fail(MathCalculateFailure.UnsupportedInfixOperator)
}
