// Verify JSON_SCHEMA.md against the real source of truth:
//   Design/Recipe app with cooking steps/simmer-data.js
//
// The schema document claims that its section 7.1 condition rows, section 9
// fmtQty examples, and section 10 worked selections (S1-S4) were *executed*
// against SIMMER.cond / SIMMER.fmtQty / SIMMER.resolve. This script reproduces
// that execution so the claim can be re-checked at home (no hand-tracing).
//
// Run: node scripts/verify-issue5-schema.js
// Requires: node >= 18 (uses vm.createContext / runInContext).

const vm = require('vm');
const fs = require('fs');
const path = require('path');

const SRC = path.join(__dirname, '..', 'Design', 'Recipe app with cooking steps', 'simmer-data.js');

function loadSIMMER() {
  const src = fs.readFileSync(SRC, 'utf8');
  const sandbox = { window: {} }; // the source assigns to `window`, not `global`
  vm.createContext(sandbox);
  vm.runInContext(src, sandbox);
  if (!sandbox.window.SIMMER) throw new Error('SIMMER not exported (window.SIMMER missing)');
  return sandbox.window.SIMMER;
}

function assertEq(label, got, exp) {
  if (got !== exp) {
    FAILURES.push({ label, expected: exp, got });
    return false;
  }
  return true;
}

function main() {
  const SIMMER = loadSIMMER();
  const FAILURES = [];

  // ---- Section 7.1: invalid-condition rows (selection from the doc) ----
  const sel = { choice: { mince: 'beef', rice: 'pot' }, add: {} };
  const rows = [
    ['', 'hold'],
    ['   ', 'hold'],
    ['rice>pot', 'hold'],
    ['rice=', 'nothold'],
    ['=pot', 'nothold'],
    ['add:', 'nothold'],
    ['!add', 'hold'],
    ['rice = pot', 'nothold'],
    ['rice=pot=x', 'hold'],
    ['Rice=pot', 'nothold'],
    ['mnce=beef', 'nothold'],
    ['mnce!=beef', 'hold'],
    ['mince=chicken', 'nothold'],
    ['mince!=chicken', 'hold'],
    ['add:bear', 'nothold'],
    ['!add:bear', 'hold'],
    ['add:mince', 'nothold'],
    [[], 'hold'],
    [['rice=pot', ''], 'hold'],
    [['rice=pot', 5], 'throws'],
    [5, 'throws'],
    [null, 'hold'],
    [0, 'hold'],
    [false, 'hold'],
  ];
  for (const [inp, expected] of rows) {
    let actual;
    try {
      actual = SIMMER.cond(inp, sel) ? 'hold' : 'nothold';
    } catch {
      actual = 'throws';
    }
    assertEq(`7.1 ${JSON.stringify(inp)}`, actual, expected);
  }

  // ---- Section 9: fmtQty ----
  const fmt = [
    [1000, 'g', '1 kg'],
    [1500, 'g', '1.5 kg'],
    [30, 'g', '30 g'],
    [52, 'g', '50 g'],
    [3, 'g', '5 g'],
    [0.1, 'tsp', '¼ tsp'],
    [3, 'tbsp', '3 tbsp'],
    [3, ['clove', 'cloves'], '3 cloves'],
    [4.5, ['clove', 'cloves'], '4½ cloves'],
  ];
  for (const [q, u, exp] of fmt) assertEq(`9 fmtQty(${q}, ${JSON.stringify(u)})`, SIMMER.fmtQty(q, u), exp);
  assertEq("9 fmtQty(3, '')", SIMMER.fmtQty(3, ''), '3');

  // ---- Section 10: worked selections against the trimmed chilli example ----
  const recipe = {
    id: 'chilli-mini', title: 'Chilli (trimmed example)', category: 'Mains', tags: ['Spicy', 'One-pot'],
    time: 90, difficulty: 'Easy', base: 4, icon: 'flame', summary: 'x',
    variations: [
      { id: 'mince', kind: 'swap', label: 'Mince', options: [{ v: 'beef', l: 'Beef' }, { v: 'plant', l: 'Plant-based' }], def: 'beef' },
      { id: 'beer', kind: 'add', label: 'Dark beer', d: 'Replaces half the stock.', ing: 'be' },
    ],
    methods: [{ id: 'rice', label: 'Rice', q: 'How do you want to cook the rice?', options: [
      { v: 'pot', l: 'In a pot', icon: 'flame', time: '18 min', d: 'Absorption method on the hob.' },
      { v: 'microwave', l: 'Microwave', icon: 'zap', time: '12 min', d: 'Hands-off, no pot to wash.' },
    ] }],
    ingredients: [
      { id: 'oi', name: 'Olive oil', q: 2, u: 'tbsp', aisle: 'Pantry' },
      { id: 'ga', name: 'Garlic', q: 3, u: ['clove', 'cloves'], note: 'crushed', aisle: 'Produce' },
      { id: 'mb', name: 'Beef mince', q: 500, u: 'g', if: 'mince=beef', aisle: 'Meat' },
      { id: 'mp', name: 'Plant-based mince', q: 400, u: 'g', if: 'mince=plant', aisle: 'Chilled' },
      { id: 'st', name: 'Stock', q: 300, u: 'ml', alt: [['add:beer', 150]], aisle: 'Pantry' },
      { id: 'be', name: 'Dark beer', q: 200, u: 'ml', if: 'add:beer', aisle: 'Drinks' },
      { id: 'ri', name: 'Long-grain rice', q: 300, u: 'g', aisle: 'Pantry' },
      { id: 'wa', name: 'Water', q: 450, u: 'ml', alt: [['rice=microwave', 600]], note: 'for the rice' },
    ],
    steps: [
      { id: 'p-veg', ph: 'prep', t: 'Crush the garlic', d: 60, b: 'Crush the {ga:garlic} and set it aside.' },
      { id: 'c-brown', ph: 'cook', if: 'mince=beef', t: 'Brown the mince', d: 480, needs: ['p-veg'], b: 'Heat the {oi:olive oil}, add the {mb:beef mince} and the garlic, and break it up until browned all over.' },
      { id: 'c-brown', ph: 'cook', if: 'mince=plant', t: 'Brown the mince', d: 300, needs: ['p-veg'], b: 'Heat the {oi:olive oil}, add the {mp:plant-based mince} and the garlic, and brown lightly.' },
      { id: 'c-liquid', ph: 'cook', if: '!add:beer', t: 'Add the stock', d: 120, b: 'Pour in the {st:stock} and bring to a simmer.' },
      { id: 'c-liquid', ph: 'cook', if: 'add:beer', t: 'Add the beer and stock', d: 240, b: 'Pour in the {be:dark beer} and let it bubble for 2 minutes, then add the {st:stock} and bring to a simmer.' },
      { id: 'r-pot', ph: 'cook', if: 'rice=pot', t: 'Cook the rice', d: 1080, b: 'Put the {ri:rice} and {wa:water} in a pot with a pinch of salt. Bring to the boil, cover and cook on the lowest heat for 12 minutes, then rest off the heat for 6.' },
      { id: 'r-mw', ph: 'cook', if: 'rice=microwave', t: 'Microwave the rice', d: 720, b: 'Put the {ri:rice} and {wa:water} in a large microwave-safe bowl. Cover loosely, cook on full power for 10 minutes, then let it stand for 2.' },
      { id: 'c-fin', ph: 'cook', t: 'Serve', d: 60, b: 'Serve the chilli over the rice.' },
    ],
  };

  const ingLine = (r) => r.ingredients.map((i) => `${i.name} ${i.amount}${i.variation ? ' (variation)' : ''}`).join('; ');
  const stepLine = (r) => r.steps.map((s) => `${s.label} | ${s.id} | ${s.t} | ${s.d} | ${s.needs.map((n) => n.label).join('') || '-'}`).join('\n');

  const S1 = SIMMER.resolve(recipe, 4, { choice: { mince: 'beef', rice: 'pot' }, add: {} });
  assertEq('S1 ingredients', ingLine(S1), 'Olive oil 2 tbsp; Garlic 3 cloves; Beef mince 500 g (variation); Stock 300 ml; Long-grain rice 300 g; Water 450 ml');
  assertEq('S1 steps', stepLine(S1), 'Prep 1 | p-veg | Crush the garlic | 60 | -\nStep 1 | c-brown | Brown the mince | 480 | Prep 1\nStep 2 | c-liquid | Add the stock | 120 | -\nStep 3 | r-pot | Cook the rice | 1080 | -\nStep 4 | c-fin | Serve | 60 | -');

  const S2 = SIMMER.resolve(recipe, 4, { choice: { mince: 'plant', rice: 'pot' }, add: {} });
  assertEq('S2 Step 1', stepLine(S2).split('\n')[1], 'Step 1 | c-brown | Brown the mince | 300 | Prep 1');

  const S3 = SIMMER.resolve(recipe, 4, { choice: { mince: 'beef', rice: 'pot' }, add: { beer: true } });
  assertEq('S3 ingredients', ingLine(S3), 'Olive oil 2 tbsp; Garlic 3 cloves; Beef mince 500 g (variation); Stock 150 ml; Dark beer 200 ml (variation); Long-grain rice 300 g; Water 450 ml');
  assertEq('S3 steps', stepLine(S3), 'Prep 1 | p-veg | Crush the garlic | 60 | -\nStep 1 | c-brown | Brown the mince | 480 | Prep 1\nStep 2 | c-liquid | Add the beer and stock | 240 | -\nStep 3 | r-pot | Cook the rice | 1080 | -\nStep 4 | c-fin | Serve | 60 | -');

  const S4 = SIMMER.resolve(recipe, 6, { choice: { mince: 'beef', rice: 'microwave' }, add: {} });
  assertEq('S4 ingredients', ingLine(S4), 'Olive oil 3 tbsp; Garlic 4½ cloves; Beef mince 750 g (variation); Stock 450 ml; Long-grain rice 450 g; Water 900 ml');
  assertEq('S4 steps', stepLine(S4), 'Prep 1 | p-veg | Crush the garlic | 60 | -\nStep 1 | c-brown | Brown the mince | 480 | Prep 1\nStep 2 | c-liquid | Add the stock | 120 | -\nStep 3 | r-mw | Microwave the rice | 720 | -\nStep 4 | c-fin | Serve | 60 | -');

  const extra = SIMMER.resolve(recipe, 4, { choice: { mince: 'plant', rice: 'microwave' }, add: { beer: true } });
  assertEq('extra ingredients', ingLine(extra), 'Olive oil 2 tbsp; Garlic 3 cloves; Plant-based mince 400 g (variation); Stock 150 ml; Dark beer 200 ml (variation); Long-grain rice 300 g; Water 600 ml');

  if (FAILURES.length) {
    console.error(`FAIL: ${FAILURES.length} assertion(s) did not match the schema document.\n`);
    console.error(JSON.stringify(FAILURES, null, 2));
    process.exit(1);
  }
  console.log('OK: JSON_SCHEMA.md matches the real SIMMER source (cond, fmtQty, resolve).');
}

main();
