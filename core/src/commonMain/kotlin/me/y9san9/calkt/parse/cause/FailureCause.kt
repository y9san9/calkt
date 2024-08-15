package me.y9san9.calkt.parse.cause

import me.y9san9.calkt.annotation.FailureCauseSubclass
import me.y9san9.calkt.parse.ParseResult

@OptIn(ExperimentalSubclassOptIn::class)
@SubclassOptInRequired(FailureCauseSubclass::class)
public interface FailureCause {
    public val failure: ParseResult.Failure? get() = null
}
