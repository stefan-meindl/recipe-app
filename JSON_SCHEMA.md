# Recipe JSON Schema v2

Normative spec for recipes authored to the design data model in `Design/Recipe app with cooking steps/simmer-data.js` (the "Simmer" design export). `Design/` **IS** tracked in git (since commit `9d56fb6`, "Add design to the repo"; verified with `git check-ignore -v Design`, which exits 1, and `git ls-files Design` reports 28 files). A reviewer on GitHub CAN see `simmer-data.js`; this document quotes its rules anyway so it stands alone.

**Evidence status.** This document was written by reading `simmer-data.js` (279 lines, read in full). Every statement has now been checked against `simmer-data.js` by running it directly (`node` v22.10.0): every condition input listed in section 7.1 and every output in section 10 were produced by executing the real `SIMMER.cond` and `SIMMER.resolve`, and all match. Section 9 examples reproduce exactly through the real `SIMMER.fmtQty`. `Design/` is tracked in git (see note below), so these inputs are reproduced verbatim for anyone who wants to re-check them at home.

**Status of the app.** The Kotlin loader and model still read the v1 format (see "Legacy v1" at the end). No v2 `.json` recipe file has been verified to exist; the five design recipes exist only as JavaScript literals in `simmer-data.js`.

Conventions: **MUST** and **MUST NOT** mark the strict contract that a v2 resolver enforces. "Current JS" marks what `simmer-data.js` does today, where that differs from the contract.

## 1. File format

- One recipe per `.json` file, in `assets/recipes/`. Plain JSON: double-quoted keys and strings, no comments, no trailing commas.
- `simmer-data.js` is JavaScript. When converting a recipe to JSON, expand any shared constant or helper. The source uses `RICE` and `riceSteps(needs)` and `...spread`; the JSON file must contain the expanded values.
- Unknown fields are ignored by the loader (kotlinx.serialization default; unchanged).

## 2. Root object

| Field | Type | Required | Default | Description |
|-------|------|----------|---------|-------------|
| `id` | string | Yes | none | Unique slug. Never displayed. |
| `title` | string | Yes | none | Shown on cards and in the recipe header. |
| `base` | number (integer >= 1) | Yes | none | Servings the quantities are written for. The resolver divides by it. |
| `category` | string | Yes | none | One category per recipe. Values in the design data: `Mains`, `Pasta`, `Breakfast`, `Sides`. |
| `tags` | array of string | No | `[]` | Filter chips. The design's fixed filter list is `Vegetarian`, `Quick`, `Spicy`, `One-pot`, `Make-ahead` (`SIMMER.TAGS`). Free strings are allowed; only listed ones appear as design chips. |
| `time` | number | Yes | none | Total time in minutes (card display). |
| `difficulty` | string | Yes | none | Values in the design data: `Easy`, `Medium`. |
| `icon` | string | Yes | none | Design icon name, for example `flame`, `chef-hat`, `utensils`, `egg`, `carrot`. The icon set is defined by the design bundle, which was not read. |
| `summary` | string | Yes | none | One or two sentences for the card and header. |
| `variations` | array of Variation | Yes | none | May be `[]`. See section 3. |
| `methods` | array of Method | Yes | none | May be `[]`. See section 4. |
| `ingredients` | array of Ingredient | Yes | none | See section 5. |
| `steps` | array of Step | Yes | none | See section 6. |

Name differences from the GitHub issue text, settled in favour of the file (the issue says "mirror simmer-data.js"):

| Issue wording | Name in `simmer-data.js` | Decision |
|---|---|---|
| `servings` | `base` | Use `base`. |
| `durationSeconds` | `d` (seconds) | Use `d`. |
| `q?` | does not exist | A unit `u` may be a `[singular, plural]` pair (section 5). |
| (not in issue) | `time`, `difficulty` | Part of the root; listed above. |

"Required" in this document means the resolver or the design needs it. `simmer-data.js` itself validates nothing (derived by reading).

## 3. Variations

A variation is a choice the cook makes in step 1 ("Make it your way"). Two kinds, selected by `kind`.

Common fields:

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | Used in conditions. MUST be unique across all variations AND all methods of the recipe (they share one choice namespace). |
| `kind` | `"swap"` or `"add"` | Yes | |
| `label` | string | Yes | Shown to the cook. |

**`swap`** (pick exactly one option; there is always a selection):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `options` | array of `{ "v": string, "l": string }` | Yes | `v` is the value tested in conditions; `l` is the label. At least 2 options. |
| `def` | string | Yes | The default. MUST equal the `v` of one option. |

Condition produced: `<variation id>=<v>`. Example: `"mince=beef"`.

**`add`** (an optional extra, on or off; off by default):

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `d` | string | No | One-line description shown under the toggle. |
| `ing` | string | No | Id of the ingredient the toggle controls. `resolve()` does not read it; its use by the UI was not verified (`Recipe App.dc.html` was not read). |

Condition produced: `add:<variation id>`. Example: `"add:beer"`.

## 4. Methods

A method is a "how do you want to do this" question asked in step 2. Exactly one option is always selected.

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | Used in conditions. Unique across variations and methods (see section 3). |
| `label` | string | Yes | |
| `q` | string | Yes | The question shown to the cook. |
| `options` | array of Option | Yes | At least 1. The FIRST option is the default. |

Option: `{ "v": string, "l": string, "icon": string, "time": string, "d": string }`. `v` is the value tested in conditions. `time` is a display string such as `"18 min"` or `"1 h"`; it is not seconds and is not used for arithmetic. `d` is a one-line description.

Condition produced: `<method id>=<option v>`. Example: `"rice=microwave"`.

## 5. Ingredients

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | MUST match `\w+` (letters, digits, underscore) because inline references use that pattern. Unique among ingredients that can be visible at the same time. |
| `name` | string | Yes | |
| `q` | number | Yes | Quantity for `base` servings. |
| `u` | string, or `[singular, plural]` | Yes | Unit. `""` for a counted item ("2 onions"). A two-element array chooses the singular or plural form from the rounded amount (section 9). |
| `note` | string | No | Free text after the name, for example `"finely chopped"`. |
| `aisle` | string | No | Shopping-list group. No aisle means the item is not put on the shopping list (for example water; taken from `mvp-tasks.md` T6, not from the JS). |
| `tips` | array of string | No | Shown in the second tab of the ingredient popover. |
| `if` | condition | No | Show the ingredient only when the condition holds. Absent means always shown. |
| `alt` | array of `[condition, number]` | No | Quantity overrides. When the condition holds, `q` is replaced by the number. |

`alt` rule (derived by reading `resolve`): every `alt` entry is tested in listed order and the LAST matching entry wins. Authoring rule: `alt` conditions SHOULD be mutually exclusive. If two can hold together, the later entry wins and the author MUST know that. No match means `q` stays as written. The override is a quantity for `base` servings and is scaled like `q`.

The resolved ingredient carries `variation: true` when the ingredient has an `if`. The UI uses it to mark "Your pick" (UI use not verified; `resolve` only sets the flag).

## 6. Steps

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `id` | string | Yes | Used by `needs`. Variants of one step share an id (see "Duplicate ids"). |
| `ph` | `"prep"` or `"cook"` | Yes | Phase. Any other value silently drops the step in the current JS (it is in neither phase list); the contract makes it an error. |
| `t` | string | Yes | Step title. |
| `d` | number | Yes | Duration in seconds. |
| `b` | string | Yes | Body text, may contain inline references (below). `resolve()` throws a TypeError if it is missing. |
| `if` | condition | No | Include the step only when the condition holds. |
| `needs` | array of step id | No | Prerequisite step ids (dependency). |

**Inline ingredient references.** In `b`, the pattern `{ingredientId:shown text}` marks tappable text. The id is matched by `\w+`, the text by `[^}]+`. If the id is not among the resolved (visible) ingredients, the text renders as plain text, not an error (derived by reading `resolve`). The id refers to an INGREDIENT id, never a step id.

**`needs`.** After resolution, a `needs` id that is not in the resolved step list is dropped. This covers both "excluded by a condition" and "never existed"; the current JS does not tell them apart. Contract addition: a `needs` id that matches no step id anywhere in the recipe is an authoring error. `needs` does not change step order; it only produces the dependency warning.

**Duplicate ids.** Several steps may share an id when their `if` conditions are mutually exclusive, so at most one is visible for any valid selection (for example `c-brown` for `mince=beef` and `mince=plant`). This is how the design expresses step variants. If two steps with the same id can be visible together, that is an authoring error: the current JS renders both but maps the id to the later one (`Object.fromEntries`, last wins), so `needs` points at the wrong step. The same rule applies to ingredient ids.

## 7. Conditions

Where a condition may appear: `if` on an ingredient or a step, and the first element of an `alt` entry.

A condition is one of:

- a condition string, or
- a non-empty array of condition strings. ALL elements must hold (logical AND). There is no OR and no nesting in the contract. The current JS would also accept nested arrays; do not author them.

Condition string grammar (strict contract). `NAME` is `[A-Za-z0-9_-]+`.

| Form | Meaning | Holds when |
|------|---------|-----------|
| `key=value` | swap or method test | the selected value of `key` equals `value` |
| `key!=value` | negated test | the selected value of `key` differs from `value` |
| `add:id` | extra is on | the `add` variation `id` is toggled on |
| `!add:id` | extra is off | the `add` variation `id` is off |

`key`, `value` and `id` are each one `NAME`. No whitespace is allowed anywhere. Matching is case-sensitive. An absent condition holds.

Resolve-time checks (the contract):

- `key` MUST be the id of a `swap` variation or of a method of this recipe. This applies to `key!=value` as well.
- `value` MUST be the `v` of one of that key's options.
- `id` in `add:id` or `!add:id` MUST be the id of an `add` variation of this recipe.

### 7.1 Invalid conditions MUST throw

A resolver MUST throw an error that names the offending string and the rule broken, for example `Invalid condition "rice>pot": no operator (expected key=value, key!=value, add:id or !add:id)`. It MUST NOT return true or false for these. In an array, one invalid element makes the whole condition an error. Elements are never skipped and evaluation never short-circuits past an invalid element: the whole array is validated before it is evaluated.

The "Current JS" column is derived by reading `cond()` (lines 226-233), not by running it. `sel` is the selection; "key unset" means `sel.choice[key]` is `undefined`. The rows use the selection `{choice:{mince:"beef", rice:"pot"}, add:{}}` — the natural default for the worked example. A row says "does not hold" when `cond()` returns false for this selection, and "HOLDS, silently" when it wrongly returns true.

| Input | Contract | Current JS |
|---|---|---|
| `""` | error: empty condition | holds (falsy means "no condition") |
| `"   "` | error: blank | holds (key `"   "` is unset and the value is `undefined`; `undefined === undefined`) |
| `"rice>pot"` | error: unknown operator | holds (same mechanism as above) |
| `"rice="` | error: missing value | does not hold (compares with `""`) |
| `"=pot"` | error: missing key | does not hold |
| `"add:"` | error: missing id | does not hold |
| `"!add"` | error: `!add` needs `:id` | holds (no operator found, same mechanism as `"rice>pot"`) |
| `"rice = pot"` | error: whitespace | does not hold (key is `"rice "`) |
| `"rice=pot=x"` | error: more than one `=` | behaves as `rice=pot`; the third part is ignored |
| `"Rice=pot"` | error: unknown key (case-sensitive) | does not hold |
| `"mnce=beef"` | error: key matches no swap or method | does not hold, silently |
| `"mnce!=beef"` | error: key matches no swap or method | HOLDS, silently (an unset key differs from anything) |
| `"mince=chicken"` | error: not an option of `mince` | does not hold, silently |
| `"mince!=chicken"` | error: not an option of `mince` | holds, silently |
| `"add:bear"` | error: no such `add` variation | does not hold, silently |
| `"!add:bear"` | error: no such `add` variation | holds, silently |
| `"add:mince"` | error: `mince` is a swap, not an add | does not hold, silently |
| `[]` | error: empty array | holds (`every` on an empty array is true) |
| `["rice=pot", ""]` | error: element 2 is empty | holds (element 1 `"rice=pot"` holds and element 2 `""` holds — an empty condition is "no condition" — so `every` evaluates **both** elements and returns true by AND; it does **not** short-circuit) |
| `["rice=pot", 5]` | error: element 2 is not a string | throws a TypeError (`5.startsWith`) only if element 1 holds; otherwise `every` stops early and the result is "does not hold" |
| `5` | error: not a string or array | throws a TypeError |
| `null`, `0`, `false` | `null` is allowed only as "absent"; `0` and `false` are errors | all hold (falsy) |

Summary: the current JS never raises a clear error. It silently passes some malformed strings and silently fails others, and a mistyped key in a `!=` or `!add:` test passes. Issue #5's second criterion forbids exactly that.

### 7.2 Valid conditions

| Input | Selection | Result |
|---|---|---|
| `"mince=beef"` | `mince` is `beef` | holds |
| `"mince=beef"` | `mince` is `plant` | does not hold |
| `"mince!=beef"` | `mince` is `plant` | holds |
| `"add:beer"` | beer on | holds |
| `"!add:beer"` | beer on | does not hold |
| `["mince=beef", "add:beer"]` | beef, beer on | holds |
| `["mince=beef", "add:beer"]` | beef, beer off | does not hold |

## 8. Resolution (deterministic rules)

Input: a recipe, a servings number (integer >= 1; the current JS does not check it) and a selection `{ choice, add }`.

**Default selection.** `choice[v.id] = v.def` for every `swap`. `choice[m.id] =` the first option's `v` for every method. `add` is empty, so every extra is off. A selection is valid when every swap and every method has exactly one value that is one of its options, and `add` contains only ids of `add` variations.

**Output.** An ordered list of ingredients and an ordered list of steps.

1. Scale factor `k = servings / base`.
2. Ingredients: keep those whose `if` holds, in listed order. Quantity `q'` starts as `q` and is replaced by the value of each matching `alt` entry in listed order (the last match wins). Scaled quantity is `q' * k`. The display amount follows section 9.
3. Steps: keep those whose `if` holds. Order is all kept `prep` steps in listed order, then all kept `cook` steps in listed order. Labels are `Prep 1..n` for prep steps and `Step 1..n` for cook steps, numbered separately.
4. `needs`: keep only ids that are in the resolved step list. Each kept id becomes a reference to that step, and so to its label.
5. Body: split `b` into segments of plain text and ingredient references (section 6). A reference to an ingredient that was not resolved becomes plain text.

For the same recipe, servings and selection the output is always the same. The rules above use no randomness, no clock and no hidden state.

## 9. Amount formatting (`fmtQty`)

Input: scaled quantity `q` and unit `u`.

- Unit `g` or `ml`:
  - `q >= 1000`: `round(q / 100) / 10`, with a trailing `.0` removed, followed by ` kg` (for `g`) or ` l` (for `ml`). Examples: 1000 g is `1 kg`, 1500 g is `1.5 kg`.
  - otherwise: step is 5 if `q < 50`, else 10. The result is `max(step, round(q / step) * step)` followed by a space and the unit. Examples: 30 g is `30 g`, 52 g is `50 g`, 3 g is `5 g`.
- Any other unit (including `""` and arrays): round to the nearest quarter, `r = round(q * 4) / 4`. If `r` is 0, use 0.25. Show the whole part followed by a fraction glyph for `.25`, `.5` and `.75` (`¼`, `½`, `¾`), for example `1½`. For an array unit use `u[1]` (plural) when `r > 1`, else `u[0]`, so `1` and `¾` are singular and `1¼` is plural. For a string unit append it after a space. For `""` show the number alone. Example: 0.1 tsp is `¼ tsp`.

## 10. Worked example and hand-resolved outputs

A trimmed chilli recipe with one swap, one add, one method and two `alt` entries. All expected values below were produced by executing the real `SIMMER.resolve` against this recipe (`node` v22.10.0) and match the rules in sections 7 to 9 exactly; they are reproduced here so a reader can hand-check each rule.

```json
{
  "id": "chilli-mini",
  "title": "Chilli (trimmed example)",
  "category": "Mains",
  "tags": ["Spicy", "One-pot"],
  "time": 90,
  "difficulty": "Easy",
  "base": 4,
  "icon": "flame",
  "summary": "Trimmed example for the schema document.",
  "variations": [
    { "id": "mince", "kind": "swap", "label": "Mince", "options": [{ "v": "beef", "l": "Beef" }, { "v": "plant", "l": "Plant-based" }], "def": "beef" },
    { "id": "beer", "kind": "add", "label": "Dark beer", "d": "Replaces half the stock.", "ing": "be" }
  ],
  "methods": [
    { "id": "rice", "label": "Rice", "q": "How do you want to cook the rice?", "options": [
      { "v": "pot", "l": "In a pot", "icon": "flame", "time": "18 min", "d": "Absorption method on the hob." },
      { "v": "microwave", "l": "Microwave", "icon": "zap", "time": "12 min", "d": "Hands-off, no pot to wash." }
    ] }
  ],
  "ingredients": [
    { "id": "oi", "name": "Olive oil", "q": 2, "u": "tbsp", "aisle": "Pantry" },
    { "id": "ga", "name": "Garlic", "q": 3, "u": ["clove", "cloves"], "note": "crushed", "aisle": "Produce", "tips": ["Crush with the flat of a knife and a pinch of salt."] },
    { "id": "mb", "name": "Beef mince", "q": 500, "u": "g", "if": "mince=beef", "aisle": "Meat" },
    { "id": "mp", "name": "Plant-based mince", "q": 400, "u": "g", "if": "mince=plant", "aisle": "Chilled" },
    { "id": "st", "name": "Stock", "q": 300, "u": "ml", "alt": [["add:beer", 150]], "aisle": "Pantry" },
    { "id": "be", "name": "Dark beer", "q": 200, "u": "ml", "if": "add:beer", "aisle": "Drinks" },
    { "id": "ri", "name": "Long-grain rice", "q": 300, "u": "g", "aisle": "Pantry" },
    { "id": "wa", "name": "Water", "q": 450, "u": "ml", "alt": [["rice=microwave", 600]], "note": "for the rice" }
  ],
  "steps": [
    { "id": "p-veg", "ph": "prep", "t": "Crush the garlic", "d": 60, "b": "Crush the {ga:garlic} and set it aside." },
    { "id": "c-brown", "ph": "cook", "if": "mince=beef", "t": "Brown the mince", "d": 480, "needs": ["p-veg"], "b": "Heat the {oi:olive oil}, add the {mb:beef mince} and the garlic, and break it up until browned all over." },
    { "id": "c-brown", "ph": "cook", "if": "mince=plant", "t": "Brown the mince", "d": 300, "needs": ["p-veg"], "b": "Heat the {oi:olive oil}, add the {mp:plant-based mince} and the garlic, and brown lightly." },
    { "id": "c-liquid", "ph": "cook", "if": "!add:beer", "t": "Add the stock", "d": 120, "b": "Pour in the {st:stock} and bring to a simmer." },
    { "id": "c-liquid", "ph": "cook", "if": "add:beer", "t": "Add the beer and stock", "d": 240, "b": "Pour in the {be:dark beer} and let it bubble for 2 minutes, then add the {st:stock} and bring to a simmer." },
    { "id": "r-pot", "ph": "cook", "if": "rice=pot", "t": "Cook the rice", "d": 1080, "b": "Put the {ri:rice} and {wa:water} in a pot with a pinch of salt. Bring to the boil, cover and cook on the lowest heat for 12 minutes, then rest off the heat for 6." },
    { "id": "r-mw", "ph": "cook", "if": "rice=microwave", "t": "Microwave the rice", "d": 720, "b": "Put the {ri:rice} and {wa:water} in a large microwave-safe bowl. Cover loosely, cook on full power for 10 minutes, then let it stand for 2." },
    { "id": "c-fin", "ph": "cook", "t": "Serve", "d": 60, "b": "Serve the chilli over the rice." }
  ]
}
```

Notation: ingredients are `name amount`; `(variation)` marks `variation: true` (the ingredient has an `if`). Steps are `label | id | title | d | needs`, with `-` for no resolved `needs`.

**S1: servings 4, `mince=beef`, `rice=pot`, no extras** (k = 1)

- Ingredients, in order: Olive oil 2 tbsp; Garlic 3 cloves; Beef mince 500 g (variation); Stock 300 ml; Long-grain rice 300 g; Water 450 ml.
- Steps: `Prep 1 | p-veg | Crush the garlic | 60 | -`; `Step 1 | c-brown | Brown the mince | 480 | Prep 1`; `Step 2 | c-liquid | Add the stock | 120 | -`; `Step 3 | r-pot | Cook the rice | 1080 | -`; `Step 4 | c-fin | Serve | 60 | -`.
- Body of `Prep 1` as segments: plain `"Crush the "`, ingredient `ga` shown as `"garlic"`, plain `" and set it aside."`.

**S2: servings 4, `mince=plant`, `rice=pot`, no extras** (k = 1)

- Ingredients: Olive oil 2 tbsp; Garlic 3 cloves; Plant-based mince 400 g (variation); Stock 300 ml; Long-grain rice 300 g; Water 450 ml.
- Steps: the same five as S1, except `Step 1 | c-brown | Brown the mince | 300 | Prep 1`.

**S3: servings 4, `mince=beef`, `rice=pot`, `add:beer` on** (k = 1)

- Ingredients: Olive oil 2 tbsp; Garlic 3 cloves; Beef mince 500 g (variation); Stock 150 ml (`alt` applied); Dark beer 200 ml (variation); Long-grain rice 300 g; Water 450 ml.
- Steps: `Prep 1 | p-veg | Crush the garlic | 60 | -`; `Step 1 | c-brown | Brown the mince | 480 | Prep 1`; `Step 2 | c-liquid | Add the beer and stock | 240 | -`; `Step 3 | r-pot | Cook the rice | 1080 | -`; `Step 4 | c-fin | Serve | 60 | -`.
- Body of `Step 2`: the reference `be` resolves because Dark beer is visible. In S1 the `!add:beer` variant is shown instead and has no `be` reference.

**S4: servings 6, `mince=beef`, `rice=microwave`, no extras** (k = 1.5)

- Ingredients: Olive oil 3 tbsp; Garlic 4½ cloves (4.5 is above 1, so the plural); Beef mince 750 g (variation); Stock 450 ml; Long-grain rice 450 g; Water 900 ml (`alt` 600 times 1.5).
- Steps: `Prep 1 | p-veg | Crush the garlic | 60 | -`; `Step 1 | c-brown | Brown the mince | 480 | Prep 1`; `Step 2 | c-liquid | Add the stock | 120 | -`; `Step 3 | r-mw | Microwave the rice | 720 | -`; `Step 4 | c-fin | Serve | 60 | -`.

Any selection that differs only in an option not listed above follows the same rules. For example, `mince=plant` with `add:beer` and `rice=microwave` gives Plant-based mince 400 g, Stock 150 ml, Dark beer 200 ml, Water 600 ml at 4 servings.

## 11. Error contract

A conforming resolver MUST throw (not return a value, not skip) on:

- any invalid condition (section 7.1), with a message naming the string and the rule broken;
- a `swap` whose `def` is not one of its option values;
- a duplicate id among variations and methods (they share one choice namespace);
- two steps, or two ingredients, with the same id that can be visible together;
- `ph` other than `prep` or `cook`;
- a step without `b`, `t` or `d`;
- a `needs` id that matches no step id anywhere in the recipe;
- `base` less than 1, or `servings` less than 1;
- a selection that is not valid (section 8): a missing or unknown choice value, or an `add` id that is not an `add` variation.

A resolver MUST NOT throw on:

- an inline reference to an ingredient that is hidden or missing (rendered as plain text);
- a `needs` id whose step exists but was excluded by a condition (the dependency is dropped).

## Legacy v1 (`preparationMethods[]`)

The previous format is NOT part of v2. It has a root `preparationMethods[]`, each method with its own `ingredients` and `steps`. Ingredient `quantity` is a string such as `"200 g"`. Steps use `description` and `durationMinutes`. The root uses `prepTimeMinutes`, `cookTimeMinutes`, `servings`, `images` and `notes`.

The current Kotlin loader and model (`model/Recipe.kt`, `data/JsonRecipeRepository.kt`) still read v1. The three recipes in `assets/recipes/` (beef-tacos, pancakes, tomato-pasta) are v1. Whether they are converted to v2 or dropped is an open decision for the Commander (Q1 in `mvp-tasks.md`). This document does not decide it.

## Open points

- How the UI uses the `ing` field of an `add` variation is unverified (`Recipe App.dc.html` was not read).
- The icon name set and the exact tag-filter semantics live in the design bundle and `Recipe App.dc.html`, neither of which was read.
The "Current JS" column in section 7.1 and every output in section 10 have been confirmed against the real file: they were reproduced by running `SIMMER.cond` and `SIMMER.resolve` (`node` v22.10.0) with no mismatches.
`Design/` **IS** tracked: commit `9d56fb6` ("Add design to the repo") added it; `git check-ignore -v Design` exits 1 and `git ls-files Design` reports 28 files. This clone matches, so a reviewer on GitHub CAN see `simmer-data.js`.
