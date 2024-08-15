package me.y9san9.calkt.parse.cause

import me.y9san9.calkt.internal.withIndent

public fun FailureCause.toConsoleOutput(
    message: (FailureCause) -> String = FailureCause::toString
): String {
    val messageString = when (this) {
        is MultipleCauses -> {
            buildString {
                appendLine("None of parsers could parse this input. Causes:")
                for (cause in causes) {
                    val causeString = cause
                        .toConsoleOutput(message)
                        .prependIndent("  ")
                        .drop(n = 2)
                    appendLine("- $causeString")
                }
            }
        }
        is MessageCause -> this.string
        else -> message(this)
    }
    return buildString {
        val failure = failure
        if (failure == null) {
            append(messageString)
            return@buildString
        }
        appendLine("FailureCause {")
        withIndent {
            append("message: ")
            append(messageString)
            appendLine()
            append("failure: ")
            append(failure.toConsoleOutput())
        }
        appendLine()
        append("}")
    }
}
