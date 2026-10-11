package com.example.recipeapp.ui.resolver

/**
 * Sealed hierarchy of validation errors for the resolution engine.
 */
sealed class ValidationError {
    object InvalidServings : ValidationError() {
        override val message: String = "Servings must be at least 1"
    }

    data class MissingId(val id: String) : ValidationError() {
        override val message: String = "Missing ID: '$id' referenced in needs[] but not found in recipe"
    }

    data class CircularDependency(val path: List<String>) : ValidationError() {
        override val message: String = "Circular dependency detected: ${path.joinToString(" → ")}"
    }

    data class MissingPrerequisite(val stepId: String, val missingId: String) : ValidationError() {
        override val message: String = "MissingPrerequisite: step '$stepId' depends on '$missingId' which is not present after filtering"
    }

    abstract val message: String
}
