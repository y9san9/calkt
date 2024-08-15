package me.y9san9.calkt.math.parse

import me.y9san9.calkt.math.DefaultInfixKeys
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.cause.ExpectedInputCause

public object DefaultMathInfixOperators {
    public val list: List<MathParseInfixKeyFunction> = listOf(
        Times + Div,    // Times, Div have the same priority
        Plus + Minus  // Plus, Minus have the same priority
    )

    public object Plus : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("+") { ExpectedInputCause.of("+") }
            return DefaultInfixKeys.Plus
        }
    }

    public object Minus : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("-") { ExpectedInputCause.of("-") }
            return DefaultInfixKeys.Minus
        }
    }

    public object Times : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("*") { ExpectedInputCause.of("*") }
            return DefaultInfixKeys.Times
        }
    }

    public object Div : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("/") { ExpectedInputCause.of("/") }
            return DefaultInfixKeys.Div
        }
    }
}
