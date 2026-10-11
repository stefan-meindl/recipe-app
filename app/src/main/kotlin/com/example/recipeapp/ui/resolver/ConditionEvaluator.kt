package com.example.recipeapp.ui.resolver

/**
 * Result of evaluating a condition expression.
 */
sealed class ConditionResult {
    object Pass : ConditionResult()
    object Fail : ConditionResult()
    object Invalid : ConditionResult()
}

/**
 * Evaluates condition expressions against a context map.
 *
 * Syntax:
 * - `key = value` — Equality check
 * - `key != value` — Inequality check
 * - `add:key` — Presence check (passes if key exists in context)
 * - `!add:key` — Absence check (passes if key does not exist in context)
 *
 * Array semantics: If the context value for the key is a `List`, the condition passes
 * only if the `value` matches **all** elements in the list (strict all-match).
 * Single values perform direct matches.
 *
 * @param condition The condition string to evaluate, or null (treated as Pass)
 * @param context The context map containing variation selections
 * @return ConditionResult.Pass, .Fail, or .Invalid
 */
object ConditionEvaluator {

    /**
     * Evaluate a single condition string against the context.
     *
     * @param condition The condition string (e.g. "diet=vegan", "add:tags")
     * @param context The context map (variation selections)
     * @return ConditionResult.Pass, .Fail, or .Invalid
     */
    fun evaluate(condition: String?, context: Map<String, Any?>): ConditionResult {
        if (condition == null || condition.trim().isEmpty()) {
            return ConditionResult.Pass
        }

        val parsed = parseCondition(condition)
        if (parsed == null) {
            return ConditionResult.Invalid
        }

        val (key, op, value) = parsed
        val fieldValue = context[key]

        return when (op) {
            "=" -> evaluateEquality(fieldValue, value)
            "!=" -> evaluateInequality(fieldValue, value)
            "add:" -> evaluatePresence(fieldValue)
            "!add:" -> evaluateAbsence(fieldValue)
            else -> ConditionResult.Invalid
        }
    }

    /**
     * Evaluate a list of conditions. All must pass (AND logic).
     * Returns Pass if the list is null or empty.
     * Returns Invalid if any condition is invalid.
     * Returns Fail if any condition fails.
     */
    fun evaluate(conditions: List<String>?, context: Map<String, Any?>): ConditionResult {
        if (conditions.isNullOrEmpty()) {
            return ConditionResult.Pass
        }

        var hasInvalid = false
        for (condition in conditions) {
            when (val result = evaluate(condition, context)) {
                is ConditionResult.Invalid -> {
                    hasInvalid = true
                }
                is ConditionResult.Fail -> {
                    return ConditionResult.Fail
                }
                is ConditionResult.Pass -> {
                    // continue
                }
            }
        }
        return if (hasInvalid) ConditionResult.Invalid else ConditionResult.Pass
    }

    private fun evaluateEquality(fieldValue: Any?, value: String): ConditionResult {
        if (fieldValue == null) return ConditionResult.Fail

        // Array semantics: if context value is a List, check all elements match
        return when (fieldValue) {
            is List<*> -> {
                if (fieldValue.all { it.toString() == value }) ConditionResult.Pass else ConditionResult.Fail
            }
            else -> {
                if (fieldValue.toString() == value) ConditionResult.Pass else ConditionResult.Fail
            }
        }
    }

    private fun evaluateInequality(fieldValue: Any?, value: String): ConditionResult {
        if (fieldValue == null) return ConditionResult.Pass

        // Array semantics: if context value is a List, check all elements differ
        return when (fieldValue) {
            is List<*> -> {
                if (fieldValue.all { it.toString() != value }) ConditionResult.Pass else ConditionResult.Fail
            }
            else -> {
                if (fieldValue.toString() != value) ConditionResult.Pass else ConditionResult.Fail
            }
        }
    }

    private fun evaluatePresence(fieldValue: Any?): ConditionResult {
        // Presence check: passes if key exists in context (fieldValue != null)
        return if (fieldValue != null) ConditionResult.Pass else ConditionResult.Fail
    }

    private fun evaluateAbsence(fieldValue: Any?): ConditionResult {
        // Absence check: passes if key does NOT exist in context
        return if (fieldValue == null) ConditionResult.Pass else ConditionResult.Fail
    }

    private fun parseCondition(condition: String): Triple<String, String, String>? {
        // Handle prefix operators first: add:key and !add:key
        if (condition.startsWith("add:")) {
            val key = condition.substring(4)
            if (key.isNotEmpty()) {
                return Triple(key, "add:", "")
            }
            return null
        }
        if (condition.startsWith("!add:")) {
            val key = condition.substring(5)
            if (key.isNotEmpty()) {
                return Triple(key, "!add:", "")
            }
            return null
        }

        // Try operators in order of specificity (longest first to avoid partial matches)
        val operators = listOf(
            "!=" to { field: String, value: String -> Triple(field, "!=", value) },
            "=" to { field: String, value: String -> Triple(field, "=", value) },
        )

        for ((op, factory) in operators) {
            val idx = condition.indexOf(op)
            if (idx > 0) {
                val field = condition.substring(0, idx)
                val value = condition.substring(idx + op.length)
                return try {
                    factory(field, value)
                } catch (_: Exception) {
                    return null
                }
            }
        }

        return null
    }
}
