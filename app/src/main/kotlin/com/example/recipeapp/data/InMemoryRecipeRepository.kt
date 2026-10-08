package com.example.recipeapp.data

import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step

/** Hard-coded sample data so the placeholder UI has something to show. */
class InMemoryRecipeRepository : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> = sampleRecipes
}

private val sampleRecipes = listOf(
    Recipe(
        id = "pancakes",
        title = "Pancakes",
        prepTimeMinutes = 10,
        cookTimeMinutes = 15,
        servings = 4,
        tags = listOf("breakfast", "vegetarian"),
        images = listOf("https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445?w=800"),
        notes = "Best served with maple syrup or fresh berries.",
        preparationMethods = listOf(
            PreparationMethod(
                id = "stove",
                displayName = "Stovetop",
                ingredients = listOf(
                    Ingredient("Flour", "200 g", alternatives = listOf("gluten-free flour"), tips = listOf("Sift for fluffier pancakes")),
                    Ingredient("Milk", "300 ml", alternatives = listOf("oat milk", "almond milk")),
                    Ingredient("Eggs", "2", tips = listOf("Room temperature mixes better")),
                    Ingredient("Butter", "20 g", alternatives = listOf("vegetable oil (1 tbsp)")),
                    Ingredient("Sugar", "2 tbsp"),
                    Ingredient("Baking powder", "1 tsp"),
                    Ingredient("Salt", "pinch"),
                ),
                steps = listOf(
                    Step("Mix dry ingredients: flour, sugar, baking powder, and salt in a large bowl.", durationMinutes = 2, tips = listOf("Sift together for even distribution")),
                    Step("Make a well in the center and pour in milk, eggs, and melted butter.", durationMinutes = 1),
                    Step("Whisk until just combined — lumps are fine. Don't overmix.", durationMinutes = 1, tips = listOf("A few lumps ensure tender pancakes")),
                    Step("Heat a non-stick pan or griddle over medium heat. Add a small knob of butter.", durationMinutes = 2),
                    Step("Pour a ladle of batter (about ¼ cup per pancake). Cook until bubbles form on surface.", durationMinutes = 3, tips = listOf("Bubbles that don't pop mean it's time to flip")),
                    Step("Flip and cook until golden brown, about 1-2 minutes more.", durationMinutes = 2),
                ),
            ),
            PreparationMethod(
                id = "microwave",
                displayName = "Microwave Mug Pancake",
                ingredients = listOf(
                    Ingredient("Flour", "4 tbsp"),
                    Ingredient("Milk", "3 tbsp"),
                    Ingredient("Egg", "1"),
                    Ingredient("Butter", "1 tsp", alternatives = listOf("vegetable oil")),
                    Ingredient("Sugar", "1 tbsp"),
                ),
                steps = listOf(
                    Step("Mix flour, sugar, and a pinch of salt in a microwave-safe mug.", durationMinutes = 1),
                    Step("Add milk, egg, and melted butter. Stir until smooth.", durationMinutes = 1, tips = listOf("Make sure there are no dry flour pockets at the bottom")),
                    Step("Microwave on high for 90 seconds. Check if center is set.", durationMinutes = 1, tips = listOf("If still wet in center, add 15-30 seconds more")),
                    Step("Let cool for 1 minute before eating from the mug.", durationMinutes = 1),
                ),
            ),
        ),
    ),
    Recipe(
        id = "tomato-pasta",
        title = "Tomato Pasta",
        prepTimeMinutes = 5,
        cookTimeMinutes = 20,
        servings = 2,
        tags = listOf("dinner", "vegetarian", "italian"),
        images = listOf("https://images.unsplash.com/photo-1622973536968-3ead9e780960?w=800"),
        notes = "Add a pinch of sugar to balance acidity of tomatoes.",
        preparationMethods = listOf(
            PreparationMethod(
                id = "pot",
                displayName = "Stovetop Pot",
                ingredients = listOf(
                    Ingredient("Spaghetti", "250 g", alternatives = listOf("penne", "fusilli")),
                    Ingredient("Canned tomatoes", "400 g", alternatives = listOf("fresh tomatoes (600g, diced)")),
                    Ingredient("Garlic", "2 cloves", alternatives = listOf("1 tsp garlic powder")),
                    Ingredient("Olive oil", "2 tbsp"),
                    Ingredient("Salt", "to taste"),
                    Ingredient("Black pepper", "to taste"),
                    Ingredient("Fresh basil", "handful", alternatives = listOf("1 tsp dried basil")),
                ),
                steps = listOf(
                    Step("Bring a large pot of salted water to boil. Water should taste like the sea.", durationMinutes = 5, tips = listOf("Use 1 tbsp salt per liter of water")),
                    Step("Add spaghetti and cook until al dente according to package directions.", durationMinutes = 8, tips = listOf("Reserve ½ cup pasta water before draining")),
                    Step("While pasta cooks, crush tomatoes by hand or blend briefly.", durationMinutes = 2),
                    Step("Heat olive oil in a pan. Add crushed garlic and cook until fragrant, 30 seconds.", durationMinutes = 1),
                    Step("Add crushed tomatoes, salt, and pepper. Simmer for 10 minutes.", durationMinutes = 10, tips = listOf("Stir occasionally to prevent sticking")),
                    Step("Drain pasta, reserving water. Add pasta to sauce with a splash of pasta water.", durationMinutes = 1),
                    Step("Toss over low heat until sauce clings to pasta. Top with basil.", durationMinutes = 1),
                ),
            ),
            PreparationMethod(
                id = "pressure-cooker",
                displayName = "Pressure Cooker (Instant Pot)",
                ingredients = listOf(
                    Ingredient("Spaghetti", "250 g", alternatives = listOf("penne", "fusilli"), tips = listOf("Break spaghetti in half to fit")),
                    Ingredient("Canned tomatoes", "400 g"),
                    Ingredient("Garlic", "2 cloves"),
                    Ingredient("Olive oil", "2 tbsp"),
                    Ingredient("Water", "2 cups"),
                    Ingredient("Salt", "1 tsp"),
                    Ingredient("Fresh basil", "handful"),
                ),
                steps = listOf(
                    Step("Add water, olive oil, and salt to pressure cooker.", durationMinutes = 1),
                    Step("Add broken spaghetti on top (don't stir). Pour crushed tomatoes over pasta.", durationMinutes = 2),
                    Step("Seal lid, set to high pressure for 7 minutes.", durationMinutes = 1, tips = listOf("Do not stir — pasta cooks in the water below")),
                    Step("Quick release pressure when timer beeps.", durationMinutes = 2),
                    Step("Stir well — sauce should coat pasta. If too thick, add water ¼ cup at a time.", durationMinutes = 1),
                    Step("Top with fresh basil and serve.", durationMinutes = 1),
                ),
            ),
        ),
    ),
)
