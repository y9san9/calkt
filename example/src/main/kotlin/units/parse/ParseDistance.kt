package units.parse

import me.y9san9.calkt.units.parse.UnitsParseUnitKeyFunction
import me.y9san9.calkt.units.parse.plus
import units.DistanceUnitKey

object ParseDistance {
    // Metric
    val kilometers = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Kilometers,
        "km", "kilometer", "kilometers"
    )
    val meters = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Meters,
        "m", "meter", "meters"
    )
    val centimeters = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Centimeters,
        "cm", "centimeter", "centimeters"
    )
    val millimeters = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Millimeters,
        "mm", "millimeter", "millimeters"
    )

    // Imperial
    val mile = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Miles,
        "mi", "mile"
    )
    val yard = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Yards,
        "yd", "yard"
    )
    val foot = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Feet,
        "ft", "foot", "feet"
    )
    val inches = UnitsParseUnitKeyFunction.ofWords(
        DistanceUnitKey.Inches,
        "in", "inch", "inches"
    )

    val function = kilometers + meters + centimeters + millimeters +
        mile + yard + foot + inches
}
