package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.DefaultUnaryKeys
import me.y9san9.calkt.math.UnaryKey
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.cause.ExpectedInputCause

public object DefaultMathUnaryOperators {
    public val function: MathParseUnaryKeyFunction = Plus + Minus

    public object Plus : MathParseUnaryKeyFunction {
        override fun invoke(context: ParseContext): UnaryKey {
            context.token("+") { ExpectedInputCause.of("+") }
            return DefaultUnaryKeys.Plus
        }
    }

    public object Minus : MathParseUnaryKeyFunction {
        override fun invoke(context: ParseContext): UnaryKey {
            context.token("-") { ExpectedInputCause.of("-") }
            return DefaultUnaryKeys.Minus
        }
    }
}
