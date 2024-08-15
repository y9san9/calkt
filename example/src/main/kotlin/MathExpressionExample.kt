import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.tryCalculate
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.math.calculate.calculateMathExpression
import me.y9san9.calkt.math.parse.parseMathExpression
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.tryParse
import kotlin.system.exitProcess

fun mathExpressionExample() {
    print("Enter math expression to calculate: ")
    val mathExpression = readln()
    // Parse expression
    val parseResult = tryParse(mathExpression) { context ->
        context.parseMathExpression()
    }
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
    val result = tryCalculate(expression, precision = 12) { context -> context.calculateMathExpression() }

    when (result) {
        is MathCalculateSuccess -> println("Result: ${result.number}")
        is CalculateResult.DivisionByZero -> println("Result: Division By Zero")
        else -> error("")
    }
}
