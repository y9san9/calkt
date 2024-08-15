package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.MathExpression
import me.y9san9.calkt.number.nextPreciseNumber
import me.y9san9.calkt.parse.ParseContext

public object DefaultMathParseOperand : MathParseOperandFunction {

    private val operand = MathParseOperandFunction { context ->
        MathExpression.Number(context.nextPreciseNumber())
    }

    override fun invoke(context: ParseContext): Expression {
        val parseGroup = MathParseGroupFunction(operand)
        return parseGroup(context)
    }
}
