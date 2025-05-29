package com.epicadk.kotlin.compiler.plugin.playground.fir.checkers.declaration

import org.jetbrains.kotlin.descriptors.ClassKind
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.constructors
import org.jetbrains.kotlin.fir.declarations.utils.isAbstract

class MyDeclarationCheckers : DeclarationCheckers() {

    override val classCheckers: Set<FirClassChecker> = setOf(MyClassChecker())

}


class MyClassChecker : FirClassChecker(MppCheckerKind.Common) {
    override fun check(
        declaration: FirClass,
        context: CheckerContext,
        reporter: DiagnosticReporter
    ) {
        if (declaration.classKind == ClassKind.CLASS && !declaration.isAbstract) {
            val hasEmptyConstructor = declaration.constructors(context.session).any {
                it.valueParameterSymbols.isEmpty()
            }
            if (!hasEmptyConstructor) {
                reporter.reportOn(
                    declaration.source,
                    FirErrors.OTHER_ERROR_WITH_REASON,
                    "Class Must have at least one empty constructor",
                    context
                )
            }
        }
    }

}