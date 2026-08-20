tasks {
    register("printVersion") {
        group = "CI"

        doFirst {
            println(versionFromProperties())
        }
    }
}
