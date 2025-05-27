package com.epicadk.kotlin.compiler.plugin.playground

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

@OptIn(ExperimentalCompilerApi::class)
class MyComponentRegistrar() : CompilerPluginRegistrar() {

    override val supportsK2: Boolean
        get() = true

    override fun ExtensionStorage.registerExtensions(
        configuration: CompilerConfiguration
    ) {
        IrGenerationExtension.registerExtension(
            MyIrGenerationExtension()
        )
        AnalysisHandlerExtension.registerExtension(
            MyAnalysisHandlerExtension()
        )
    }
}
