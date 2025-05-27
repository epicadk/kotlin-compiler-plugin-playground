plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":compiler-plugin-annotations"))
    kotlinCompilerPluginClasspath(project(":compiler-plugin"))
}
