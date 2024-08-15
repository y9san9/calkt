package me.y9san9.calkt.units.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.consume
import me.y9san9.calkt.parse.ParseFunction
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.UnitsExpression

public class UnitsParse(
    private val parse: ParseFunction,
    private val parseUnitKey: UnitsParseUnitKeyFunction,
    private val conversion: Conversion = Conversion { context -> context.consume("to") { ExpectedInputCause.of("to") } }
) : ParseFunction {

    override fun invoke(context: ParseContext): Expression {
        val expression = parse(context)

        context.tryParse {
            conversion(context)
            context.tryParse {
                val unitKey = parseUnitKey(context)
                return UnitsExpression.Conversion(expression, unitKey)
            }
        }

        return expression
    }

    public fun interface Conversion {
        public operator fun invoke(context: ParseContext)
    }
}
