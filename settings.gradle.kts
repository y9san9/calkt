enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "calkt"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

includeBuild("build-logic")

include("core", "units", "math", "example")
