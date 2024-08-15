import org.gradle.kotlin.dsl.creating

tasks {
    val printVersion by creating {
        group = "CI"

        doFirst {
            println(versionFromProperties())
        }
    }
}
