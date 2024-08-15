package me.y9san9.calkt.annotation

@RequiresOptIn(
    message = "Usage of CalculateResult type is heavily dependent on knowledge of all subclasses. " +
        "So when you subclass CalculateResult those places might brake, be careful",
)
public annotation class CalculateSubclass
