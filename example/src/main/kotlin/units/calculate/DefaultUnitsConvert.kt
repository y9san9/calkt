package units.calculate

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.calculate.UnitsCalculateFailure
import me.y9san9.calkt.units.calculate.UnitsConvertFunction
import units.DistanceUnitKey
import units.TimeUnitKey

object DefaultUnitsConvert : UnitsConvertFunction {
    override fun invoke(context: CalculateContext, value: PreciseNumber, from: UnitKey, to: UnitKey): CalculateResult {
        return when {
            from is DistanceUnitKey && to is DistanceUnitKey -> DistanceConvert(context, value, from, to)
            from is TimeUnitKey && to is TimeUnitKey -> TimeConvert(context, value, from, to)
            else -> UnitsCalculateFailure.UnitsCantBeConverted
        }
    }
}
