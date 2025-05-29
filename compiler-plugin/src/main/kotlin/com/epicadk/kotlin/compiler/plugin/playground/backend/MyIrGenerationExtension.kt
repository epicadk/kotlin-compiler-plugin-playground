package com.epicadk.kotlin.compiler.plugin.playground.backend

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.isNullableAny
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.StandardClassIds

/**
 * An example of an IrGenerationExtension.
 *
 * This extension demonstrates:
 * 1. Accessing plugin context.
 * 2. Traversing IR declarations (specifically functions).
 * 3. Modifying IR: Adding a println call at the beginning of each function.
 *
 * To use this in a real plugin, you would register it in your CompilerPluginRegistrar.
 */
class MyIrGenerationExtension(private val messageCollector: MessageCollector) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        messageCollector.report(
            CompilerMessageSeverity.INFO,
            "MyIrGenerationExtension: Starting IR processing for module ${moduleFragment.name}"
        )
        val printlnFunSymbol = pluginContext.referenceFunctions(
            CallableId(
                packageName = StandardClassIds.BASE_KOTLIN_PACKAGE.child(Name.identifier("io")),
                callableName = Name.identifier("println")
            )
        )
            .firstOrNull {
                val parameters = it.owner.valueParameters
                parameters.size == 1 && parameters[0].type.isNullableAny()
            }

        if (printlnFunSymbol == null) {
            messageCollector.report(
                CompilerMessageSeverity.WARNING,
                "MyIrGenerationExtension: kotlin.io.println(Any?) function not found. Cannot add log statements."
            )
            return
        }

        moduleFragment.transformChildrenVoid(
            FunctionBodyTransformer(
                pluginContext,
                messageCollector,
                printlnFunSymbol
            )
        )

        messageCollector.report(
            CompilerMessageSeverity.INFO,
            "MyIrGenerationExtension: Finished IR processing for module ${moduleFragment.name}"
        )
    }
}

/**
 * An IR transformer that visits functions and adds a println call at the beginning of their bodies.
 */
private class FunctionBodyTransformer(
    private val pluginContext: IrPluginContext,
    private val messageCollector: MessageCollector,
    private val printlnSymbol: IrSimpleFunctionSymbol
) : IrElementTransformerVoidWithContext() {

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        val body = declaration.body
        if (body == null || declaration.isExternal) {
            return super.visitFunctionNew(declaration)
        }

        messageCollector.report(
            CompilerMessageSeverity.LOGGING,
            "MyIrGenerationExtension: Visiting function: ${declaration.name.asString()} in ${currentFile.name}"
        )

        declaration.body = DeclarationIrBuilder(pluginContext, declaration.symbol).irBlockBody(
            declaration.startOffset,
            declaration.endOffset
        ) {
            val message = "MyIrGenerationExtension: Entered function ${declaration.name.asString()}"
            val printlnCall = irCall(printlnSymbol, pluginContext.irBuiltIns.unitType).apply {
                putValueArgument(0, irString(message))
            }
            +printlnCall
            +body.statements
        }
        messageCollector.report(
            CompilerMessageSeverity.INFO,
            "MyIrGenerationExtension: Added entry log to function: ${declaration.name.asString()}"
        )

        return super.visitFunctionNew(declaration)
    }
}