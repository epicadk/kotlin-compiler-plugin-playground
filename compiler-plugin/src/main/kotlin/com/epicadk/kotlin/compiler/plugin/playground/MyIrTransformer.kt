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
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.expressions.impl.*
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class MyIrTransformer(private val pluginContext: IrPluginContext) : IrElementTransformerVoid() {

    private val myAnnotationFqName = FqName("com.epicadk.kotlin.compiler.plugin.playground.annotation.MyAnnotation")

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

    override fun visitClass(declaration: IrClass): IrStatement {
        if (declaration.hasAnnotation(myAnnotationFqName)) {
            println("Compiler Plugin: Found MyAnnotation on class ${declaration.name}")

            // Add a synthetic property
            val syntheticProperty = pluginContext.irFactory.createProperty(
                startOffset = SYNTHETIC_OFFSET,
                endOffset = SYNTHETIC_OFFSET,
                origin = IrDeclarationOrigin.SYNTHETIC_PROPERTY,
                name = Name.identifier("syntheticProperty"),
                visibility = declaration.visibility,
                isVar = false,
                isConst = false,
                isLateinit = false,
                isDelegated = false
            ).apply {
                parent = declaration
                backingField = pluginContext.irFactory.createField(
                    startOffset = SYNTHETIC_OFFSET,
                    endOffset = SYNTHETIC_OFFSET,
                    origin = IrDeclarationOrigin.SYNTHETIC_PROPERTY_BACKING_FIELD,
                    name = name,
                    visibility = visibility,
                    isFinal = true,
                    isExternal = false,
                    isStatic = false,
                    type = pluginContext.irBuiltIns.stringType // Type of the synthetic property
                ).apply {
                    parent = declaration
                }
                getter = pluginContext.irFactory.createSimpleFunction(
                    startOffset = SYNTHETIC_OFFSET,
                    endOffset = SYNTHETIC_OFFSET,
                    origin = IrDeclarationOrigin.SYNTHETIC_ACCESSOR,
                    name = Name.identifier("get${name.asString().capitalize()}"),
                    visibility = visibility,
                    isInline = false,
                    isExpect = false,
                    returnType = backingField!!.type,
                    modality = Modality.FINAL,
                    symbol = pluginContext.irFactory.createSimpleFunctionSymbol()
                ).apply {
                    parent = declaration
                    body = pluginContext.irFactory.createBlockBody(
                        startOffset = SYNTHETIC_OFFSET,
                        endOffset = SYNTHETIC_OFFSET,
                        statements = listOf(
                            IrReturnImpl(
                                startOffset = SYNTHETIC_OFFSET,
                                endOffset = SYNTHETIC_OFFSET,
                                type = returnType,
                                returnTargetSymbol = symbol,
                                value = IrGetFieldImpl(
                                    startOffset = SYNTHETIC_OFFSET,
                                    endOffset = SYNTHETIC_OFFSET,
                                    symbol = backingField!!.symbol,
                                    type = backingField!!.type,
                                    origin = IrStatementOrigin.GET_PROPERTY
                                ).apply {
                                    receiver = IrGetValueImpl(
                                        startOffset = SYNTHETIC_OFFSET,
                                        endOffset = SYNTHETIC_OFFSET,
                                        symbol = declaration.thisReceiver!!.symbol
                                    )
                                }
                            )
                        )
                    )
                }
            }
            declaration.declarations.add(syntheticProperty)
        }
        return super.visitClass(declaration)
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction): IrStatement {
        if (declaration.hasAnnotation(myAnnotationFqName)) {
            println("Compiler Plugin: Found MyAnnotation on function ${declaration.name}")
            // TODO: Add IR transformation logic for annotated functions
        }
        return super.visitSimpleFunction(declaration)
    }

    override fun visitProperty(declaration: IrProperty): IrStatement {
        if (declaration.hasAnnotation(myAnnotationFqName)) {
            println("Compiler Plugin: Found MyAnnotation on property ${declaration.name}")
            // TODO: Add IR transformation logic for annotated properties
        }
        return super.visitProperty(declaration)
    }

    // Helper function to check if a type is String
    private fun IrType.isString(): Boolean {
        return this == pluginContext.irBuiltIns.stringType
    }

    // Helper function to check for annotation
    private fun IrAnnotationContainer.hasAnnotation(fqName: FqName): Boolean {
        return annotations.any {
            it.type.classifierOrNull?.owner?.fqNameWhenAvailable == fqName
        }
    }
}
