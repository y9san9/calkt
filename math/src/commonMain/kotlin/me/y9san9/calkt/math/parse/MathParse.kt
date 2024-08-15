package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseFunction
import me.y9san9.calkt.parse.ParseContext

public class MathParse(
    operand: MathParseOperandFunction,
    parseInfixKeyList: List<MathParseInfixKeyFunction>
) : ParseFunction {
    private val parseInfixOperatorList = MathParseInfixOperatorList(operand, parseInfixKeyList)

    override fun invoke(context: ParseContext): Expression {
        return parseInfixOperatorList(context)
    }
}
