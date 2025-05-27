plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":compiler-plugin-annotations"))
}

// Apply the compiler plugin
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
