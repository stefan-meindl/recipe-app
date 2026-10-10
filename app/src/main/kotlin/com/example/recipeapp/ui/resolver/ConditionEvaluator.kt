package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.IngredientVariant

/**
 * Evaluates condition expressions on ingredients and steps.
 * Condition syntax: "field=value", "field!=value", "field=add:value", "field=!add:value".
 * Arrays: all-match AND logic. Unknown fields → false. Unknown operators → throw.
 */
object ConditionEvaluator {

    fun evaluateCondition(condition: String, variationSelections: Map<String, String>): Boolean {
        val (field, op, value) = parseCondition(condition)
        val fieldValue = variationSelections[field] ?: return false
        
        return when (op) {
            "=" -> fieldValue == value
            "!=" -> fieldValue != value
            "add:" -> {
                val values = fieldValue.split(",").map { it.trim() }
                value in values
            }
            "!add:" -> {
                val values = fieldValue.split(",").map { it.trim() }
                value !in values
            }
            else -> throw IllegalArgumentException("Unknown operator in condition: $condition")
        }
    }

    fun evaluateConditions(conditions: List<String>?, variationSelections: Map<String, String>): Boolean {
        return conditions?.all { evaluateCondition(it, variationSelections) } ?: true
    }

    private fun parseCondition(condition: String): Triple<String, String, String> {
        val operators = listOf("=add:", "=!add:", "!=", "=")
        for (op in operators) {
            val idx = condition.indexOf(op)
            if (idx > 0) {
                val field = condition.substring(0, idx)
                val value = condition.substring(idx + op.length)
                val normalizedOp = when (op) {
                    "=add:" -> "add:"
                    "=!add:" -> "!add:"
                    else -> op
                }
                return Triple(field, normalizedOp, value)
            }
        }
        throw IllegalArgumentException("Malformed condition: '$condition'")
    }
}
