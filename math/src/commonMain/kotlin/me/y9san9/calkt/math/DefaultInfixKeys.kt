package me.y9san9.calkt.math

import me.y9san9.calkt.math.annotation.InfixKeySubclass

@OptIn(InfixKeySubclass::class)
public sealed interface DefaultInfixKeys : InfixKey {
    public data object Plus : DefaultInfixKeys {
        override fun toString(): String {
            return "plus"
        }
    }
    public data object Minus : DefaultInfixKeys {
        override fun toString(): String {
            return "minus"
        }
    }
    public data object Times : DefaultInfixKeys {
        override fun toString(): String {
            return "times"
        }
    }
    public data object Div : DefaultInfixKeys {
        override fun toString(): String {
            return "div"
        }
    }
}
