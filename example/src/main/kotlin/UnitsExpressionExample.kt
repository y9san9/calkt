import me.y9san9.calkt.calculate.CalculateResult
import me.y9san9.calkt.calculate.tryCalculate
import me.y9san9.calkt.math.calculate.MathCalculateSuccess
import me.y9san9.calkt.parse.ParseResult
import me.y9san9.calkt.parse.tryParse
import me.y9san9.calkt.units.calculate.UnitsCalculateSuccess
import me.y9san9.calkt.units.calculate.calculateUnitsExpression
import me.y9san9.calkt.units.parse.parseUnitsExpression
import units.calculate.DefaultUnitsConvert
import units.parse.DefaultParseUnitKey
import kotlin.system.exitProcess

fun unitsExpressionExample() {
    print("Enter an expression with units to calculate: ")
    val unitsExpression = readln()
    // Parse expression
    val parseResult = tryParse(unitsExpression) { context ->
        context.parseUnitsExpression(DefaultParseUnitKey)
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
    val result = tryCalculate(expression, precision = 12) { context -> context.calculateUnitsExpression(DefaultUnitsConvert) }

    when (result) {
        is MathCalculateSuccess -> println("Result: ${result.number}")
        is UnitsCalculateSuccess -> println("Result: ${result.number} ${result.key}")
        is CalculateResult.DivisionByZero -> println("Result: Division By Zero")
        else -> error("")
    }
}
