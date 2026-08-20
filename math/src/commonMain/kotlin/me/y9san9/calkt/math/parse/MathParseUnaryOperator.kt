package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.getOrElse
import me.y9san9.calkt.parse.tryParse

public class MathParseUnaryOperator(
    private val parseOperand: MathParseOperandFunction,
    private val parseUnaryKey: MathParseUnaryKeyFunction
) : MathParseOperandFunction {
    override fun invoke(context: ParseContext): Expression {
        return context.tryParse {
            val key = parseUnaryKey(context)
            context.clearNonTerminalCauses()
            val operand = invoke(context)
            context.clearNonTerminalCauses()
            MathExpression.Unary(operand = operand, key = key)
        }.getOrElse(context) { failure ->
            context.pushNonTerminalCause(failure.cause)
            parseOperand(context)
        }
    }
}
