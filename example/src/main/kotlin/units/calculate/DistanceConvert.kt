package units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.number.PreciseNumber
import units.DistanceUnitKey

object DistanceConvert {
    operator fun invoke(
        context: CalculateContext,
        value: PreciseNumber,
        from: DistanceUnitKey,
        to: DistanceUnitKey
    ): CalculateResult {
        val result = value.times(multiplier(from)).divide(multiplier(to), context.precision)
        return MathCalculateSuccess(result)
    }

    private fun multiplier(
        key: DistanceUnitKey
    ): PreciseNumber {
        return when (key) {
            // Imperial
            DistanceUnitKey.Kilometers -> PreciseNumber.of(1_000_000)
            DistanceUnitKey.Meters -> PreciseNumber.of(1_000)
            DistanceUnitKey.Centimeters -> PreciseNumber.of(10)
            DistanceUnitKey.Millimeters -> PreciseNumber.of(1)
            // Metric
            DistanceUnitKey.Inches -> PreciseNumber.of(25.4)
            DistanceUnitKey.Feet -> PreciseNumber.of(304.8)
            DistanceUnitKey.Yards -> PreciseNumber.of(914.4)
            DistanceUnitKey.Miles -> PreciseNumber.of(1_609_344)
        }
    }
}
