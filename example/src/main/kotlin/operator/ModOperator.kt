package operator

import me.y9san9.calkt.Expression
import me.y9san9.calkt.calculate.CalculateContext
import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.tryCalculate
import me.y9san9.calkt.math.InfixKey
import me.y9san9.calkt.math.annotation.InfixKeySubclass
import me.y9san9.calkt.math.calculate.*
import me.y9san9.calkt.math.calculate.MathCalculateFailure.UnsupportedInfixOperator
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators.Div
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators.Minus
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators.Plus
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators.Times
import me.y9san9.calkt.math.parse.MathParseInfixKeyFunction
import me.y9san9.calkt.math.parse.parseMathExpression
import me.y9san9.calkt.math.parse.plus
import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.base.token
import me.y9san9.calkt.parse.cause.ExpectedInputCause
import me.y9san9.calkt.parse.tryParse
import kotlin.system.exitProcess

@OptIn(InfixKeySubclass::class)
data object ModKey : InfixKey {
    override fun toString() = "mod"
}

data object ModParse : MathParseInfixKeyFunction {
    override fun invoke(context: ParseContext): InfixKey {
        context.token("%") { ExpectedInputCause.of("%") }
        return ModKey
    }
}

data object ModCalculate : MathCalculateInfixOperatorFunction {
    override fun invoke(
        context: CalculateContext,
        left: CalculateResult.Success,
        right: CalculateResult.Success,
        key: InfixKey
    ): CalculateResult.Success {
        if (left !is MathCalculateSuccess) context.fail(UnsupportedInfixOperator)
        if (right !is MathCalculateSuccess) context.fail(UnsupportedInfixOperator)
        if (key != ModKey) context.fail(UnsupportedInfixOperator)
        return MathCalculateSuccess(number = left.number % right.number)
    }
}

val exampleInfixOperatorList = listOf(
    Times + Div,  // Times, Div have the same priority
    Plus + Minus + ModParse  // Plus, Minus, Mod have the same priority
)

fun ParseContext.parseExampleExpression(): Expression {
    return context.parseMathExpression(infixOperatorList = exampleInfixOperatorList)
}

val exampleCalculateInfixOperator = DefaultMathCalculateInfixOperator + ModCalculate

fun CalculateContext.calculateExampleExpression(): CalculateResult.Success {
    return context.calculateMathExpression(calculateInfixOperator = exampleCalculateInfixOperator)
}

fun main() {
    print("Enter math expression to calculate: ")
    val mathExpression = readln()
    // Parse expression
    val parseResult = tryParse(mathExpression) { context -> context.parseExampleExpression() }
    when (parseResult) {
        is ParseResult.Failure -> {
            System.err.println("Cannot parse expression:")
            System.err.print(parseResult.toConsoleOutput())
            exitProcess(0)
        }
        is ParseResult.Success -> {
            println("Parsed as: ${parseResult.value}")
        }
    }

    // Calculate expression
    val expression = parseResult.value
    val result = tryCalculate(expression, precision = 12) { context -> context.calculateExampleExpression() }

    when (result) {
        is MathCalculateSuccess -> println("Result: ${result.number}")
        is CalculateResult.DivisionByZero -> println("Result: Division By Zero")
        else -> error("")
    }
}
