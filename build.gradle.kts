plugins {
    kotlin("jvm") version "1.9.22" // Use a recent stable Kotlin version
}

allprojects {
    group = "com.epicadk"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/compiler/dev") // For compiler dependencies
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(kotlin("stdlib"))
    }
}
