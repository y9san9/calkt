package me.y9san9.calkt.math.annotation

@RequiresOptIn(
    message = "Usage of CalktExpression.Infix type is heavily dependent on knowledge of all subclasses. " +
        "So when you subclass CalktExpression.Infix those places might brake, be careful",
)
public annotation class InfixKeySubclass
