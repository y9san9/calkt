package me.y9san9.calkt.parse.cause

public interface MultipleCauses : MessageCause {
    public val causes: List<FailureCause>

    public companion object {
        public fun of(vararg causes: FailureCause): MultipleCauses {
            return object : MultipleCauses {
                override val causes = causes.flatMap(FailureCause::causes).toList()
                override val string = "None of parsers could parse this input. Causes: \n${causes.joinToString(separator = "\n").prependIndent("- ")}"
                override fun toString() = string
            }
        }
    }
}

public val FailureCause.causes: List<FailureCause> get() = when (this) {
    is MultipleCauses -> causes
    else -> listOf(this)
}
