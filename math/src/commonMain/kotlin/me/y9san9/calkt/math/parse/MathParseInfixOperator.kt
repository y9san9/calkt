package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.getOrElse
import me.y9san9.calkt.parse.tryParse

public class MathParseInfixOperator(
    private val parseOperand: MathParseOperandFunction,
    level: MathParseInfixOperatorLevel
) {
    private val parseInfixKey = level.parseInfixKey
    private val associativity = level.associativity

    public constructor(
        parseOperand: MathParseOperandFunction,
        parseInfixKey: MathParseInfixKeyFunction
    ) : this(parseOperand, MathParseInfixOperatorLevel(parseInfixKey))

    public operator fun invoke(context: ParseContext): Expression {
        return when (associativity) {
            MathParseInfixAssociativity.LEFT -> parseLeftAssociative(context)
            MathParseInfixAssociativity.RIGHT -> parseRightAssociative(context)
        }
    }

    private fun parseLeftAssociative(context: ParseContext): Expression {
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

    private fun parseRightAssociative(context: ParseContext): Expression {
        val operands = mutableListOf(parseOperand(context))
        val keys = mutableListOf<InfixKey>()

        while (true) {
            context.tryParse {
                val key = parseInfixKey(context)
                context.clearNonTerminalCauses()
                val next = parseOperand(context)
                keys += key
                operands += next
            }.getOrElse(context) { failure ->
                context.pushNonTerminalCause(failure.cause)
                return foldRight(operands, keys)
            }
        }
    }

    private fun foldRight(
        operands: List<Expression>,
        keys: List<InfixKey>
    ): Expression {
        var result = operands.last()

        for (index in keys.indices.reversed()) {
            result = MathExpression.Infix(
                left = operands[index],
                right = result,
                key = keys[index]
            )
        }

        return result
    }
}
