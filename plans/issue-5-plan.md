
## Build log (executed 2026-10-10)

- `node /tmp/verify-cond.js` — `SIMMER.cond` for all 22 section 7.1 rows vs `{choice:{mince:'beef',rice:'pot'}, add:{}}`: every result matches the doc's "Current JS" column.
- `node /tmp/verify-ex.js` — `SIMMER.resolve` for S1–S4 and `fmtQty` for 9 cases: all match the doc exactly.
- `node /tmp/verify-final.js` — re-parsed the section 10 example from the edited `JSON_SCHEMA.md` and re-ran S1–S4: JSON parse OK, S1/S2/S3/S4 PASS.
- `git checkout -b docs/issue-5-v2-schema && git add JSON_SCHEMA.md && git commit` — 1 file changed, 311 insertions/42 deletions.
- `git push -u origin docs/issue-5-v2-schema` — new branch pushed.
- `gh pr create ...` — PR #30 "docs: author v2 JSON schema (JSON_SCHEMA.md)" opened at https://github.com/stefan-meindl/recipe-app/pull/30, body lists all executed checks, ends with "Closes #5".
- Fix applied: section 10 body no longer says "computed by hand / NOT executed"; it states the example was executed against the real `SIMMER.resolve` (the header/Open points had already claimed execution — this removed the self-contradiction).

## ENGINEER notes

### What I checked (independently, against the live source)
The tester and reviewer both ran with `code_run`/`shell_exec` withheld, so their
verification of the doc's "executed" claim was hand-traced. I ran the real source
this pass. `Design/Recipe app with cooking steps/simmer-data.js` assigns to
`window` (not `global`), so it was loaded via `vm.createContext({window:{}})` +
`vm.runInContext`.

Verified against the **real** `SIMMER` (node v22.10.0):
- Section 7.1: all 22 invalid-condition rows match the doc's "Current JS" column
  (the `null`/`0`/`false` row counts as 3). 0 mismatches.
- Section 9: all 9 `fmtQty` cases match; `fmtQty(3,'')` -> `"3"` (number-alone rule).
- Section 10: S1, S2, S3, S4, and the extra example (plant + microwave + beer @4)
  all match the doc's stated ingredient lines, step lines, and Prep-1 body segments.
- The section-10 JSON example parses (`JSON.parse`, no throw).
- `git check-ignore -v Design` exits 1; `git ls-files Design` = 28 files. `Design/` IS tracked.

### The one real thing I changed
The doc claimed (in its header, section 8, section 10, and Open points) that the
worked example was *executed*. The tester/reviewer could only hand-trace it. To make
that claim durable and re-checkable, I added a committed, self-contained script that
reproduces the execution so nobody has to trust a sentence in the doc.

### Files changed
- `scripts/verify-issue5-schema.js` (NEW, 155 lines) — loads the real
  `simmer-data.js`, re-checks every 7.1 row, every 9 `fmtQty` case, and S1–S4, and
  exits non-zero on any mismatch. Re-run with: `node scripts/verify-issue5-schema.js`.
- Committed to branch `docs/issue-5-v2-schema` (commit `1ffdac9`). PR #30 now carries
  it (diff grew from 311 to 466 insertions).

### How to run the check
```
cd /home/stefan/projects/recipe-app
node scripts/verify-issue5-schema.js   # -> "OK: JSON_SCHEMA.md matches the real SIMMER source..."
```
Requires node >= 18. No dependencies.

### Anything the tester should probe
- Run the script above on a clean checkout (it reads the real source, not a copy).
- Confirm the branch is up to date with origin: `git log --oneline -1 origin/docs/issue-5-v2-schema`.
- The doc's two "minor" open points from the tester notes are still open (not mine to
  close): the `["rice=pot",""]` row wording, and the fact that section 7.1's `sel`
  assumption is stated now but the two remaining Open-points items (UI use of `ing`,
  icon-name set) are unverified.
- PR #30 is OPEN and not merged. Per the repo's own workflow, adding the `engineering`
  label triggers the build line.

## TESTER notes

Ran the engineer's checks against the live source on this pass (shell available), plus
added edge-case tests that go beyond the doc's own worked rows.

### Checks I actually ran (real output)

**1. Engineer's script — `node scripts/verify-issue5-schema.js`**
```
OK: JSON_SCHEMA.md matches the real SIMMER source (cond, fmtQty, resolve).
```
Exit 0. Covers all 22 section 7.1 rows, all 9 `fmtQty` cases, S1–S4 + extra. **PASS.**

**2. Branch / PR state**
- `git log --oneline -1 origin/docs/issue-5-v2-schema` = `1ffdac9` = current HEAD. Branch up to date.
- `gh pr view 30` → state **OPEN**, mergeable **MERGEABLE**, 2 commits (dbc0b11 + 1ffdac9). URL https://github.com/stefan-meindl/recipe-app/pull/30.

**3. `Design/` tracking**
- `git check-ignore -v Design` → exit 1 (tracked). `git ls-files Design` → **28 files**. Matches the doc's claim.

**4. Added edge-case script — `scripts/verify-issue5-edge.js`** (new, independent of the doc's rows)
```
OK: edge cases pass (fmtQty boundaries, cond short-circuit, resolve rendering/error contract).
```
Exit 0. Probes:
- `fmtQty` boundaries not in the doc: `999 g`→`1000 g`, `950 g`→`950 g`, `45/49 g`→step 5, `50 g`→step 10, `0 g`→`5 g`, `1000 ml`→`1 l`, `2500 ml`→`2.5 l`, `1050 g`→`1.1 kg`, `10000 g`→`10 kg`, `0.25/0.75/1.25/1.75 tsp`, `1.5/2.5` cloves plural, `1` clove singular. All match section 9.
- `cond` array short-circuit: `[mince=chicken, 5]` → **false** (element 1 false stops before the throwing element 2); `[mince=chicken, add:beer]` → **false**. `[rice=pot, 5]` and `[rice>pot, 5]` → **throw** (element 1 true, element 2 throws). This confirms the doc's "evaluation never short-circuits past an invalid element" caveat only applies when element 1 holds.
- `resolve` body rendering: S1 `Prep 1` segs = `["Crush the ", {garlic}, " and set it aside."]`; `needs` resolves to the step label `Prep 1`; `resolve` **throws** when a step is missing `b`.
- `fmtTime` helper sanity: `90`→`1 h 30 min`, `45`→`45 min`.

### Independent re-trace of one doc explanation (found a nit)

The doc's section 7.1 row `["rice=pot", ""]` says the result "follows element 1: `every`
short-circuits on the first false element". I re-ran it: element 1 (`rice=pot`) is
**true** and element 2 (`""`) is **true**, so `every()` evaluates **both** and returns
true by AND — **not** by short-circuiting. The *output* (true) is correct and the
engineer's script verifies it; the *explanation* is imprecise and could mislead a reader
into thinking a false element 1 is involved. Worth tightening before the doc ships.

### Minor issues (not blockers)

- Section 7.1 `["rice=pot", ""]` explanation (above): output correct, reasoning imprecise.
- The two Open points from the engineer notes remain open by design: the `ing` field's
  UI use, and the icon-name set / tag-filter semantics (design bundle not read).

### Anything the reviewer should scrutinise

- The `["rice=pot", ""]` short-circuit wording (above) — output verified, wording is off.
- The doc's section 7.1 "Current JS" column is now execution-proofed (both scripts pass on
  the real source); the reviewer can trust it rather than re-hand-tracing all 22 rows.

VERDICT: approved
Handoff to REVIEWER: the `["rice=pot", ""]` explanation wording (output is correct, the
short-circuit rationale is not). Everything else — 22 cond rows, 9 fmtQty cases, S1–S4,
the extra example, `Design/` tracking, PR #30 open/mergeable — is verified against the
real source.
