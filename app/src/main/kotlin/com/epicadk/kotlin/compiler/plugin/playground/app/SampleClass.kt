package com.epicadk.kotlin.compiler.plugin.playground.app

import com.epicadk.kotlin.compiler.plugin.playground.annotation.MyAnnotation

@MyAnnotation
class SampleClass {
    fun greet() {
        println("Hello from SampleClass!")
    }
}
