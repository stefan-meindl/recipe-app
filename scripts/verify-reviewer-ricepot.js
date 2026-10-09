// REVIEWER independent check: does cond(["rice=pot", ""]) short-circuit or evaluate both?
// Selection: { choice: { mince: "beef", rice: "pot" }, add: {} }
const fs = require("fs");
const vm = require("vm");
const path = "Design/Recipe app with cooking steps/simmer-data.js";
const src = fs.readFileSync(path, "utf8");
const sandbox = { window: {} };
vm.createContext(sandbox);
vm.runInContext(src, sandbox);
const SIMMER = sandbox.window.SIMMER;
const sel = { choice: { mince: "beef", rice: "pot" }, add: {} };
const r_empty = SIMMER.cond("", sel);
const r_ricepot = SIMMER.cond("rice=pot", sel);
const r_both = SIMMER.cond(["rice=pot", ""], sel);
console.log('cond("")            =', JSON.stringify(r_empty));
console.log('cond("rice=pot")    =', JSON.stringify(r_ricepot));
console.log('cond(["rice=pot",""]) =', JSON.stringify(r_both));
console.log('element1 holds?     =', r_ricepot === true);
console.log('element2 holds?     =', r_empty === true);
console.log('evaluated BOTH?     =', r_ricepot === true && r_empty === true && r_both === true);
console.log('result "holds"?     =', r_both === true);
