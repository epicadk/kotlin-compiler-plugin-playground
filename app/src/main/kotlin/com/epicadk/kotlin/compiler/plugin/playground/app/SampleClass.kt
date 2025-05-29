package com.epicadk.kotlin.compiler.plugin.playground.app

import com.epicadk.kotlin.compiler.plugin.playground.annotation.MyAnnotation

@MyAnnotation
class SampleClass(val greeter: String) {
    fun greet() {
        println("Hello from $greeter!")
    }
}

fun main() {
    SampleClass("").greet()
}
