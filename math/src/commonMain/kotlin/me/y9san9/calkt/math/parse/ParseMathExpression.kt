package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext

public fun ParseContext.parseMathExpression(
    parseOperand: MathParseOperandFunction = DefaultMathParseOperand,
    infixOperatorList: List<MathParseInfixKeyFunction> = DefaultMathInfixOperators.list,
): Expression {
    val parse = MathParse(parseOperand, infixOperatorList)
    return parse(context)
}
