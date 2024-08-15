package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext

public class MathParseInfixOperatorList(
    private val operand: MathParseOperandFunction,
    private val parseInfixKeyList: List<MathParseInfixKeyFunction>
) {
    public operator fun invoke(context: ParseContext): Expression {
        var result: MathParseOperandFunction = operand

        for (parseInfixKey in parseInfixKeyList) {
            result = infixOperand(result, parseInfixKey)
        }

        return result(context)
    }

    private fun infixOperand(
        operand: MathParseOperandFunction,
        parseInfixKey: MathParseInfixKeyFunction
    ): MathParseOperandFunction = MathParseOperandFunction { context ->
        val infix = MathParseInfixOperator(operand, parseInfixKey)
        infix(context)
    }
}
