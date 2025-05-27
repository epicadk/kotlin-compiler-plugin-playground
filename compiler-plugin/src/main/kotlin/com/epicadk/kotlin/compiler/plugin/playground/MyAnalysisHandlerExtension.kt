package com.epicadk.kotlin.compiler.plugin.playground

import org.jetbrains.kotlin.analyzer.AnalysisResult
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.container.ComponentManager
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.jvm.extensions.AnalysisHandlerExtension

class MyAnalysisHandlerExtension : AnalysisHandlerExtension {
    override fun analysisCompleted(
        project: Project,
        module: ComponentManager,
        bindingTrace: BindingTrace,
        files: Collection<KtFile>
    ): AnalysisResult? {
        println("Compiler Plugin: Analysis completed!")
        // TODO: Add some analysis logic here
        return null // Return null to indicate that the analysis result should not be modified
    }
}
