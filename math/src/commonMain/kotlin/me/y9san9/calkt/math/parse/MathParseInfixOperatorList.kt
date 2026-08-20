package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext

public class MathParseInfixOperatorList(
    private val operand: MathParseOperandFunction,
    private val levels: MathParseInfixOperatorLevels
) {
    public constructor(
        operand: MathParseOperandFunction,
        parseInfixKeyList: List<MathParseInfixKeyFunction>
    ) : this(
        operand = operand,
        levels = MathParseInfixOperatorLevels(
            parseInfixKeyList.map(::MathParseInfixOperatorLevel)
        )
    )

    public operator fun invoke(context: ParseContext): Expression {
        var result: MathParseOperandFunction = operand

        for (level in levels.list) {
            result = infixOperand(result, level)
        }

        return result(context)
    }

    private fun infixOperand(
        operand: MathParseOperandFunction,
        level: MathParseInfixOperatorLevel
    ): MathParseOperandFunction = MathParseOperandFunction { context ->
        val infix = MathParseInfixOperator(operand, level)
        infix(context)
    }
}
