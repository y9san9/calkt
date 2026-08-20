package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseFunction
import me.y9san9.calkt.parse.ParseContext

public class MathParse(
    operand: MathParseOperandFunction,
    infixOperatorLevels: MathParseInfixOperatorLevels,
    parseUnaryKey: MathParseUnaryKeyFunction = DefaultMathUnaryOperators.function
) : ParseFunction {
    private val parseUnaryOperator = MathParseUnaryOperator(operand, parseUnaryKey)
    private val parseInfixOperatorList = MathParseInfixOperatorList(parseUnaryOperator, infixOperatorLevels)

    public constructor(
        operand: MathParseOperandFunction,
        parseInfixKeyList: List<MathParseInfixKeyFunction>,
        parseUnaryKey: MathParseUnaryKeyFunction = DefaultMathUnaryOperators.function
    ) : this(
        operand = operand,
        infixOperatorLevels = MathParseInfixOperatorLevels(
            parseInfixKeyList.map(::MathParseInfixOperatorLevel)
        ),
        parseUnaryKey = parseUnaryKey
    )

    override fun invoke(context: ParseContext): Expression {
        return parseInfixOperatorList(context)
    }
}
