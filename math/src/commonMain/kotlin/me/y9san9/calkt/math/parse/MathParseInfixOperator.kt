package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.getOrElse
import me.y9san9.calkt.parse.tryParse

public class MathParseInfixOperator(
    private val parseOperand: MathParseOperandFunction,
    private val parseInfixKey: MathParseInfixKeyFunction
) {
    public operator fun invoke(context: ParseContext): Expression {
        var result = parseOperand(context)

        while (true) {
            context.tryParse {
                val key = parseInfixKey(context)
                context.clearNonTerminalCauses()
                val next = parseOperand(context)
                result = MathExpression.Infix(
                    left = result,
                    right = next,
                    key = key
                )
            }.getOrElse(context) { failure ->
                context.pushNonTerminalCause(failure.cause)
                return result
            }
        }
    }
}
