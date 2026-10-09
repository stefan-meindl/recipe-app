# Recipe JSON Schema

Recipes are supplied to the app as JSON files placed in `assets/recipes/`. Each file represents one recipe. The app reads all `.json` files from that directory and displays them as a list.

## Root Object

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `id` | `string` | **Yes** | — | Unique slug identifier. Used internally for selection; never displayed to the user. |
| `title` | `string` | **Yes** | — | Human-readable recipe name shown in the recipe list and detail header. |
| `prepTimeMinutes` | `integer` | No | `null` | Preparation time in minutes (e.g., chopping, mixing). Shown in metadata. |
| `cookTimeMinutes` | `integer` | No | `null` | Cooking time in minutes (e.g., baking, simmering). Shown in metadata. |
| `servings` | `integer` | No | `4` | Default serving count. Used as the base for ingredient scaling. |
| `tags` | `array of strings` | No | `[]` | Category labels (e.g. `"breakfast"`, `"vegetarian"`, `"italian"`). Displayed as chips. |
| `images` | `array of strings` | No | `[]` | URLs to hero images. Loaded via Coil3. Only the first image is shown. |
| `notes` | `string` | No | `null` | General notes or serving suggestions. Shown in a dedicated section. |
| `preparationMethods` | `array of objects` | **Yes** | — | List of preparation methods (e.g. stovetop, microwave). Each method has its own ingredients and steps. |

## Preparation Method

Each preparation method defines its own ingredients and steps. The app shows a tab bar to switch between methods when more than one is present.

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `id` | `string` | **Yes** | — | Unique slug for this method. Used internally; never displayed. |
| `displayName` | `string` | **Yes** | — | Human-readable name shown as a tab label (e.g. `"Stovetop"`, `"Microwave Mug Pancake"`). |
| `ingredients` | `array of objects` | **Yes** | — | Ingredients for this method. Scaled automatically when servings change. |
| `steps` | `array of objects` | **Yes** | — | Step-by-step cooking instructions. |

## Ingredient

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `name` | `string` | **Yes** | — | Ingredient name (e.g. `"Flour"`, `"Salt"`). |
| `quantity` | `string` | **Yes** | — | Quantity with optional unit (e.g. `"200 g"`, `"2"`, `"1 tbsp"`, `"pinch"`, `"to taste"`). The app scales any leading numeric value when servings change. Non-numeric quantities like `"pinch"` are left unchanged. |
| `alternatives` | `array of strings` | No | `null` | Suggested substitutes displayed as underlined text (e.g. `["oat milk", "almond milk"]`). |
| `tips` | `array of strings` | No | `null` | Tips for this ingredient displayed with a 💡 prefix. |

## Step

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `description` | `string` | **Yes** | — | Instruction text. Displayed as a numbered step (1., 2., …). |
| `durationMinutes` | `integer` | No | `null` | Expected duration for this step. Shown with a clock icon. |
| `tips` | `array of strings` | No | `null` | Tips or warnings displayed indented below the step with a 💡 prefix. |

## Example

See `app/src/main/assets/recipes/pancakes.json` for a complete working example with two preparation methods.

## Notes

- **Unknown fields are ignored** — kotlinx.serialization's default behavior.
- **Required fields**: `id`, `title`, `preparationMethods` (root); `id`, `displayName`, `ingredients`, `steps` (preparation method); `name`, `quantity` (ingredient); `description` (step).
- **Servings scaling**: When the user adjusts servings, each ingredient's quantity is multiplied by `(effectiveServings / baseServings)`. The `scaleQuantity` function extracts the leading number from strings like `"200 g"` or `"1.5 cups"`, scales it, and reattaches the unit. Quantities without a leading number (e.g. `"pinch"`, `"to taste"`) are left unchanged.
- **Image loading**: Images are fetched asynchronously via Coil3. Only the first URL in `images` is displayed as the hero image.
- **Tab switching**: When a recipe has more than one preparation method, a tab bar appears to switch between methods. Each method has its own independent ingredient list and steps.
- **UI behavior**: The app uses Android Jetpack Compose with Material 3. The recipe list shows title, total time (prep + cook), and tags. The detail view shows a hero image, metadata, notes, method tabs, a servings adjuster, a shopping list, and numbered steps.
