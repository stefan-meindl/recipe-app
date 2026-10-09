// Verifies the REVIEWER fix: JSON_SCHEMA.md line 155's summary statement now
// matches the real `cond()` short-circuit behaviour (Array.prototype.every).
//
// The doc's summary statement (line 155) must NOT claim full-array validation.
// The doc's section 7.1 table (line 179) already states the correct behaviour.
// This script checks both: the summary is corrected, and the source behaves
// the way the table (and corrected summary) say.
//
// Usage: node scripts/verify-issue5-shortcircuit-fix.js
// Exit 0 = OK, exit 1 = mismatch.

const fs = require('fs');
const vm = require('vm');
const path = require('path');

let failures = [];
function assert(label, cond) {
  if (!cond) failures.push(label);
}

// --- 1. Read the doc and check the summary statement (line 155). ---
const docPath = path.resolve(__dirname, '..', 'JSON_SCHEMA.md');
const lines = fs.readFileSync(docPath, 'utf8').split('\n');
const summary = lines[154]; // 0-based index of line 155

// The buggy claim must be gone.
assert(
  'summary no longer claims "whole array is validated"',
  !/whole array is validated before it is evaluated/.test(summary)
);
assert(
  'summary no longer claims "Elements are never skipped"',
  !/Elements are never skipped/.test(summary)
);

// The corrected claim must be present (short-circuit via every).
assert(
  'summary now states short-circuit / every',
  /short.circuit|every/.test(summary)
);

// --- 2. Load the real SIMMER source and check cond() short-circuit behaviour. ---
const srcPath = path.resolve(
  __dirname,
  '..',
  'Design',
  'Recipe app with cooking steps',
  'simmer-data.js'
);
const src = fs.readFileSync(srcPath, 'utf8');
const sandbox = { window: {} };
vm.createContext(sandbox);
vm.runInContext(src, sandbox);

if (!sandbox.window.SIMMER) {
  console.error('FAIL: SIMMER not exported from source');
  process.exit(1);
}
const { SIMMER } = sandbox.window;

// A false element 1 must short-circuit BEFORE a throwing element 2 -> false.
const sel = { choice: { rice: 'pot' }, add: {} };
assert(
  'cond([mince=chicken, 5]) === false (short-circuit, no throw)',
  SIMMER.cond(['mince=chicken', 5], sel) === false
);
assert(
  'cond([mince=chicken, add:beer]) === false (short-circuit, no throw)',
  SIMMER.cond(['mince=chicken', 'add:beer'], sel) === false
);

// A held element 1 followed by a throwing element 2 must throw.
function throws(fn) {
  try {
    fn();
    return false;
  } catch {
    return true;
  }
}
assert(
  'cond([rice=pot, 5]) throws (element 1 holds, element 2 throws)',
  throws(() => SIMMER.cond(['rice=pot', 5], sel))
);

// --- 3. Report. ---
if (failures.length > 0) {
  console.error('FAIL:');
  failures.forEach((f) => console.error('  - ' + f));
  process.exit(1);
}
console.log(
  'OK: JSON_SCHEMA.md line 155 summary now matches the real cond() short-circuit behaviour.'
);
process.exit(0);
