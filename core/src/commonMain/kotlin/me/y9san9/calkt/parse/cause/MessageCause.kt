package me.y9san9.calkt.parse.cause

import me.y9san9.calkt.annotation.FailureCauseSubclass
import me.y9san9.calkt.parse.ParseResult

@OptIn(FailureCauseSubclass::class)
public interface MessageCause : FailureCause {
    public val string: String

    public companion object {
        public fun of(
            string: String,
            failure: ParseResult.Failure? = null
        ): MessageCause {
            return object : MessageCause {
                override val string = string
                override val failure = failure
                override fun toString() = string
            }
        }
    }
}
