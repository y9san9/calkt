package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext

public fun ParseContext.parseMathExpression(
    parseOperand: MathParseOperandFunction = DefaultMathParseOperand,
    infixOperatorList: List<MathParseInfixKeyFunction> = DefaultMathInfixOperators.list,
    parseUnaryKey: MathParseUnaryKeyFunction = DefaultMathUnaryOperators.function,
): Expression {
    val parse = MathParse(parseOperand, infixOperatorList, parseUnaryKey)
    return parse(context)
}

public fun ParseContext.parseMathExpression(
    parseOperand: MathParseOperandFunction = DefaultMathParseOperand,
    infixOperatorLevels: MathParseInfixOperatorLevels,
    parseUnaryKey: MathParseUnaryKeyFunction = DefaultMathUnaryOperators.function,
): Expression {
    val parse = MathParse(parseOperand, infixOperatorLevels, parseUnaryKey)
    return parse(context)
}
