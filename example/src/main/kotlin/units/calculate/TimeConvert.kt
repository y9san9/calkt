package units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.number.PreciseNumber
import units.TimeUnitKey

object TimeConvert {
    operator fun invoke(
        context: CalculateContext,
        value: PreciseNumber,
        from: TimeUnitKey,
        to: TimeUnitKey
    ): CalculateResult {
        val result = value.times(multiplier(from)).divide(multiplier(to), context.precision)
        return MathCalculateSuccess(result)
    }

    private fun multiplier(key: TimeUnitKey): PreciseNumber {
        return when (key) {
            TimeUnitKey.Hours -> PreciseNumber.of(3_600_000)
            TimeUnitKey.Minutes -> PreciseNumber.of(60_000)
            TimeUnitKey.Seconds -> PreciseNumber.of(1_000)
            TimeUnitKey.Millis -> PreciseNumber.of(1)
        }
    }
}
