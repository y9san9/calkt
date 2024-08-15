package units

import me.y9san9.calkt.units.UnitKey
import me.y9san9.calkt.units.annotation.UnitKeySubclass

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
