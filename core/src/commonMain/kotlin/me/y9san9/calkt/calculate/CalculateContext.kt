package me.y9san9.calkt.calculate

import me.y9san9.calkt.Expression

public class CalculateContext(
    public val expression: Expression,
    public val calculateFunction: CalculateFunction,
    public val precision: Long
) {
    public val context: CalculateContext get() = this

    public fun tryCalculate(function: CalculateFunction): CalculateResult {
        return tryCalculate(expression, precision, function, calculateFunction)
    }

    public fun recursive(expression: Expression): CalculateResult.Success {
        val context = CalculateContext(expression, calculateFunction, precision)
        return calculateFunction(context)
    }

    public fun unsupportedExpression(): Nothing {
        fail(CalculateResult.UnsupportedExpression)
    }

    public fun fail(
        failure: CalculateResult.Failure
    ): Nothing {
        throw FailureException(failure)
    }

    public class FailureException(public val failure: CalculateResult.Failure) : Throwable()
}

public fun tryCalculate(
    expression: Expression,
    precision: Long,
    function: CalculateFunction
): CalculateResult {
    return tryCalculate(expression, precision, function, function)
}

public fun tryCalculate(
    expression: Expression,
    precision: Long,
    function: CalculateFunction,
    recursive: CalculateFunction
): CalculateResult {
    return try {
        val context = CalculateContext(expression, recursive, precision)
        function(context)
    } catch (exception: CalculateContext.FailureException) {
        exception.failure
    }
}
