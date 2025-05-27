plugins {
    kotlin("jvm") version "2.1.21" // Use a recent stable Kotlin version
}

allprojects {
    group = "com.epicadk"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(kotlin("stdlib"))
    }
}
