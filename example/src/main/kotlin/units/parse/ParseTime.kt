package units.parse

import me.y9san9.calkt.units.parse.UnitsParseUnitKeyFunction
import me.y9san9.calkt.units.parse.plus
import units.TimeUnitKey

object ParseTime {
    val hours = UnitsParseUnitKeyFunction.ofStrings(
        TimeUnitKey.Hours,
        "h", "hour", "hours"
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
