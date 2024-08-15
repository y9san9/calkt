package me.y9san9.calkt.calculate

public fun interface CalculateFunction {
    public operator fun invoke(context: CalculateContext): CalculateResult.Success
}
