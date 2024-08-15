package me.y9san9.calkt.annotation

@RequiresOptIn(
    message = "Usage of Expression type is heavily dependent on knowledge of all subclasses. " +
        "So when you subclass Expression those places might brake, be careful",
)
public annotation class ExpressionSubclass
