plugins {
    id("kmp-library-convention")
}

dependencies {
    commonMainImplementation(libs.bignum)
    commonTestImplementation(projects.math)
    commonTestImplementation(projects.units)
}
