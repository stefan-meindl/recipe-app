## Issue #6 — 02 · Port Recipe model to v2

**Request:** https://github.com/stefan-meindl/recipe-app/issues/6
**Branch:** `feature/recipe-model-v2` (local) → PR pending

### Scope determination (STAGE 3 — TESTER, 2026-10-10)

The upstream engineer turn could not read issue #6's content (private-repo link 404s) and
asked whether the issue was the JSON conversion, the git setup, or something else. The scope
question was material — the interpretations led to completely different work.

The TESTER stage resolved the scope by reading issue #6 directly via the GitHub connector
(`mcp__github__issue_read`), which returns the real state for the private repo:

> **Issue #6 — "02 · Port Recipe model to v2"**
> Replace `model/Recipe.kt` with v2 data classes.
> - New root `RecipeV2(id, title, servings, category, tags, icon, summary, variations[], methods[], ingredients[], steps[])`.
> - `Variation(kind: swap|add, label, options, def)` and method option with `time`.
> - `IngredientVariant(q: Double?, u: String?, alt: List<Pair<String, Double>> /* (condition, value) pairs */, aisle, tips[])` — plain quantity strings gone.
> - Steps carry `needs[]`, inline ref markers, `durationSeconds`.
> - Keep deserialization tolerant of unknown fields (don't crash on legacy samples).
>
> **Acceptance criteria:**
> 1. GIVEN v2 sample input, THEN it deserializes cleanly with no missing-field exceptions.
> 2. GIVEN a legacy v1 sample, THEN parsing surfaces a clear validation error (not a silent partial success).

So this is a **real Kotlin coding task**, not a doc or git task. The engineer's turn was
correctly blocked (couldn't read the issue) and produced no code change. The TESTER stage
located the repo code at `/home/stefan/projects/recipe-app`, found the v2 work already
started in untracked files (`RecipeV2.kt`, `RecipeV2CodecTest.kt`), and proceeded to
complete and verify it.

---

## ENGINEER notes

The engineer turn was a scope determination only (issue #6 was unreachable). No code change
was made upstream. The v2 model files existed as untracked work-in-progress in the repo
(`app/src/main/kotlin/com/example/recipeapp/model/RecipeV2.kt` and
`app/src/test/kotlin/com/example/recipeapp/model/RecipeV2CodecTest.kt`), written alongside
the existing v1 `Recipe.kt` so the app keeps compiling while the port is completed.

---

## TESTER notes (STAGE 3)

### Checks I actually ran (real output)

1. **Repo location** — `find /home/stefan` found the real repo at
   `/home/stefan/projects/recipe-app` (has `.git`, `app/`, `JSON_SCHEMA.md`). The engineer's
   `scaffold/` is only the starting point. The private repo is reachable via `gh`
   (`git ls-remote` returned HEAD `166d9d0d`).

2. **Build before the fix** — `./gradlew testDebugUnitTest --tests
   com.example.recipeapp.model.RecipeV2CodecTest` → **BUILD FAILED**:
   ```
   e: .../RecipeV2.kt:183:9 Conflicting declarations: phase: StepPhase
   e: .../RecipeV2.kt:186:9 Conflicting declarations: phase: StepPhase
   * What went wrong: REDECLARATION
   ```
   Root cause: `StepV2` declared **two properties named `phase`** — one plain and one with
   `@SerialName("ph")`. The plain one was a copy-paste bug; the intended field is the
   `@SerialName("ph")` one (the design key for phase is `"ph"`, per the JSON schema and the
   existing test `v2StepsReadThePhPhaseKey`).

3. **Fix applied** — removed the duplicate `val phase: StepPhase,` line (line 183), keeping
   only the `@SerialName("ph")` field. Verified by read-back before and after.

4. **Build after the fix** — `./gradlew testDebugUnitTest` → **BUILD SUCCESSFUL**.

5. **Full unit-test suite** (all 4 classes):

   | Suite | Tests | Failures | Errors |
   |-------|------:|---------:|-------:|
   | `RecipeSerializationTest` | 8 | 0 | 0 |
   | `RecipeV2CodecTest` | 10 | 0 | 0 |
   | `RecipesViewModelTest` | 3 | 0 | 0 |
   | `RecipeResolverTest` | 11 | 0 | 0 |
   | **TOTAL** | **32** | **0** | **0** |

6. **No compiler warnings** after fixing the JUnit float-comparison opt-in warning in the
   new test.

### Acceptance-criteria verification

- **Criterion 1 (v2 sample deserializes cleanly):** covered by `v2SampleDeserializesCleanly`,
  `v2StepsReadThePhPhaseKey`, `v2IngredientsReadNumericQtyAndUnit`, and the new
  `unknownFieldsAreIgnored` and `altConditionPairDecodesPositionally` — all pass.
- **Criterion 2 (legacy v1 surfaces a clear error):** covered by `legacyV1SampleSurfacesClearError`,
  the new `legacyV1WithoutIdStillSurfacesClearError`, and `rootObjectMustBeAnObject` — all pass.

### Tests added / changed (tester stage)

Added 5 edge-case tests to `RecipeV2CodecTest.kt`, all passing:

- **`unknownFieldsAreIgnored`** — the issue's "tolerant of unknown fields" requirement is
  **not** covered by the original tests. Injects an unknown root field + a nested unknown
  object; asserts the recipe still decodes. **Covers a gap.**
- **`legacyDetectionRequiresMissingV2Methods`** — a v2 sample that *also* carries a
  `preparationMethods` array must still decode as v2, not be rejected as legacy. Legacy
  detection is keyed on the *absence* of v2 `methods`, so this pins that boundary.
- **`legacyV1WithoutIdStillSurfacesClearError`** — a legacy sample with no `id` must still
  surface a clear "legacy v1" error rather than crash on the `"<unknown>"` fallback.
- **`altConditionPairDecodesPositionally`** — the `alt` per-condition override must round-trip
  its positional `[condition, value]` pair (design `alt` as a JSON array, decoded by the
  custom `AltConditionSerializer`).
- **`missingRequiredFieldThrows`** — a missing required field (`id`) must surface as a
  `MissingFieldException`, not a silent partial success.

Two of these initially **failed** and I fixed the test code (not the model):
- `unknownFieldsAreIgnored` produced malformed JSON from the string replace (missing comma);
  fixed by anchoring on the trailing newline/comma.
- `altConditionPairDecodesPositionally` used a bare `assertEquals(3.0, value)` — JUnit
  requires a delta for floats; fixed with `assertEquals(3.0, value, 0.0001)`.
- The `missingRequiredFieldThrows` test referenced `MissingFieldException` with an opt-in
  warning; simplified the assertion to check `javaClass.simpleName`.

### Files changed

- `app/src/main/kotlin/com/example/recipeapp/model/RecipeV2.kt` — removed the duplicate
  `phase` property in `StepV2` (the REDECLARATION blocker). 1 line removed.
- `app/src/test/kotlin/com/example/recipeapp/model/RecipeV2CodecTest.kt` — added the 5
  edge-case tests above.

### How to run the tests
```
./gradlew testDebugUnitTest --tests "com.example.recipeapp.model.RecipeV2CodecTest"
./gradlew testDebugUnitTest            # all 32 tests
```

### Anything the reviewer should scrutinise

- The `StepV2` fix: confirm the `@SerialName("ph")` field is the intended one (the design
  key is `"ph"` per JSON_SCHEMA.md and the existing `v2StepsReadThePhPhaseKey` test). The
  removed field was a plain `val phase: StepPhase` with no `@SerialName`, which would have
  mapped to a nonexistent `"phase"` JSON key anyway.
- `RecipeV2Codec.decodeFromString` is the only public entry point; it is **not yet wired**
  into any UI, loader, or the `RecipeResolver` (that's issues #03+). Confirm the model
  compiles and decodes but nothing calls it yet — the acceptance criteria are about the
  codec, not integration.
- The model keeps the v1 `Recipe.kt` alongside the v2 model (additive port). Confirm both
  `RecipeSerializationTest` (v1) and `RecipeV2CodecTest` (v2) still pass together — they do.

VERDICT: approved — the REDECLARATION blocker is fixed and all 32 tests pass on a clean run.

Handoff to REVIEWER: re-run `./gradlew testDebugUnitTest`, confirm the `@SerialName("ph")`
field is the intended `StepV2` phase, and note that `RecipeV2Codec` is not yet wired into
any UI/loader/resolver (that's the next issue).
