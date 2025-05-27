package com.epicadk.kotlin.compiler.plugin.playground.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class MyAnnotation
