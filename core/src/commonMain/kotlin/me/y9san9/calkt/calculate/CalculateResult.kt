package me.y9san9.calkt.calculate

import me.y9san9.calkt.annotation.CalculateSubclass

public sealed interface CalculateResult {
    @OptIn(ExperimentalSubclassOptIn::class)
    @SubclassOptInRequired(CalculateSubclass::class)
    public interface Failure : CalculateResult

    @OptIn(CalculateSubclass::class)
    public data object UnsupportedExpression : Failure

    @OptIn(CalculateSubclass::class)
    public data object DivisionByZero : Failure

    @OptIn(ExperimentalSubclassOptIn::class)
    @SubclassOptInRequired(CalculateSubclass::class)
    public interface Success : CalculateResult
}

public fun CalculateResult.getOrFail(context: CalculateContext): CalculateResult.Success {
    return when (this) {
        is CalculateResult.Failure -> context.fail(failure = this)
        is CalculateResult.Success -> this
    }
}
