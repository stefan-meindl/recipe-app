package com.example.recipeapp.domain.util

import junit.framework.TestCase.assertEquals
import org.junit.Test

class RecipeFormatterTest {

    // ---- fmtQty tests ----

    @Test
    fun testFractionHalf() {
        assertEquals("½ tsp", RecipeFormatter.fmtQty(0.5, "tsp"))
    }

    @Test
    fun testUnitRolloverFraction() {
        assertEquals("1 ½ kg", RecipeFormatter.fmtQty(1500.0, "g"))
        assertEquals("2 l", RecipeFormatter.fmtQty(2000.0, "ml"))
    }

    @Test
    fun testUnitRolloverQuarter() {
        assertEquals("1 ¼ kg", RecipeFormatter.fmtQty(1250.0, "g"))
    }

    @Test
    fun testRounding() {
        assertEquals("¼ tsp", RecipeFormatter.fmtQty(0.2, "tsp"))
        assertEquals("¾ tsp", RecipeFormatter.fmtQty(0.8, "tsp"))
        assertEquals("1¼ tsp", RecipeFormatter.fmtQty(1.2, "tsp"))
    }

    @Test
    fun testPluralization() {
        assertEquals("1 clove", RecipeFormatter.fmtQty(1.0, "clove"))
        assertEquals("2 cloves", RecipeFormatter.fmtQty(2.0, "clove"))
        assertEquals("3 boxes", RecipeFormatter.fmtQty(3.0, "box"))
    }

    @Test
    fun testZero() {
        assertEquals("0 g", RecipeFormatter.fmtQty(0.0, "g"))
    }

    // ---- fmtTime tests ----

    @Test
    fun testUnder60() {
        assertEquals("30 min", RecipeFormatter.fmtTime(30))
    }

    @Test
    fun testExactHour() {
        assertEquals("1 h", RecipeFormatter.fmtTime(60))
    }

    @Test
    fun testMixed() {
        assertEquals("1 h 35 min", RecipeFormatter.fmtTime(95))
    }

    @Test
    fun testZeroTime() {
        assertEquals("0 min", RecipeFormatter.fmtTime(0))
    }
}
