# Calkt

Calkt is a Kotlin library that supports parsing and calculating
various expressions. Parser is written in a way to have an ability
to be extended.

<!-- TOC -->
* [Calkt](#calkt)
  * [Example](#example)
  * [Implementation](#implementation)
  * [Modules](#modules)
    * [core](#core)
    * [math](#math)
    * [units](#units)
    * [example](#example-1)
<!-- TOC -->

## Example

The result of running [example/Main.kt](example/src/main/kotlin/Main.kt):

```text
Enter math expression to calculate: 2 + 2 * 2
Parsed as: 2 plus (2 times 2)
Result: 6

Enter an expression with units to calculate: (1km - 0.5mi) + 2 yd to inches
Parsed as: (((1 Kilometers) minus (0.5 Miles)) plus (2 Yards)).convert(Inches)
Result: 7886.272 Inches
```

## Implementation

Work In Progress. Will be available at maven central soon.
Now you can use workarounds like [jitpack.io](https://jitpack.io/#y9san9/calkt/-SNAPSHOT):

Add Jitpack repo in the root build.gradle at the end of repositories:

```kotlin
dependencyResolutionManagement {
  repositories {
    mavenCentral()
    maven("https://jitpack.io")
  }
}
```

Step 2. Add the dependency
```kotlin
dependencies {
  implementation("com.github.y9san9:calkt:-SNAPSHOT")
}
```

## Modules

The library consists of several different modules:

### core

Module with all basic types for parsing and calculating. Here
you can find `ParseContext` and `CalculateContext` as well as
functions to launch parsers/calculators.

### math

Module with implementation of basic math expressions that any
calculator can calculate. This is where you can find logic to
calculate numbers combined with basic supported operators (
`+`, `-`, `*`, `/`). You can implement your own operator, like 
in [this example](example/src/main/kotlin/operator/ModOperator.kt) (% operator).

### units

Module with implementation of math expressions with units. It
depends on `math` module heavily and does not do any calculations
on its own. There is a logic to support calculation and 
conversions of units like `1 km + 2`, `1km to inches`, etc.

### example

Module with all the examples you need to know. If something is missing,
PRs are welcome.
