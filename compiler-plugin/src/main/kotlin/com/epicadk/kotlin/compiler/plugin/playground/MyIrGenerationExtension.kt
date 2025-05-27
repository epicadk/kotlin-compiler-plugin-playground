package com.epicadk.kotlin.compiler.plugin.playground

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

class MyIrGenerationExtension : IrGenerationExtension {
    override fun generateIrStuff(
        pluginContext: IrPluginContext,
        moduleFragment: IrModuleFragment
    ) {
        // Apply the IR transformer
        moduleFragment.transform(MyIrTransformer(pluginContext), null)
    }
}
