plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    pom {
        name = "calkt-${project.name}"
        description = "Kotlin library that supports parsing and calculating various expressions"
        url = "https://github.com/itzephir/calkt"

        licenses {
            license {
                name = "MIT License"
                distribution = "repo"
                url = "https://github.com/itzephir/calkt/blob/main/LICENSE.md"
            }
        }

        developers {
            developer {
                id = "itzephir"
                name = "Dmitry Dvoryannikov"
                email = "81320723+itzephir@users.noreply.github.com"
                url = "https://github.com/itzephir"
            }
        }

        scm {
            connection = "scm:git:https://github.com/itzephir/calkt.git"
            developerConnection = "scm:git:ssh://git@github.com/itzephir/calkt.git"
            url = "https://github.com/itzephir/calkt"
        }
    }

    val hasSecretKeyRing = providers.gradleProperty("signing.secretKeyRingFile")
        .map { file(it).isFile }
        .getOrElse(false)

    if (providers.gradleProperty("signingInMemoryKey").isPresent || hasSecretKeyRing) {
        signAllPublications()
    }
}
