package units.parse

import me.y9san9.calkt.units.parse.UnitsParseUnitKeyFunction
import me.y9san9.calkt.units.parse.plus
import units.DistanceUnitKey

object ParseDistance {
    // Metric
    val kilometers = UnitsParseUnitKeyFunction.ofStrings(
        DistanceUnitKey.Kilometers,
        "km", "kilometer", "kilometers", "\$CSACS*(_)(*"
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
