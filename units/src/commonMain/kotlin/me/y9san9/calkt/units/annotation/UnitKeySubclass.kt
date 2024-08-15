package me.y9san9.calkt.units.annotation

@RequiresOptIn(
    message = "Usage of UnitsExpression.Unit type is heavily dependent on knowledge of all subclasses. " +
        "So when you subclass UnitsExpression.Unit those places might brake, be careful",
)
public annotation class UnitKeySubclass
