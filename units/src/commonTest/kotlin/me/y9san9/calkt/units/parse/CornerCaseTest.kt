package me.y9san9.calkt.units.parse

import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.annotation.UnitKeySubclass
import me.y9san9.calkt.units.calculate.UnitsCalculateFailure
import me.y9san9.calkt.units.calculate.UnitsConvertFunction
import kotlin.test.Test
import kotlin.test.assertTrue

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

object DefaultUnitsConvert : UnitsConvertFunction {
    override fun invoke(context: CalculateContext, value: PreciseNumber, from: UnitKey, to: UnitKey): CalculateResult {
        return when {
            from is DistanceUnitKey && to is DistanceUnitKey -> DistanceConvert(context, value, from, to)
            from is TimeUnitKey && to is TimeUnitKey -> TimeConvert(context, value, from, to)
            else -> UnitsCalculateFailure.UnitsCantBeConverted
        }
    }
}

@OptIn(UnitKeySubclass::class)
sealed interface TimeUnitKey : UnitKey {
    data object Hours : TimeUnitKey
    data object Minutes : TimeUnitKey
    data object Seconds : TimeUnitKey
    data object Millis : TimeUnitKey
}

@OptIn(UnitKeySubclass::class)
sealed interface DistanceUnitKey : UnitKey {
    // Metric
    data object Kilometers : DistanceUnitKey
    data object Meters : DistanceUnitKey
    data object Centimeters : DistanceUnitKey
    data object Millimeters : DistanceUnitKey

    // Imperial
    data object Inches : DistanceUnitKey
    data object Feet : DistanceUnitKey
    data object Yards : DistanceUnitKey
    data object Miles : DistanceUnitKey
}

object ParseDistance {
    // Metric
    val kilometers = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Kilometers,
        "km", "kilometer", "kilometers"
    )
    val meters = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Meters,
        "m", "meter", "meters"
    )
    val centimeters = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Centimeters,
        "cm", "centimeter", "centimeters"
    )
    val millimeters = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Millimeters,
        "mm", "millimeter", "millimeters"
    )

    // Imperial
    val mile = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Miles,
        "mi", "mile"
    )
    val yard = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Yards,
        "yd", "yard"
    )
    val foot = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Feet,
        "ft", "foot", "feet"
    )
    val inches = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Inches,
        "in", "inch", "inches"
    )

    val function = kilometers + meters + centimeters + millimeters +
            mile + yard + foot + inches
}

object ParseTime {
    val hours = UnitsParseUnitKeyFunction.ofStrings(
        TimeUnitKey.Hours,
        "h", "hr", "hrs", "hour", "hours"
    )
    val minutes = UnitsParseUnitKeyFunction.ofStrings(
        TimeUnitKey.Minutes,
        "min", "mins", "minute", "minutes"
    )
    val seconds = UnitsParseUnitKeyFunction.ofStrings(
        TimeUnitKey.Seconds,
        "sec", "second", "seconds"
    )
    val milliseconds = UnitsParseUnitKeyFunction.ofStrings(
        TimeUnitKey.Millis,
        "millis", "millisecond", "milliseconds"
    )

    val function = hours + minutes + seconds + minutes + milliseconds
}

object DefaultParseUnitKey : UnitsParseUnitKeyFunction {
    val function = ParseDistance.function + ParseTime.function

    override fun invoke(context: ParseContext): UnitKey {
        return function(context)
    }
}

class CornerCaseTest {
    @Test
    fun testMillimetersToMeters() {
        val unitsExpression = "mm to m"

        val parseResult = tryParse(unitsExpression) { context ->
            context.parseUnitsExpression(DefaultParseUnitKey)
        }

        assertTrue { parseResult is ParseResult.Success }
    }
}
