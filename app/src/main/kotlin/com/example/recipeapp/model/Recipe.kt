package com.example.recipeapp.model

// Placeholder model. The JSON recipe format is not defined yet, so expect this to change.

data class Ingredient(val name: String, val quantity: String)

data class Recipe(
    val id: String,
    val title: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
)
