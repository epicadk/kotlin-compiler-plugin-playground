# Kotlin Compiler Plugin Playground

This project is a playground for experimenting with Kotlin compiler plugins.

## Building

Use the Gradle wrapper to build the project:

```bash
./gradlew build
```

## Running

To run the sample application, use the following command:

```bash
./gradlew run
```

## Compiler Plugin

This project includes a Kotlin compiler plugin and a corresponding annotation `@MyAnnotation`. The plugin processes code annotated with `@MyAnnotation`.

To use the annotation, add the `compiler-plugin-annotations` dependency to your module:

```kotlin
dependencies {
    implementation(project(":compiler-plugin-annotations"))
}
```

Then, you can apply the `@MyAnnotation` to classes, functions, or properties:

```kotlin
import com.epicadk.kotlin.compiler.plugin.playground.annotation.MyAnnotation

@MyAnnotation
class MyClass {
    @MyAnnotation
    fun myMethod() {
        // ...
    }

    @MyAnnotation
    val myProperty: String = "Hello"
}
```

The compiler plugin will process these annotated elements during compilation.
