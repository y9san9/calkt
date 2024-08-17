import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.vanniktech.maven.publish")
}

group = "me.y9san9.calkt"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)

    pom {
        name = "calkt"
        description = "Kotlin library that supports parsing and calculating various expressions"
        url = "https://github.com/y9san9/calkt"

        licenses {
            license {
                name = "MIT"
                distribution = "repo"
                url = "https://github.com/y9san9/calkt/blob/main/LICENSE.md"
            }
        }

        developers {
            developer {
                id = "y9san9"
                name = "Alex Sokol"
                email = "y9san9@gmail.com"
            }
        }

        scm {
            connection ="scm:git:ssh://github.com/y9san9/calkt.git"
            developerConnection = "scm:git:ssh://github.com/y9san9/calkt.git"
            url = "https://github.com/y9san9/calkt"
        }
    }

    signAllPublications()
}
