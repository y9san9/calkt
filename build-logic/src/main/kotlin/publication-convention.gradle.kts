plugins {
    id("org.gradle.maven-publish")
}

group = "app.meetacy.ksm"

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/meetacy/ksm")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications.withType<MavenPublication> {
        versionFromProperties { version ->
            this.version = version
        }
    }
}
