# Issues → Workflows

How a GitHub Issue is routed through the station's build/test/review lines.

## Board labels (status)

| Label | Meaning |
|-------|---------|
| `todo` | Queued, not started |
| `in-progress` | Being worked on |
| `done` | Complete |

## Work labels (type / owner)

| Label | Meaning |
|-------|---------|
| `feature` | New feature or request |
| `design` | Design work |
| `docs` | Documentation improvements |
| `test` | Test infrastructure |

## Workflow mapping

Each work label points a issue at a workflow line. The station is **local-first and currently has automation OFF**, so labels define the mapping — the line only fires when a job is sent to it (manually, or via a trigger you wire up).

| Label | Workflow line | Path |
|-------|---------------|------|
| `engineering` | `p138` | ENGINEER → TESTER → REVIEWER → **OUTBOX** |
| `devops` | `p158` | ENGINEER → TESTER → REVIEWER → **DEVOPS BOT** |

### How to route an issue

1. Open the issue in GitHub.
2. Add the type label (`feature`, `design`, `docs`, `test`) and one work label:
   - `engineering` → sends it through **p138** (build → test → review → outbox).
   - `devops` → sends it through **p158** (build → test → review → deploy).
3. The station routes it to the right specialist and runs the line.
4. Move the status label through `todo` → `in-progress` → `done` as it progresses.

### Triggering (optional, not yet set up)

By default the lines run when a job is sent to them. To make them fire automatically, wire a trigger onto the line (channel, folder, or GitHub webhook). Labels are the reliable webhook trigger (`issues` → `labeled`); Projects-column moves are historically flaky.
