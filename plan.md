# Kotlin Compiler Plugin Playground - Plan

This project aims to create a comprehensive sample demonstrating various features of Kotlin compiler plugins. It will be structured as a multi-module Gradle project, with each module focusing on a specific aspect or feature of compiler plugin development.

## Project Structure

The project will consist of the following modules:

*   `:app`: A simple Kotlin application module that will use the compiler plugin.
*   `:compiler-plugin`: The core compiler plugin module, containing the plugin's logic.
*   `:compiler-plugin-annotations`: A module for annotations used by the compiler plugin.
*   `:compiler-plugin-runtime`: (Optional, if needed) A module for runtime components of the plugin.

## Features to Demonstrate

The following features of Kotlin compiler plugins will be demonstrated:

1.  **Basic IR Transformation**: A simple transformation of the Intermediate Representation (IR) tree.
2.  **Annotation Processing**: How to read and react to custom annotations.
3.  **Synthetic Properties/Functions**: Adding new properties or functions to existing classes.
4.  **Message Generation**: Emitting custom compiler messages (warnings/errors).
5.  **Extension Points**: Demonstrating various extension points available in the compiler.
6.  **Source Code Generation**: Generating new Kotlin source files.

## Documentation

*   `plan.md`: This document, outlining the overall plan and project structure.
*   `tasks.md`: A detailed breakdown of implementation tasks, updated as progress is made.
*   `context.md`: A living document containing relevant information, code snippets, and troubleshooting notes. This will be pruned as tasks are completed and information becomes less critical.

## Workflow

1.  **Planning**: Define the project scope, structure, and features. (Current phase)
2.  **Setup**: Initialize Git, configure Gradle multi-module project.
3.  **Implementation**: Develop each compiler plugin feature incrementally.
4.  **Documentation**: Maintain `tasks.md` and `context.md` throughout the development process.
5.  **Verification**: After each significant step, check for project errors and commit changes to Git.

## Initial Setup Steps

1.  Check for existing Git repository and initialize if necessary.
2.  Modify `settings.gradle.kts` to include new modules.
3.  Modify `build.gradle.kts` for module dependencies and plugin application.
4.  Create initial `app`, `compiler-plugin`, and `compiler-plugin-annotations` modules.
