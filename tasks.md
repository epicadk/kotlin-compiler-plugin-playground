# Kotlin Compiler Plugin Playground - Tasks

This document outlines the detailed tasks for setting up and developing the Kotlin Compiler Plugin Playground project.

## Phase 1: Project Setup

*   **Task 1.1: Initialize Git Repository**
    *   Check if a Git repository exists in the project root.
    *   If not, initialize a new Git repository (`git init`).
    *   Add initial files to Git (`git add .`).
    *   Perform the first commit (`git commit -m "Initial project setup"`).
    *   *Verification*: `git status` should show a clean working tree.

*   **Task 1.2: Configure Multi-Module Project in `settings.gradle.kts`**
    *   Add the following modules to `settings.gradle.kts`:
        *   `:app`
        *   `:compiler-plugin`
        *   `:compiler-plugin-annotations`
    *   *Verification*: Gradle sync should complete without errors.

*   **Task 1.3: Create Module Directories and Basic `build.gradle.kts` files**
    *   Create directories for each new module:
        *   `app/src/main/kotlin`
        *   `compiler-plugin/src/main/kotlin`
        *   `compiler-plugin-annotations/src/main/kotlin`
    *   Create a basic `build.gradle.kts` file in each new module directory.
    *   *Verification*: Project structure should reflect the new modules.

*   **Task 1.4: Configure `build.gradle.kts` for Modules**
    *   **Root `build.gradle.kts`**:
        *   Apply common Kotlin plugins.
        *   Define common dependencies.
    *   **`:app` module `build.gradle.kts`**:
        *   Apply Kotlin JVM plugin.
        *   Add dependency on `:compiler-plugin-annotations`.
        *   Apply the `:compiler-plugin` as a compiler plugin.
    *   **`:compiler-plugin` module `build.gradle.kts`**:
        *   Apply Kotlin JVM plugin.
        *   Apply `java-library` plugin.
        *   Add Kotlin compiler embedding dependencies.
        *   Add dependency on `:compiler-plugin-annotations`.
        *   Configure the compiler plugin.
    *   **`:compiler-plugin-annotations` module `build.gradle.kts`**:
        *   Apply Kotlin JVM plugin.
    *   *Verification*: Gradle sync should complete without errors. `get_project_problems` should return an empty list.

## Phase 2: Compiler Plugin Feature Implementation

*   **Task 2.1: Implement Basic IR Transformation**
    *   Create a simple IR visitor/transformer in the `:compiler-plugin` module.
    *   Apply a basic transformation (e.g., print a message during compilation, or modify a simple expression).
    *   *Verification*: Compile `:app` and observe the effect of the transformation.

*   **Task 2.2: Implement Annotation Processing**
    *   Define a custom annotation in `:compiler-plugin-annotations`.
    *   Implement a compiler plugin component to detect and process this annotation.
    *   *Verification*: Apply the annotation in `:app` and verify the plugin reacts as expected.

*   **Task 2.3: Implement Synthetic Properties/Functions**
    *   Add a synthetic property or function to a class in `:app` using the compiler plugin.
    *   *Verification*: Access the synthetic member from `:app` code.

*   **Task 2.4: Implement Message Generation**
    *   Add logic to the compiler plugin to emit custom warnings or errors based on certain code patterns.
    *   *Verification*: Introduce the code pattern in `:app` and verify the custom message appears during compilation.

*   **Task 2.5: Explore Extension Points**
    *   Demonstrate usage of various compiler extension points (e.g., `AnalysisHandlerExtension`, `ComponentRegistrar`).
    *   *Verification*: Observe the behavior of the extensions during compilation.

*   **Task 2.6: Implement Source Code Generation**
    *   Generate a new Kotlin source file during compilation based on some input (e.g., an annotation or a specific class).
    *   *Verification*: Verify the generated file exists and is compiled with the `:app` module.

## Phase 3: Documentation Refinement and Cleanup

*   **Task 3.1: Review and Refine Documentation**
    *   Ensure `plan.md`, `tasks.md`, and `context.md` are up-to-date and comprehensive.
    *   Add in-depth explanations for each demonstrated feature.
*   **Task 3.2: Final Verification**
    *   Run all tests and ensure the project compiles without errors or warnings.
    *   *Verification*: `get_project_problems` should return an empty list.
