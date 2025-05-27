package com.epicadk.kotlin.compiler.plugin.playground

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
class MyCommandLineProcessor : CommandLineProcessor {
    override val pluginId: String = "com.epicadk.plugin"
    override val pluginOptions: Collection<AbstractCliOption> = listOf()
}