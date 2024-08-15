package me.y9san9.calkt.math.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.base.overrideCause
import me.y9san9.calkt.parse.getOrElse
import me.y9san9.calkt.parse.tryParse

private val defaultCause = { failure: ParseResult.Failure -> ExpectedInputCause.of("GROUP", failure) }

public class MathParseGroupFunction(
    private val operand: MathParseOperandFunction,
    private val beginGroup: Begin = Begin.Default,
    private val endGroup: End = End.Default
) {
    public operator fun invoke(context: ParseContext): Expression {
        return context.tryParse {
            operand(context)
        }.getOrElse(context) {
            context.overrideCause(defaultCause) {
                beginGroup(context)
                val result = context.recursive()
                context.tryParse {
                    endGroup(context)
                    context.clearNonTerminalCauses()
                }.getOrElse(context) {
                    context.failWithNonTerminalCauses()
                }
                result
            }
        }
    }

    public fun interface Begin {
        public operator fun invoke(context: ParseContext)

        public object Default : Begin {
            override fun invoke(context: ParseContext) {
                context.token("(") { ExpectedInputCause.of("GROUP_START") }
            }
        }
    }

    public fun interface End {
        public operator fun invoke(context: ParseContext)

        public object Default : End {
            override fun invoke(context: ParseContext) {
                context.token(")") { ExpectedInputCause.of("GROUP_END") }
            }
        }
    }
}
