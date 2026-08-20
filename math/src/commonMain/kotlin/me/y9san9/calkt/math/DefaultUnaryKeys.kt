package me.y9san9.calkt.math

import me.y9san9.calkt.math.annotation.UnaryKeySubclass

@OptIn(UnaryKeySubclass::class)
public sealed interface DefaultUnaryKeys : UnaryKey {
    public data object Plus : DefaultUnaryKeys {
        override fun toString(): String = "plus"
    }

    public data object Minus : DefaultUnaryKeys {
        override fun toString(): String = "minus"
    }
}
