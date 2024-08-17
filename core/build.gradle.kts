plugins {
    id("kmp-library-convention")
}

version = libs.versions.calkt.get()

dependencies {
    commonMainImplementation(libs.bignum)
    commonTestImplementation(projects.math)
    commonTestImplementation(projects.units)
}
