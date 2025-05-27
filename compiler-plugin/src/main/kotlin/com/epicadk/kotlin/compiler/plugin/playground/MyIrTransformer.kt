package com.epicadk.kotlin.compiler.plugin.playground

import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.isUnit
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class MyIrTransformer(private val pluginContext: IrPluginContext) : IrElementTransformerVoid() {

    override fun visitFile(declaration: IrFile): IrFile {
        // Find the main function in the app module
        val mainFunction = declaration.declarations.find {
            it is IrSimpleFunction && it.name.asString() == "main"
        } as? IrSimpleFunction

        if (mainFunction != null) {
            // Add a print statement at the beginning of the main function
            val oldBody = mainFunction.body
            if (oldBody != null) {
                val newBody = pluginContext.irFactory.createBlockBody(
                    oldBody.startOffset,
                    oldBody.endOffset
                )

                // Create a call to println
                val printlnFunction = pluginContext.referenceFunctions(
                    FqName("kotlin.io.println")
                ).singleOrNull {
                    it.owner.valueParameters.size == 1 && it.owner.valueParameters[0].type.isString()
                }?.owner ?: error("println not found")

                val printCall = IrCallImpl(
                    SYNTHETIC_OFFSET, SYNTHETIC_OFFSET,
                    printlnFunction.returnType,
                    printlnFunction.symbol,
                    typeArgumentsCount = 0,
                    valueArgumentsCount = 1,
                    origin = IrStatementOrigin.IR_TEMPORARY_VARIABLE
                ).apply {
                    putValueArgument(
                        0,
                        IrConstImpl.string(
                            SYNTHETIC_OFFSET, SYNTHETIC_OFFSET,
                            pluginContext.irBuiltIns.stringType,
                            "Hello from compiler plugin!"
                        )
                    )
                }

                newBody.statements.add(printCall)
                newBody.statements.addAll(oldBody.statements)
                mainFunction.body = newBody
            }
        }

        return super.visitFile(declaration)
    }

    // Helper function to check if a type is String
    private fun IrType.isString(): Boolean {
        return this == pluginContext.irBuiltIns.stringType
    }
}
