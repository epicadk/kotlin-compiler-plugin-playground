plugins {
    kotlin("jvm")
    `java-library` // Apply the java-library plugin for publishing
}

dependencies {
    // Kotlin compiler embedding dependencies
    // Use the same Kotlin version as defined in the root build.gradle.kts
    val kotlinVersion = rootProject.extra["kotlin.version"] as String
    implementation(kotlin("compiler-embedding", kotlinVersion))
    implementation(kotlin("compiler-hosted", kotlinVersion))

    // Dependency on the annotations module
    api(project(":compiler-plugin-annotations"))
}

// Configure the compiler plugin
// This block is necessary for the compiler plugin to be discoverable by the Kotlin compiler
kotlin {
    sourceSets.main {
        dependencies {
            // This dependency is required for the compiler plugin to be applied
            // The artifact name should match the artifact name defined in the compiler-plugin module
            // and the group id should match the group id defined in the root build.gradle.kts
            compileOnly(project(":compiler-plugin"))
        }
    }
}
