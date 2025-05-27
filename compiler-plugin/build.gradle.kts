plugins {
    kotlin("jvm") version "2.1.0"
}

dependencies {
    // Kotlin compiler embedding dependencies
    // Use the same Kotlin version as defined in the root build.gradle.kts
    compileOnly(kotlin("compiler-embeddable"))
    api(project(":compiler-plugin-annotations"))
}
