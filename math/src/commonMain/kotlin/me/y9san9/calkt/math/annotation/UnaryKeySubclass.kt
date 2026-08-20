package me.y9san9.calkt.math.annotation

@RequiresOptIn(
    message = "Usage of MathExpression.Unary type is heavily dependent on knowledge of all subclasses. " +
        "So when you subclass MathExpression.Unary those places might brake, be careful",
)
public annotation class UnaryKeySubclass
