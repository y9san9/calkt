package me.y9san9.calkt.parse.cause

public interface HasRemainingInput<T> : ExpectedInputCause {
    public val parsedValue: T
    override val string: String

    public companion object {
        public fun <T> of(value: T): HasRemainingInput<T> {
             return object : HasRemainingInput<T>, ExpectedInputCause by ExpectedInputCause.of("EOF") {
                 override val parsedValue = value
                 override fun toString() = string
             }
        }
    }
}
