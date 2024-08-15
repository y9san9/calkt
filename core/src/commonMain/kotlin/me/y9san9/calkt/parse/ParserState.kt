package me.y9san9.calkt.parse

import me.y9san9.calkt.internal.withIndent

public data class ParserState(
    val string: String,
    val position: Int
) {
    public fun toConsolePointer(): String {
        return buildString {
            val fromIndex = (position - 10).coerceAtLeast(0)
            val toIndex = (position + 10).coerceAtMost(string.length)

            val leftOverflow = position - 10 > 0
            val rightOverflow = position + 10 < string.length

            if (leftOverflow) {
                append("...")
            }
            append(string.substring(fromIndex, toIndex))
            if (rightOverflow) {
                append("...")
            }
            appendLine()

            if (leftOverflow) {
                append("   ")
            }
            repeat(position - fromIndex) {
                append(" ")
            }
            append("^")
        }
    }

    public fun toConsoleOutput(): String {
        return buildString {
            appendLine("ParserState {")
            withIndent {
                appendLine("initial: $string")
                append("position: $position")
            }
            appendLine()
            append("}")
        }
    }
}
