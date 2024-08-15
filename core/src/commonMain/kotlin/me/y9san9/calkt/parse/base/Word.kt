package me.y9san9.calkt.parse.base

import me.y9san9.calkt.parse.ParseContext
import me.y9san9.calkt.parse.cause.FailureCause

public fun ParseContext.nextWord(): String {
    val string = token { takeWhile { char -> char.isLetter() } }
    return string
}

public fun ParseContext.tryWord(
    vararg strings: String
): Boolean {
    val backupPosition = this.position
    val word = nextWord()
    if (word !in strings) {
        this.position = backupPosition
        return false
    }
    return true
}

public fun ParseContext.word(
    vararg strings: String,
    cause: () -> FailureCause
) {
    if (!tryWord(*strings)) fail(cause())
}
