package me.y9san9.calkt.math

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.annotation.InfixKeySubclass
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators
import me.y9san9.calkt.math.parse.MathParseInfixAssociativity
import me.y9san9.calkt.math.parse.MathParseInfixKeyFunction
import me.y9san9.calkt.math.parse.MathParseInfixOperatorLevel
import me.y9san9.calkt.math.parse.MathParseInfixOperatorLevels
import me.y9san9.calkt.math.parse.parseMathExpression
import me.y9san9.calkt.math.parse.plus
import me.y9san9.calkt.number.PreciseNumber
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.getOrThrow
import me.y9san9.calkt.parse.tryParse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(InfixKeySubclass::class)
class InfixAssociativityTest {
    @Test
    fun legacyOperatorListRemainsLeftAssociative() {
        val parsed = tryParse("8 - 3 - 2") { context ->
            context.parseMathExpression()
        }.getOrThrow()

        assertEquals(leftAssociatedSubtraction(), parsed)
    }

    @Test
    fun configuredOperatorLevelsDefaultToLeftAssociativity() {
        val parsed = parseWithLevels(
            expression = "8 - 3 - 2",
            levels = DefaultMathInfixOperators.levels
        )

        assertEquals(leftAssociatedSubtraction(), parsed)
    }

    private fun leftAssociatedSubtraction(): MathExpression.Infix {
        return infix(
            left = infix(number(8), number(3), DefaultInfixKeys.Minus),
            right = number(2),
            key = DefaultInfixKeys.Minus
        )
    }

    @Test
    fun configuredOperatorLevelIsRightAssociative() {
        val parsed = parseWithLevels(
            expression = "2 ^ 3 ^ 2",
            levels = rightAssociativeLevel(Power)
        )

        assertEquals(
            infix(
                left = number(2),
                right = infix(number(3), number(2), PowerKey),
                key = PowerKey
            ),
            parsed
        )
    }

    @Test
    fun operatorsAtTheSameLevelShareRightAssociativity() {
        val parsed = parseWithLevels(
            expression = "2 ^ 3 ~ 4",
            levels = rightAssociativeLevel(Power + Root)
        )

        assertEquals(
            infix(
                left = number(2),
                right = infix(number(3), number(4), RootKey),
                key = PowerKey
            ),
            parsed
        )
    }

    @Test
    fun rightAssociativeLevelComposesWithOtherPrecedenceLevels() {
        val levels = MathParseInfixOperatorLevels(
            MathParseInfixOperatorLevel(Power, MathParseInfixAssociativity.RIGHT),
            MathParseInfixOperatorLevel(
                DefaultMathInfixOperators.Times + DefaultMathInfixOperators.Div
            )
        )
        val parsed = parseWithLevels("2 * 3 ^ 2 ^ 4", levels)

        assertEquals(
            infix(
                left = number(2),
                right = infix(
                    left = number(3),
                    right = infix(number(2), number(4), PowerKey),
                    key = PowerKey
                ),
                key = DefaultInfixKeys.Times
            ),
            parsed
        )
    }

    @Test
    fun groupingAndUnaryOperatorsComposeWithRightAssociativity() {
        val parsed = parseWithLevels(
            expression = "2 ^ -(3 ^ 2)",
            levels = rightAssociativeLevel(Power)
        )

        assertEquals(
            infix(
                left = number(2),
                right = MathExpression.Unary(
                    operand = infix(number(3), number(2), PowerKey),
                    key = DefaultUnaryKeys.Minus
                ),
                key = PowerKey
            ),
            parsed
        )
    }

    @Test
    fun incompleteRightAssociativeChainFails() {
        val result = tryParse("2 ^") { context ->
            context.parseMathExpression(
                infixOperatorLevels = rightAssociativeLevel(Power)
            )
        }

        assertIs<ParseResult.Failure>(result)
    }

    private fun parseWithLevels(
        expression: String,
        levels: MathParseInfixOperatorLevels
    ): Expression {
        return tryParse(expression) { context ->
            context.parseMathExpression(infixOperatorLevels = levels)
        }.getOrThrow()
    }

    private fun rightAssociativeLevel(
        parseInfixKey: MathParseInfixKeyFunction
    ): MathParseInfixOperatorLevels {
        return MathParseInfixOperatorLevels(
            MathParseInfixOperatorLevel(
                parseInfixKey = parseInfixKey,
                associativity = MathParseInfixAssociativity.RIGHT
            )
        )
    }

    private fun number(value: Int): MathExpression.Number {
        return MathExpression.Number(PreciseNumber.of(value))
    }

    private fun infix(
        left: Expression,
        right: Expression,
        key: InfixKey
    ): MathExpression.Infix {
        return MathExpression.Infix(left = left, right = right, key = key)
    }

    private data object PowerKey : InfixKey
    private data object RootKey : InfixKey

    private data object Power : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("^") { ExpectedInputCause.of("^") }
            return PowerKey
        }
    }

    private data object Root : MathParseInfixKeyFunction {
        override fun invoke(context: ParseContext): InfixKey {
            context.token("~") { ExpectedInputCause.of("~") }
            return RootKey
        }
    }
}
