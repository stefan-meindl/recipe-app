package com.example.recipeapp.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

/**
 * Tests for the v2 model introduced to close ISSUE 02 (recipe-app issue #6).
 *
 * Covers the two acceptance criteria from the issue:
 *  - a v2 sample deserializes cleanly with no missing-field exceptions, and
 *  - a legacy v1 sample surfaces a clear validation error instead of a silent partial success.
 */
class RecipeV2CodecTest {

    // A minimal-but-complete v2 recipe, shaped exactly like the Simmer design data.
    private val v2Sample = """
        {
          "id": "test-recipe",
          "title": "Test Recipe",
          "category": "Mains",
          "tags": ["Spicy", "Quick"],
          "time": 30,
          "difficulty": "Easy",
          "base": 2,
          "icon": "flame",
          "summary": "A test recipe.",
          "variations": [
            { "id": "spice", "kind": "swap", "label": "Heat",
              "options": [ { "v": "mild", "l": "Mild" }, { "v": "hot", "l": "Hot" } ],
              "def": "mild" }
          ],
          "methods": [],
          "ingredients": [
            { "id": "salt", "name": "Salt", "q": 1, "u": "tsp", "aisle": "Spices" },
            { "id": "oil", "name": "Oil", "q": 2, "u": "tbsp", "if": "spice=hot",
              "alt": [ ["spice=hot", 3] ] }
          ],
          "steps": [
            { "id": "mix", "ph": "prep", "t": "Mix", "d": 60, "b": "Add the {salt:salt}." },
            { "id": "cook", "ph": "cook", "t": "Cook", "d": 300, "needs": ["mix"], "b": "Heat it up." }
          ]
        }
    """.trimIndent()

    // A legacy v1 sample: root has preparationMethods and no v2 methods/ingredients/steps.
    private val v1LegacySample = """
        {
          "id": "legacy",
          "title": "Legacy Pancakes",
          "preparationMethods": [
            {
              "id": "stove",
              "displayName": "Stovetop",
              "ingredients": [
                { "name": "Flour", "quantity": "200 g" }
              ],
              "steps": [
                { "description": "Mix dry ingredients" }
              ]
            }
          ]
        }
    """.trimIndent()

    @Test
    fun v2SampleDeserializesCleanly() {
        val recipe = RecipeV2Codec.decodeFromString(v2Sample)

        assertEquals("test-recipe", recipe.id)
        assertEquals("Test Recipe", recipe.title)
        // "base" maps to servings.
        assertEquals(2, recipe.servings)
        assertEquals("Mains", recipe.category)
        assertEquals(30, recipe.timeMinutes)
        assertEquals("Easy", recipe.difficulty)
        assertEquals(listOf("Spicy", "Quick"), recipe.tags)
        assertEquals("A test recipe.", recipe.summary)
        assertEquals(1, recipe.variations.size)
        assertEquals(VariationKind.swap, recipe.variations[0].kind)
        assertEquals("spice", recipe.variations[0].id)
        assertEquals("mild", recipe.variations[0].def)
    }

    @Test
    fun v2StepsReadThePhPhaseKey() {
        // Regression: the design key for phase is "ph", not "phase".
        val recipe = RecipeV2Codec.decodeFromString(v2Sample)
        assertEquals(2, recipe.steps.size)
        assertEquals(StepPhase.prep, recipe.steps[0].phase)
        assertEquals(StepPhase.cook, recipe.steps[1].phase)
        assertEquals("Mix", recipe.steps[0].title)
        assertEquals(60, recipe.steps[0].durationSeconds)
        assertEquals(listOf("mix"), recipe.steps[1].needs)
    }

    @Test
    fun v2IngredientsReadNumericQtyAndUnit() {
        val recipe = RecipeV2Codec.decodeFromString(v2Sample)
        val salt = recipe.ingredients.first { it.id == "salt" }
        assertEquals("Salt", salt.name)
        assertEquals(1.0, salt.qty)
        assertEquals("tsp", salt.unit)
        assertEquals("Spices", salt.aisle)

        val oil = recipe.ingredients.first { it.id == "oil" }
        assertEquals("spice=hot", oil.condition)
    }

    @Test
    fun legacyV1SampleSurfacesClearError() {
        try {
            RecipeV2Codec.decodeFromString(v1LegacySample)
            fail("Expected a RecipeSchemaException for a legacy v1 sample")
        } catch (e: RecipeSchemaException) {
            // The error must name the legacy format and the offending key, not be a generic crash.
            assertTrue(
                "Error should mention legacy v1 format, was: ${e.message}",
                e.message!!.contains("legacy v1", ignoreCase = true),
            )
            assertTrue(
                "Error should mention preparationMethods, was: ${e.message}",
                e.message!!.contains("preparationMethods"),
            )
        }
    }

    @Test
    fun rootObjectMustBeAnObject() {
        try {
            RecipeV2Codec.decodeFromString("[1, 2, 3]")
            fail("Expected a RecipeSchemaException for a non-object root")
        } catch (e: RecipeSchemaException) {
            assertTrue(
                "Error should mention the root must be an object, was: ${e.message}",
                e.message!!.contains("object"),
            )
        }
    }

    // --- Edge cases added by the tester stage ---

    // Acceptance criterion: deserialization must be tolerant of unknown fields (don't crash on
    // legacy samples). A v2 sample carrying an extra, unrecognized root field must still decode.
    @Test
    fun unknownFieldsAreIgnored() {
        val extra = v2Sample.replace(
            "\"difficulty\": \"Easy\",\n",
            "\"difficulty\": \"Easy\",\n\"unknownRootField\": 42,\n\"nested\": { \"a\": [1, 2, 3] },\n",
        )
        val recipe = RecipeV2Codec.decodeFromString(extra)
        assertEquals("test-recipe", recipe.id)
        assertEquals("Easy", recipe.difficulty)
    }

    // A v2 sample that ALSO happens to carry a preparationMethods array must still decode as v2,
    // not be rejected as legacy. Legacy detection is keyed on the ABSENCE of v2 methods.
    @Test
    fun legacyDetectionRequiresMissingV2Methods() {
        val sample = """
            {
              "id": "both",
              "title": "Both",
              "methods": [],
              "ingredients": [],
              "steps": [],
              "preparationMethods": [ { "id": "stove", "displayName": "x" } ]
            }
        """.trimIndent()
        val recipe = RecipeV2Codec.decodeFromString(sample)
        assertEquals("both", recipe.id)
    }

    // A legacy v1 sample that lacks an id must still be rejected with a clear error, not crash
    // with a null-pointer / missing-field exception.
    @Test
    fun legacyV1WithoutIdStillSurfacesClearError() {
        val sample = """
            {
              "title": "No Id",
              "preparationMethods": [ { "id": "stove", "displayName": "x" } ]
            }
        """.trimIndent()
        try {
            RecipeV2Codec.decodeFromString(sample)
            fail("Expected a RecipeSchemaException for a legacy v1 sample")
        } catch (e: RecipeSchemaException) {
            assertTrue(
                "Error should mention legacy v1 format, was: ${e.message}",
                e.message!!.contains("legacy v1", ignoreCase = true),
            )
        }
    }

    // The `alt` per-condition override must round-trip its positional [condition, value] pair.
    @Test
    fun altConditionPairDecodesPositionally() {
        val recipe = RecipeV2Codec.decodeFromString(v2Sample)
        val oil = recipe.ingredients.first { it.id == "oil" }
        assertEquals(1, oil.alt.size)
        assertEquals("spice=hot", oil.alt[0].condition)
        assertEquals(3.0, oil.alt[0].value, 0.0001)
    }

    // A missing required field (id) must surface as a missing-field exception, not a silent partial
    // success.
    @Test
    fun missingRequiredFieldThrows() {
        val sample = v2Sample.replace("\"id\": \"test-recipe\",", "")
        try {
            RecipeV2Codec.decodeFromString(sample)
            fail("Expected an exception for a missing required field")
        } catch (e: Exception) {
            assertTrue(
                "Expected kotlinx.serialization missing-field error, was: ${e.javaClass.simpleName}",
                e.javaClass.simpleName == "MissingFieldException",
            )
        }
    }
}
