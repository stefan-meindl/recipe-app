// Edge-case checks beyond the doc's own worked examples.
// Run: node scripts/verify-issue5-edge.js
// Independent of the doc's section 7.1 / 9 / 10 rows: these probe boundaries,
// array short-circuit behaviour, body rendering and the error contract.

const vm = require('vm');
const fs = require('fs');
const path = require('path');

const SRC = path.join(__dirname, '..', 'Design', 'Recipe app with cooking steps', 'simmer-data.js');

function loadSIMMER() {
  const src = fs.readFileSync(SRC, 'utf8');
  const sandbox = { window: {} };
  vm.createContext(sandbox);
  vm.runInContext(src, sandbox);
  if (!sandbox.window.SIMMER) throw new Error('SIMMER not exported');
  return sandbox.window.SIMMER;
}

const SIMMER = loadSIMMER();
const FAILURES = [];
function eq(label, got, exp) {
  if (got !== exp) { FAILURES.push({ label, expected: exp, got }); return false; }
  return true;
}
function throws(label, fn) {
  try { fn(); FAILURES.push({ label, expected: 'throws', got: 'no-throw' }); return false; }
  catch { return true; }
}

// ---- fmtQty boundaries (section 9) ----
eq('fmtQty(999,g) boundary', SIMMER.fmtQty(999, 'g'), '1000 g');
eq('fmtQty(950,g)', SIMMER.fmtQty(950, 'g'), '950 g');
eq('fmtQty(45,g) step5', SIMMER.fmtQty(45, 'g'), '45 g');
eq('fmtQty(49,g) step5', SIMMER.fmtQty(49, 'g'), '50 g');
eq('fmtQty(50,g) step10', SIMMER.fmtQty(50, 'g'), '50 g');
eq('fmtQty(0,g) floors to 5', SIMMER.fmtQty(0, 'g'), '5 g');
eq('fmtQty(1000,ml)', SIMMER.fmtQty(1000, 'ml'), '1 l');
eq('fmtQty(2500,ml)', SIMMER.fmtQty(2500, 'ml'), '2.5 l');
eq('fmtQty(1050,g)', SIMMER.fmtQty(1050, 'g'), '1.1 kg');
eq('fmtQty(10000,g)', SIMMER.fmtQty(10000, 'g'), '10 kg');
eq('fmtQty(0.25,tsp)', SIMMER.fmtQty(0.25, 'tsp'), '¼ tsp');
eq('fmtQty(0,tsp) floors to 0.25', SIMMER.fmtQty(0, 'tsp'), '¼ tsp');
eq('fmtQty(0.75,tsp)', SIMMER.fmtQty(0.75, 'tsp'), '¾ tsp');
eq('fmtQty(1.25,tsp)', SIMMER.fmtQty(1.25, 'tsp'), '1¼ tsp');
eq('fmtQty(1.75,tsp)', SIMMER.fmtQty(1.75, 'tsp'), '1¾ tsp');
eq('fmtQty(1,clove string)', SIMMER.fmtQty(1, 'clove'), '1 clove');
eq('fmtQty(1.5,[clove,cloves])', SIMMER.fmtQty(1.5, ['clove', 'cloves']), '1½ cloves');
eq('fmtQty(1,[clove,cloves]) singular', SIMMER.fmtQty(1, ['clove', 'cloves']), '1 clove');
eq('fmtQty(2.5,[clove,cloves])', SIMMER.fmtQty(2.5, ['clove', 'cloves']), '2½ cloves');
eq("fmtQty(3,'') number alone", SIMMER.fmtQty(3, ''), '3');

// ---- cond boundaries (section 7.1) ----
const sel = { choice: { mince: 'beef', rice: 'pot' }, add: {} };
eq('cond valid mince=beef', SIMMER.cond('mince=beef', sel), true);
eq('cond valid mince!=beef plant', SIMMER.cond('mince!=beef', { choice: { mince: 'plant' }, add: {} }), true);
eq('cond add:beer on', SIMMER.cond('add:beer', { choice: {}, add: { beer: true } }), true);
eq('cond !add:beer on', SIMMER.cond('!add:beer', { choice: {}, add: { beer: true } }), false);
eq('cond AND both hold', SIMMER.cond(['mince=beef', 'add:beer'], { choice: { mince: 'beef' }, add: { beer: true } }), true);
eq('cond AND one off', SIMMER.cond(['mince=beef', 'add:beer'], { choice: { mince: 'beef' }, add: {} }), false);
eq('cond empty array holds', SIMMER.cond([], {}), true);

// Array short-circuit: a false element 1 must STOP before a throwing element 2.
eq('cond [false,5] short-circuits to false', SIMMER.cond(['mince=chicken', 5], sel), false);
eq('cond [false,5] with beer-off variant', SIMMER.cond(['mince=chicken', 'add:beer'], sel), false);
// A true element 1 followed by a throwing element 2 must throw.
throws('cond [true,5] throws', () => SIMMER.cond(['rice=pot', 5], sel));
throws('cond [holds,5] throws', () => SIMMER.cond(['rice>pot', 5], sel));

// ---- resolve: body rendering + needs + error contract ----
const recipe = JSON.parse(fs.readFileSync(path.join(__dirname, '..', 'JSON_SCHEMA.md'), 'utf8')
  .match(/```json\n([\s\S]*?)```/)[1]);

// S1 body of Prep 1: plain / ga / plain.
const S1 = SIMMER.resolve(recipe, 4, { choice: { mince: 'beef', rice: 'pot' }, add: {} });
const p1 = S1.steps[0];
eq('S1 Prep1 seg count', p1.segs.length, 3);
eq('S1 Prep1 seg0 plain', p1.segs[0].t, 'Crush the ');
eq('S1 Prep1 seg1 shown text', p1.segs[1].t, 'garlic');
eq('S1 Prep1 seg1 ing id', p1.segs[1].ing.id, 'ga');
eq('S1 Prep1 seg2 plain', p1.segs[2].t, ' and set it aside.');

// needs resolves to the step label.
eq('S1 c-brown needs label', S1.steps[1].needs[0].label, 'Prep 1');

// resolve throws when a step is missing its body.
const noBody = JSON.parse(JSON.stringify(recipe));
noBody.steps.push({ id: 'x', ph: 'cook', t: 'Bad', d: 10 });
throws('resolve throws on step missing b', () => SIMMER.resolve(noBody, 4, { choice: { mince: 'beef', rice: 'pot' }, add: {} }));

// fmtTime helper (exported, not in doc's worked rows) — sanity only.
eq('fmtTime(90)', SIMMER.fmtTime(90), '1 h 30 min');
eq('fmtTime(45)', SIMMER.fmtTime(45), '45 min');

if (FAILURES.length) {
  console.error(`FAIL: ${FAILURES.length} edge-case assertion(s).\n`);
  console.error(JSON.stringify(FAILURES, null, 2));
  process.exit(1);
}
console.log('OK: edge cases pass (fmtQty boundaries, cond short-circuit, resolve rendering/error contract).');
