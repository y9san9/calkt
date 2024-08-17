plugins {
    id("kmp-library-convention")
}

version = libs.versions.calkt.get()

dependencies {
    commonMainApi(projects.math)
}
