plugins {
    id("print-version-convention")
}

val calktVersion = providers.gradleProperty("releaseVersion")
    .orElse(libs.versions.calkt)
    .get()

allprojects {
    group = "com.itzephir.calkt"
    version = calktVersion
}
