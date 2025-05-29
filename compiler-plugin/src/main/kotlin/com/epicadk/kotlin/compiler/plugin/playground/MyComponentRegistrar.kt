package com.epicadk.kotlin.compiler.plugin.playground

import com.epicadk.kotlin.compiler.plugin.playground.backend.MyIrGenerationExtension
import com.epicadk.kotlin.compiler.plugin.playground.fir.checkers.MyFirCheckerExtension
import com.epicadk.kotlin.compiler.plugin.playground.fir.checkers.declaration.MyDeclarationCheckers
import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.messageCollector
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirAnalysisHandlerExtension
import org.jetbrains.kotlin.fir.extensions.FirExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter
import org.jetbrains.kotlin.fir.extensions.FirExtensionService
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
class MyComponentRegistrar() : CompilerPluginRegistrar() {

    override val supportsK2: Boolean
        get() = true

    override fun ExtensionStorage.registerExtensions(
        configuration: CompilerConfiguration
    ) {

        FirExtensionRegistrarAdapter.registerExtension(MyFirExtensionRegistrar())
        IrGenerationExtension.registerExtension(
            MyIrGenerationExtension(configuration.messageCollector)
        )
        AnalysisHandlerExtension.registerExtension(
            MyAnalysisHandlerExtension()
        )
    }
}

class MyFirExtensionRegistrar : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +::MyFirCheckerExtension
    }

}
