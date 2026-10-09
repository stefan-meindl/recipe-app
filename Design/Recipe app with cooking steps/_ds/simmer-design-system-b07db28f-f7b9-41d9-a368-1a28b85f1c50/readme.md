# Simmer Design System

Formerly "Subtle Gradient" — renamed for its primary product, a mobile recipe app. The name nods to the system's slow, warm glow gradients and the orange accent.

A clean, sharp, **mobile-first** system built on soft atmospheric gradients, an editorial serif voice and a single orange accent. It ships in two themes: a light theme (white canvas, warm cream panels) and an **OLED dark theme** where the canvas is pure `#000000`, layers are separated by thick outlines instead of shadows, and the same orange carries every action.

Outlines are thick (2px everywhere, 3px for emphasis) and rounded off: 8px buttons, 12px cards, round line caps on icons and indicators, rounded focus rings.

---

## Sources

- **`uploads/DESIGN (1).md`** — "Subtle-Gradient-Design-System" spec (Google `design.md` format, version alpha). Defines tokens (colours, type scale, radii, spacing), ~40 component specs, page coverage (homepage hero, product page, solutions page, article, contact form, services tiers), elevation, responsive rules and do's/don'ts. This is the ground truth for every numeric value here.
- **Brief from the user:** "Clean and sharp design system: dark mode is pure black for OLED devices. Orange accents adapt the color palette accordingly. Outlines are thick and rounded off."

No codebase, Figma file, logo, icon set, photography or slide template was provided.

### How the brief changed the source spec

| Source | This system |
|---|---|
| Dusk rose `#b8435a` primary, white label | Orange `#FF6B1A` primary, **black** label (7.4:1). Pressed `#E8590C`. |
| Link = primary | Link splits by theme: `#C2410C` on light (5.2:1 on white), `#FF7A33` on black |
| Glow palette in rose-peach (H 1–15°) | Same lightness/saturation, re-hued to orange (H 19–27°). Apricot, creams, inks unchanged. |
| "Dark-mode token values not yet defined" | Full OLED theme under `[data-theme="dark"]` |
| 1px borders, 2px emphasis | 2px borders, 3px emphasis; focus ring 3px + 2px offset |
| No icon spec | Lucide subset, constant 2px stroke, round caps (see Iconography) |

Everything else (type scale, radii, spacing, component anatomy, layout) is copied verbatim.

---

## Products represented

The primary product is a **mobile recipe app** (recipe browsing, step-by-step cooking with timers, shopping list); it is what the UI kit recreates. The source spec also describes a marketing website (homepage, product, solutions, article, contact, services tiers); its components remain in the library for a companion site.

---

## Index

```
styles.css                 ← link this one file (imports only)
tokens/
  fonts.css                @font-face — Instrument Serif, Inter, JetBrains Mono (self-hosted woff2)
  colors.css               base palette + semantic aliases; :root (light), [data-theme="dark"], [data-theme="light"]
  typography.css           families, font shorthands (--type-*), tracking
  spacing.css              --space-* scale + layout constants
  shape.css                radii, border widths, focus ring
  elevation.css            shadow ladder (light) / none (OLED)
  motion.css               durations + easing
  base.css                 element defaults, focus ring, .type-* utility classes
fonts/                     woff2 binaries (latin + latin-ext)
assets/icons/              81 Lucide SVGs (+ _lucide-data.json used to build Icon)
components/<group>/        React primitives: <Name>.jsx + .d.ts + .prompt.md + one @dsCard per folder
guidelines/                foundation specimen cards (Colors, Type, Spacing, Shape, Brand)
ui_kits/recipe-app/        mobile recipe app (iPhone frame): recipes, recipe, cooking mode, shopping list
thumbnail.html             homepage tile
SKILL.md                   Agent Skills entry point
uploads/DESIGN (1).md      original source spec
```

### Using it

```html
<link rel="stylesheet" href="styles.css">
<html data-theme="dark">   <!-- OLED theme; omit for light. Any element can carry data-theme. -->
```

React components are exposed on `window.SubtleGradientDesignSystem_b07db2` once `_ds_bundle.js` is loaded (`const { Button, HeroBand } = window.SubtleGradientDesignSystem_b07db2`). Style new UI with the **semantic** tokens (`--text-primary`, `--surface-warm`, `--border-strong`, `--accent`…) so it follows the theme; base `--color-*` tokens exist for specimens and gradients.

---

## Components

All built from the source inventory, grouped by folder.

- **icons/** — `Icon` (+ `ICON_NAMES`)
- **buttons/** — `Button` (primary · dark · secondary · cream · on-cream · link), `AppDownloadBadge`
- **badges/** — `Badge` (primary · cream · dark), `PromoBanner`
- **cards/** — `Card` (base · feature · cream · cream-soft · feature-product · photographic)
- **pricing/** — `PricingCard` (standard · featured)
- **forms/** — `TextInput`, `TextArea`, `ContactFormPanel`
- **tabs/** — `PillTabs`, `SegmentedTabs`
- **navigation/** — `TopNav`
- **footer/** — `Footer`, `FooterLink`
- **code/** — `CodeBlock` (block · mockup)
- **content/** — `FeatureIconTile`, `IndustryTile`, `TestimonialCard`
- **stats/** — `StatCell`, `LogoWall`
- **faq/** — `FaqItem`
- **signature/** — `HeroBand`, `GradientStripe`, `CtaBanner`
- **cooking/** — `CookingStep`, `StepTimer`, `Ingredient` (mobile recipe steps: orange tap-to-start timer, tappable ingredients with Amount / Tips popover)
- **shopping/** — `ShoppingList`, `ShoppingListItem` (grouped, checkable list with basket section)

Source → component map: `button-*` → Button variants · `card-*` → Card variants · `pricing-card(-featured)` → PricingCard · `text-input(-focused)` → TextInput · `text-area` → TextArea · `contact-form-panel` → ContactFormPanel · `pill-tab` → PillTabs · `segmented-tab` → SegmentedTabs · `badge-*` → Badge · `promo-banner` → PromoBanner · Top Navigation → TopNav · `footer-region` / `footer-link` → Footer / FooterLink · `code-block(-header)` → CodeBlock · `feature-icon-tile` → FeatureIconTile · `industry-tile` → IndustryTile · `customer-testimonial-card` → TestimonialCard · `stat-cell` → StatCell · `logo-wall-item` → LogoWall · `faq-accordion-item` → FaqItem · `app-download-badge` → AppDownloadBadge · `hero-band-gradient` → HeroBand · `gradient-stripe-band` → GradientStripe · `cta-banner-cream` → CtaBanner.

Starting points: `HeroBand`, `PricingCard`, `ContactFormPanel`, `CtaBanner`, `CookingStep`.

Cooking and shopping components were requested directly by the user (not in the source spec). Mobile rules: touch targets ≥44px, list rows ≥56px.

### Intentional additions

- **`Icon`** — wrapper for the copied Lucide glyphs so every component draws icons at the same 2px stroke.
- **OLED theme scopes** (`[data-theme="dark"]`, `[data-theme="light"]`) — required by the brief; the source lists dark mode as a gap.
- **Pressed states for non-primary buttons** — the source documents only primary pressed; others deepen one surface step so every button gives feedback.
- **Syntax colours** (`--code-*`) — the source shows highlighted code but defines no palette.
- **`GradientStripe` `stepped` / `vertical` options** — crisp blocks built from the source's `block-5/6/7` stops; vertical fades into the footer.
- **`CodeBlock variant="mockup"`** — the source distinguishes IDE mockups (12px radius, level-3 shadow) from code blocks (8px, flat).

---

## Content fundamentals

**Voice.** Calm, editorial and matter-of-fact. Copy states what the product does and stops. No hype words, no exclamation marks, no emoji.

**Headlines are short declarative sentences that end with a full stop.** From the source: "Ideas, brought into focus." · "Talk to our team." · "Start building today." Keep display headlines under ~6 words; they are set large in serif and need room.

**Casing.** Sentence case everywhere — headlines, buttons, nav, tabs, labels: "Get started", "Contact sales", "Read the docs". Eyebrows are written in sentence case and uppercased by the `micro-uppercase` style, never typed in caps.

**Person.** Address the reader as *you* ("wait for your review"); the company is *we/our* ("Talk to our team", "We reply within one business day"). Don't use *I*.

**Buttons** are 1–3 word verb phrases: Get started · Start free · Contact sales · Talk to sales · Send message · See the product. One primary action per view.

**Body copy.** One idea per sentence, 1–2 sentences per feature, hero subtitles a single sentence of ~10–16 words. No serial comma ("plans, logs and diffs").

**Numbers.** Stats are compact figures with a short lowercase label: "3×" faster from issue to merged change · "40%" fewer manual review steps · "24/7". Use the real multiplication sign `×`, never "x". Only publish sourced figures.

**Punctuation & symbols.** Curly quotes in testimonials. A middle dot `·` separates meta ("Oct 2, 2026 · 6 min read"). Arrows are icons, not typed characters.

**Examples**

| Use | Example |
|---|---|
| Hero | Ideas, brought into focus. / Agents that read your codebase, propose changes and wait for your review. |
| Page headline | Talk to our team. |
| Closing CTA | Start building today. / Free for your first workspace. No card required. |
| Feature | **Review the diff** — The agent works in a branch and opens a pull request with its reasoning. |
| Promo strip | Agents are now generally available. Read the announcement → |
| Helper | We reply within one business day. |

---

## Visual foundations

**Colour.** One accent: orange `#FF6B1A`, used for primary CTAs, active states (tab indicator, focus ring, featured outline) and the gradient stripe — nothing else. Labels on orange are black. Neutrals are the source's ink scale on light and its inverse on black (`#FAFAFA` text, not pure white, to avoid OLED smear). Warm cream (`#FBF5EF`) hosts form panels, feature bands, CTA panels and the footer; in OLED it becomes a warm near-black (`#110B07`) with a warm brown outline. No other hues — no blue links, no green/red status colours.

**Type.** Instrument Serif (regular only) for hero (84px), section openers (64px), page headlines (52px) and stats (56px); Inter for everything else; JetBrains Mono for code. Tight display leading (1.05–1.15) with negative tracking that relaxes as size drops; generous body leading (1.55). The serif/sans contrast is the brand voice — never set a hero in Inter.

**Spacing & layout.** 4px base, 8px rhythm. 1280px container with 32px gutters; marketing sections breathe at 96px, content pages at 64px, hero at 120px. Cards pad 24px (compact) or 32px (feature/form). Page skeleton is fixed: promo strip → sticky 64px nav → content → gradient stripe → footer.

**Backgrounds.** No photography, textures, patterns or illustrations. Depth comes from two gradients only: the **hero gradient** (135°, apricot → peach on light; near-black → ember on OLED) and the **gradient stripe** (orange → glow → apricot → footer colour) that closes every page. Everything else is a flat solid surface.

**Borders & outlines.** Thick and rounded: every outline is 2px (`--border-width`); featured cards, active indicators and focus use 3px. Input focus thickens 2→3px with an inset ring so nothing shifts. The keyboard focus ring is a 3px orange outline offset 2px, following the element's radius. Dividers are 2px too.

**Corner radii.** 8px buttons, inputs, code blocks; 12px cards, panels, mockups; 16–20px for larger emphasis panels; full pills only for badges and pill tabs. Buttons are never pills.

**Cards.** 12px radius, 2px subtle outline, flat. Cream cards swap to a warm outline. Only `feature-product` cards and IDE mockups carry shadow, and only in light. No coloured left borders, no nested cards.

**Shadows.** Light theme uses the source ladder (0 flat → 4 modal, all low-alpha black). OLED theme sets every shadow to `none` — on pure black they are invisible — and expresses elevation by stepping the outline up (`--border-subtle` → `--border-default` → `--border-strong`).

**Transparency & blur.** Not used. No glass, no backdrop blur, no scrims; text sits on solid surfaces or directly on the soft hero gradient, which is light (or dark) enough for full-strength ink. The only alpha in the system is the code-header divider (white 8%) and the modal `--overlay`.

**Interaction.** The source has a **no-hover policy**: components define default, pressed, focus and disabled states only. Pressed = the fill deepens one step (orange → `#E8590C`, inverse → tint, cream → deeper cream); text links underline (2px, 3px offset) while pressed; tiles strengthen their outline. No shrink, no lift, no glow.

**Motion.** Minimal: colour/border transitions at 150ms ease, accordion chevrons rotate at 200ms. No entrance animations, parallax, bounces or looping motion.

**Imagery.** None supplied. If photography is added, the source asks for original/owned landscape imagery toned to the glow palette — warm, soft, low contrast — full-bleed in the hero with no framing. In OLED, prefer imagery with deep blacks so it sits on the canvas without a box.

**Dark (OLED) theme rules.** Canvas `#000`; large regions (page, footer) stay pure black; only small panels (cream cards, code) lift slightly. Same orange, same radii, same 2px outlines — the outline system is what makes the dark theme work.

---

## Iconography

- **Set:** [Lucide](https://lucide.dev) (ISC licence) — **a substitution**: the source spec names no icon system. Lucide was chosen because its round caps/joins match the "thick and rounded" brief.
- **Files:** 81 glyphs copied programmatically from `lucide-static@1.53.0` into `assets/icons/*.svg`; the same path data is embedded in `components/icons/Icon.jsx`. Need more? Copy from the same package, or use the CDN (`https://unpkg.com/lucide-static@1.53.0/icons/<name>.svg`).
- **Rendering:** outline only, never filled; `stroke-linecap`/`linejoin: round`; the `Icon` component holds the rendered stroke at **2px at any size** so glyphs match the 2px outlines. Sizes: 14–16px inline with text, 18–20px in UI, 24px in feature/industry tiles, 32px for confirmation states.
- **Colour:** inherit text colour; use `--text-accent` only inside cream feature tiles, checklists and success states.
- **No icon font, no PNG icons, no emoji.** Unicode is limited to `×` in stats and `·` as a separator.
- **No brand/social logos:** Lucide removed them and none were supplied — social links are plain text (GitHub · X · LinkedIn · YouTube). App badges use generic device glyphs; use official store artwork where required.
- **No logo was supplied.** The wordmark is the system name set in plain Instrument Serif ("Simmer"); don't draw a mark.

---

## UI kits

- **`ui_kits/recipe-app/`** — the mobile recipe app: Recipes (search + category filters), Recipe (ingredients / steps, add to shopping list), Cooking mode (one step at a time with timer and ingredient popovers), Shopping list. Light by default with an OLED toggle. `screens.html` shows all four side by side. The source spec's marketing components (hero, pricing, footer…) remain in `components/` but have no kit.

---

## Known gaps (carried from the source or the brief)

- No logo, product name, photography or real copy — UI kit content is placeholder.
- Form validation (error/success colours) undefined; the kit's contact form shows a success panel built from existing tokens only.
- Source copy mentions a "black-pill" nav CTA while also forbidding pill buttons; the nav uses the 8px `dark` button.
- Fonts are the source's Google Fonts (Instrument Serif, Inter, JetBrains Mono), self-hosted — no substitution.
