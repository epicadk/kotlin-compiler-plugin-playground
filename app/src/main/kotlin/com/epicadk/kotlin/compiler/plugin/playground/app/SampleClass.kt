package com.epicadk.kotlin.compiler.plugin.playground.app

class SampleClass(val greeter: String) {
    fun greet() {
        println("Hello from $greeter!")
    }
}

fun main() {
    SampleClass("").greet()
}
