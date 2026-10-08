# Recipe JSON Schema

Recipes are supplied to the app as JSON files placed in `assets/recipes/`. Each file represents one recipe with potentially multiple preparation methods.

## Root Object

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | `string` | Yes | Unique identifier (slug). Used internally, not displayed. |
| `title` | `string` | Yes | Display name of the recipe. |
| `prepTimeMinutes` | `integer` | No | Preparation time in minutes. |
| `cookTimeMinutes` | `integer` | No | Cooking time in minutes. |
| `servings` | `integer` | No | Default number of servings. Default: `4`. |
| `tags` | `string[]` | No | Category labels (e.g. `"breakfast"`, `"vegetarian"`). |
| `images` | `string[]` | No | URLs to recipe images. |
| `notes` | `string` | No | General notes or serving suggestions. |
| `preparationMethods` | `object[]` | Yes | List of preparation methods (see below). |

## Preparation Method

Each preparation method defines its own ingredients and steps (e.g. stovetop vs. microwave).

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | `string` | Yes | Unique identifier for this method (e.g. `"stove"`, `"microwave"`). |
| `displayName` | `string` | Yes | Human-readable name shown in the UI tab (e.g. `"Stovetop"`). |
| `ingredients` | `object[]` | Yes | Ingredients specific to this method. |
| `steps` | `object[]` | Yes | Cooking steps with optional timings and tips. |

## Ingredient

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `name` | `string` | Yes | Ingredient name (e.g. `"Flour"`). |
| `quantity` | `string` | Yes | Quantity with unit (e.g. `"200 g"`, `"2"`, `"1 tbsp"`). Scaled automatically when servings change. |
| `alternatives` | `string[]` | No | Suggested substitutes (e.g. `["oat milk", "almond milk"]`). |
| `tips` | `string[]` | No | Tips for this ingredient (e.g. `["Room temperature mixes better"]`). |

## Step

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `description` | `string` | Yes | Instruction text. |
| `durationMinutes` | `integer` | No | Expected duration for this step in minutes. |
| `tips` | `string[]` | No | Tips or warnings for this step (e.g. `["Bubbles that don't pop mean it's time to flip"]`). |

## Example

See `app/src/main/assets/recipes/pancakes.json` for a complete working example with two preparation methods.

## Notes

- Unknown fields are ignored (kotlinx.serialization default behavior).
- All fields except `id`, `title`, `preparationMethods`, and `ingredients[].name` are nullable or have defaults.
- Servings scaling applies to all `quantity` strings that contain a numeric value.
- Images are loaded via Coil3 from URLs.
