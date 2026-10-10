package com.example.recipeapp.ui.resolver

/**
 * Parses raw condition strings into [Condition] instances.
 *
 * Supported syntax:
 * - `key=value` → [Condition.Eq]
 * - `key!=value` → [Condition.NotEq]
 * - `key=add:value` → [Condition.Add]
 * - `key=!add:value` → [Condition.NotAdd]
 *
 * Returns [Condition.UnparseableCondition] on failure instead of throwing.
 */
object ConditionParser {

    /**
     * Parse a raw condition string.
     *
     * @param raw The raw condition string (e.g. "diet=vegan", "tags=add:spicy")
     * @return A [Condition] instance, or [Condition.UnparseableCondition] on failure.
     */
    fun parse(raw: String): Condition {
        // Try operators in order of specificity (longest first to avoid partial matches)
        val operators = listOf(
            "=add:" to { field: String, value: String -> Condition.Add(field, value) },
            "=!add:" to { field: String, value: String -> Condition.NotAdd(field, value) },
            "!=" to { field: String, value: String -> Condition.NotEq(field, value) },
            "=" to { field: String, value: String -> Condition.Eq(field, value) },
        )

        for ((op, factory) in operators) {
            val idx = raw.indexOf(op)
            if (idx > 0) {
                val field = raw.substring(0, idx)
                val value = raw.substring(idx + op.length)
                return try {
                    factory(field, value)
                } catch (_: Exception) {
                    Condition.UnparseableCondition(raw)
                }
            }
        }

        return Condition.UnparseableCondition(raw)
    }
}
