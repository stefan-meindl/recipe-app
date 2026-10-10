package com.example.recipeapp.ui.resolver

/**
 * Sealed hierarchy of condition types for the resolution engine.
 *
 * - `Eq(key, value)` — field must equal value
 * - `NotEq(key, value)` — field must not equal value
 * - `Add(key)` — value must be present in a comma-separated field
 * - `NotAdd(key)` — value must NOT be present in a comma-separated field
 * - `UnparseableCondition` — returned when the raw string cannot be parsed
 */
sealed interface Condition {
    /** Evaluate this condition against a context map (variation selections). */
    fun evaluate(context: Map<String, Any>): Boolean

    data class Eq(val key: String, val value: String) : Condition {
        override fun evaluate(context: Map<String, Any>): Boolean {
            return context[key]?.toString() == value
        }
    }

    data class NotEq(val key: String, val value: String) : Condition {
        override fun evaluate(context: Map<String, Any>): Boolean {
            return context[key]?.toString() != value
        }
    }

    data class Add(val key: String, val value: String) : Condition {
        override fun evaluate(context: Map<String, Any>): Boolean {
            val fieldValue = context[key]?.toString() ?: return false
            return fieldValue.split(",").map { it.trim() }.contains(value)
        }
    }

    data class NotAdd(val key: String, val value: String) : Condition {
        override fun evaluate(context: Map<String, Any>): Boolean {
            val fieldValue = context[key]?.toString() ?: return true
            return fieldValue.split(",").map { it.trim() }.none { it == value }
        }
    }

    /** Returned when the raw condition string cannot be parsed. */
    data class UnparseableCondition(val raw: String) : Condition {
        override fun evaluate(context: Map<String, Any>): Boolean = false
    }
}

/**
 * Evaluate a list of conditions. All must match (AND logic).
 * Returns true if the list is null or empty.
 */
fun List<Condition>.evaluateAll(context: Map<String, Any>): Boolean {
    return all { it.evaluate(context) }
}
