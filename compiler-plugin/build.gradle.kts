plugins {
    kotlin("jvm") version "2.1.21"
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
}

dependencies {
    compileOnly(kotlin("compiler-embeddable"))
    api(project(":compiler-plugin-annotations"))
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.2.0")
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")

}
