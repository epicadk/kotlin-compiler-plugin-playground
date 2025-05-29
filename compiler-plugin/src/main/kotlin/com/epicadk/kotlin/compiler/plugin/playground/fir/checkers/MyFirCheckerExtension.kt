package com.epicadk.kotlin.compiler.plugin.playground.fir.checkers

import com.epicadk.kotlin.compiler.plugin.playground.fir.checkers.declaration.MyDeclarationCheckers
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

class MyFirCheckerExtension(session: FirSession) : FirAdditionalCheckersExtension(session){
    override val declarationCheckers: DeclarationCheckers = MyDeclarationCheckers()

}