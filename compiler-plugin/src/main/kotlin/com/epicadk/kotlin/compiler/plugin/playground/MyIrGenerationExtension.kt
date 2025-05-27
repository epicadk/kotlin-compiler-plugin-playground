package com.epicadk.kotlin.compiler.plugin.playground

package com.epicadk.kotlin.compiler.plugin.playground

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.impl.IrFileImpl
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl
import org.jetbrains.kotlin.ir.declarations.impl.IrFunctionImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrExternalPackageFragmentSymbolImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl
import org.jetbrains.kotlin.ir.types.impl.make
import org.jetbrains.kotlin.ir.util.SYNTHETIC_OFFSET
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.descriptors.Modality
import org.jetbrains.kotlin.descriptors.Visibilities

class MyIrGenerationExtension : IrGenerationExtension {
    override fun generate(
        moduleFragment: IrModuleFragment,
        pluginContext: IrPluginContext
    ) {
        // Apply the IR transformer
        moduleFragment.transform(MyIrTransformer(pluginContext), null)

        // Generate a new file and add it to the module fragment
        val generatedFile = pluginContext.irFactory.createFile(
            fileEntry = pluginContext.fileEntry,
            fqName = FqName("com.epicadk.kotlin.compiler.plugin.playground.generated"),
            module = moduleFragment.descriptor
        ).also { file ->
            val generatedClass = pluginContext.irFactory.createClass(
                startOffset = SYNTHETIC_OFFSET,
                endOffset = SYNTHETIC_OFFSET,
                origin = org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB, // Or another appropriate origin
                name = Name.identifier("GeneratedClass"),
                kind = ClassKind.CLASS,
                modality = Modality.FINAL,
                visibility = Visibilities.PUBLIC
            ).also { klass ->
                klass.parent = file
                // Add a simple function to the generated class
                val generatedFunction = pluginContext.irFactory.createSimpleFunction(
                    startOffset = SYNTHETIC_OFFSET,
                    endOffset = SYNTHETIC_OFFSET,
                    origin = org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin.DEFINED,
                    name = Name.identifier("generatedFunction"),
                    visibility = Visibilities.PUBLIC,
                    isInline = false,
                    isExpect = false,
                    returnType = pluginContext.irBuiltIns.unitType,
                    modality = Modality.FINAL,
                    symbol = IrSimpleFunctionSymbolImpl()
                ).also { function ->
                    function.parent = klass
                    function.body = pluginContext.irFactory.createBlockBody(
                        startOffset = SYNTHETIC_OFFSET,
                        endOffset = SYNTHETIC_OFFSET,
                        statements = listOf(
                            // Add a println statement
                            IrCallImpl(
                                startOffset = SYNTHETIC_OFFSET,
                                endOffset = SYNTHETIC_OFFSET,
                                type = pluginContext.irBuiltIns.unitType,
                                symbol = pluginContext.referenceFunctions(FqName("kotlin.io.println")).single { it.owner.valueParameters.singleOrNull()?.type == pluginContext.irBuiltIns.stringType },
                                typeArgumentsCount = 0,
                                valueArgumentsCount = 1
                            ).apply {
                                putValueArgument(
                                    0,
                                    IrConstImpl.string(
                                        startOffset = SYNTHETIC_OFFSET,
                                        endOffset = SYNTHETIC_OFFSET,
                                        type = pluginContext.irBuiltIns.stringType,
                                        value = "Hello from generated code!"
                                    )
                                )
                            }
                        )
                    )
                }
                klass.declarations.add(generatedFunction)
            }
            file.declarations.add(generatedClass)
        }
        moduleFragment.files.add(generatedFile)
    }
}
