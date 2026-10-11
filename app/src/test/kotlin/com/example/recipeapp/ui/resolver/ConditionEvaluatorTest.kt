package com.example.recipeapp.ui.resolver

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class ConditionEvaluatorTest {

    // ==================== Basic operators ====================

    @Test
    fun evaluateEqualityPasses() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("diet=vegan", context))
    }

    @Test
    fun evaluateEqualityFails() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("diet=omnivore", context))
    }

    @Test
    fun evaluateEqualityFailsForMissingKey() {
        val context: Map<String, Any?> = mapOf("flavor" to "spicy")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("diet=vegan", context))
    }

    @Test
    fun evaluateInequalityPasses() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("diet!=omnivore", context))
    }

    @Test
    fun evaluateInequalityFails() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("diet!=vegan", context))
    }

    @Test
    fun evaluateInequalityPassesForMissingKey() {
        val context: Map<String, Any?> = mapOf("flavor" to "spicy")
        // Missing key → null != "vegan" → Pass
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("diet!=vegan", context))
    }

    // add: checks if the KEY exists in context (regardless of value)
    @Test
    fun evaluateAddPasses() {
        val context: Map<String, Any?> = mapOf("tags" to "spicy")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("add:tags", context))
    }

    @Test
    fun evaluateAddFails() {
        val context: Map<String, Any?> = mapOf("tags" to "spicy")
        // Key "spicy" does not exist as a key in context
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("add:spicy", context))
    }

    @Test
    fun evaluateAddFailsForMissingKey() {
        val context: Map<String, Any?> = mapOf("flavor" to "spicy")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("add:diet", context))
    }

    // !add: checks if the KEY does NOT exist in context
    @Test
    fun evaluateAbsencePasses() {
        val context: Map<String, Any?> = mapOf("flavor" to "spicy")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("!add:diet", context))
    }

    @Test
    fun evaluateAbsenceFails() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("!add:diet", context))
    }

    @Test
    fun evaluateAbsencePassesForMissingKey() {
        val context: Map<String, Any?> = mapOf("flavor" to "spicy")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("!add:nonexistent", context))
    }

    // ==================== Array semantics (List values) ====================

    @Test
    fun evaluateEqualityAllMatchList() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("spicy", "vegetarian"))
        // For a List value, equality checks if all elements match the value
        // "spicy" != "vegetarian", so this should fail
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("tags=vegetarian", context))
    }

    @Test
    fun evaluateEqualityAllMatchListAllSame() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("vegan", "vegan"))
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("tags=vegan", context))
    }

    @Test
    fun evaluateInequalityAllMatchList() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("spicy", "sweet"))
        // For a List value, inequality checks if all elements differ from the value
        // "spicy" != "vegetarian" AND "sweet" != "vegetarian" → Pass
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("tags!=vegetarian", context))
    }

    @Test
    fun evaluateInequalityAllMatchListOneMatch() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("spicy", "vegetarian"))
        // "spicy" != "vegetarian" but "vegetarian" == "vegetarian" → Fail
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("tags!=vegetarian", context))
    }

    // For add: and !add: with List values, it's still just a presence/absence check on the key
    @Test
    fun evaluateAddWithListValue() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("spicy", "vegetarian"))
        // Presence check: key "tags" exists → Pass
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("add:tags", context))
    }

    @Test
    fun evaluateAbsenceWithListValue() {
        val context: Map<String, Any?> = mapOf("tags" to listOf("spicy", "vegetarian"))
        // Absence check: key "tags" exists → Fail
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate("!add:tags", context))
    }

    // ==================== Edge cases ====================

    @Test
    fun evaluateNullConditionPasses() {
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate(null as String?, emptyMap()))
    }

    @Test
    fun evaluateEmptyConditionPasses() {
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("", emptyMap()))
    }

    @Test
    fun evaluateWhitespaceConditionPasses() {
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("   ", emptyMap()))
    }

    @Test
    fun evaluateMalformedConditionReturnsInvalid() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        assertEquals(ConditionResult.Invalid, ConditionEvaluator.evaluate("malformed_no_operator", context))
    }

    @Test
    fun evaluateMissingOperatorReturnsInvalid() {
        assertEquals(ConditionResult.Invalid, ConditionEvaluator.evaluate("just_key", emptyMap()))
    }

    @Test
    fun evaluateEmptyAddKeyReturnsInvalid() {
        // "add:" with empty key → invalid
        assertEquals(ConditionResult.Invalid, ConditionEvaluator.evaluate("add:", emptyMap()))
    }

    @Test
    fun evaluateEmptyNotAddKeyReturnsInvalid() {
        // "!add:" with empty key → invalid
        assertEquals(ConditionResult.Invalid, ConditionEvaluator.evaluate("!add:", emptyMap()))
    }

    // ==================== List evaluation (AND logic) ====================

    @Test
    fun evaluateAllPassesForEmptyList() {
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate(emptyList<String>(), mapOf("diet" to "vegan")))
    }

    @Test
    fun evaluateAllPassesForNullList() {
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate(null as List<String>?, mapOf("diet" to "vegan")))
    }

    @Test
    fun evaluateAllPassesWhenAllConditionsPass() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan", "tags" to "spicy")
        val conditions = listOf("diet=vegan", "add:tags")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate(conditions, context))
    }

    @Test
    fun evaluateAllFailsWhenOneConditionFails() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan", "tags" to "spicy")
        val conditions = listOf("diet=vegan", "add:nonexistent")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate(conditions, context))
    }

    @Test
    fun evaluateAllReturnsInvalidWhenOneConditionInvalid() {
        val context: Map<String, Any?> = mapOf("diet" to "vegan")
        val conditions = listOf("diet=vegan", "malformed_no_operator")
        assertEquals(ConditionResult.Invalid, ConditionEvaluator.evaluate(conditions, context))
    }

    @Test
    fun evaluateAllFailsBeforeCheckingInvalid() {
        // Fail takes precedence: if any condition fails, return Fail immediately
        val context: Map<String, Any?> = mapOf("diet" to "omnivore")
        val conditions = listOf("diet=vegan", "malformed_no_operator")
        assertEquals(ConditionResult.Fail, ConditionEvaluator.evaluate(conditions, context))
    }

    // ==================== String value comparisons ====================

    @Test
    fun evaluateEqualityWithNumericString() {
        val context: Map<String, Any?> = mapOf("count" to "5")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("count=5", context))
    }

    @Test
    fun evaluateEqualityWithEmptyValue() {
        val context: Map<String, Any?> = mapOf("note" to "")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("note=", context))
    }

    @Test
    fun evaluateInequalityWithDifferentValue() {
        val context: Map<String, Any?> = mapOf("note" to "hello")
        // note!=world → hello != world → Pass
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("note!=world", context))
    }

    // ==================== Special characters ====================

    @Test
    fun evaluateEqualityWithSpecialCharacters() {
        val context: Map<String, Any?> = mapOf("path" to "/home/user")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("path=/home/user", context))
    }

    @Test
    fun evaluateInequalityWithSpecialCharacters() {
        val context: Map<String, Any?> = mapOf("path" to "/home/user")
        assertEquals(ConditionResult.Pass, ConditionEvaluator.evaluate("path!=/tmp", context))
    }
}
