# Kotlin Compiler Plugin Playground - Context

This document serves as a living context for the Kotlin Compiler Plugin Playground project. It will contain important information, code snippets, and notes relevant to the current development phase. As tasks are completed and information becomes less critical, this document will be pruned.

## General Information

*   **Project Goal**: To demonstrate various features of Kotlin compiler plugins in a multi-module Gradle project.
*   **Current Phase**: Planning and initial setup.
*   **Kotlin Version**: (To be determined, will use the latest stable version unless specified otherwise)
*   **Gradle Version**: (To be determined, will use the latest stable version unless specified otherwise)

## Key Concepts for Compiler Plugins

*   **Frontend**: Deals with parsing, name resolution, and type checking.
*   **Backend (IR)**: Intermediate Representation (IR) is a tree-like structure representing the code after frontend processing. Compiler plugins often transform this IR.
*   **ComponentRegistrar**: The entry point for a compiler plugin, where extensions are registered.
*   **IrGenerationExtension**: An extension point for modifying the IR tree.
*   **Annotations**: Used to mark code elements for processing by the plugin.

## Initial Setup Notes

*   **Git Initialization**: Need to check if `.git` directory exists.
*   **Gradle Multi-Module Configuration**:
    *   `settings.gradle.kts`: `include("app", "compiler-plugin", "compiler-plugin-annotations")`
    *   `build.gradle.kts` (root): Common configurations, Kotlin version.
    *   `build.gradle.kts` (modules): Specific plugins and dependencies for each module.

## Troubleshooting

*   **Gradle Sync Issues**: Check `settings.gradle.kts` and `build.gradle.kts` for syntax errors or missing dependencies.
*   **Compiler Plugin Not Applied**: Ensure the plugin is correctly applied in the `app` module's `build.gradle.kts`.
*   **IR Transformation Errors**: Debug the IR transformation logic carefully.

## Next Steps (from tasks.md)

*   **Task 1.1: Initialize Git Repository**
*   **Task 1.2: Configure Multi-Module Project in `settings.gradle.kts`**
*   **Task 1.3: Create Module Directories and Basic `build.gradle.kts` files**
*   **Task 1.4: Configure `build.gradle.kts` for Modules**
