#!/usr/bin/env bash
# Sync the live ngrok tunnel URL to the GitHub repo webhook.
#
# The free-tier ngrok URL changes on every tunnel restart, so the webhook
# registered on the repo can go stale. This script reads the current public
# URL from the ngrok local API and updates the webhook config if it differs.
#
# Usage:
#   webhook_sync.sh            # one-time sync
#   webhook_sync.sh --watch    # loop, re-sync every N seconds
#
# Configure via environment:
#   WEBHOOK_HOOK_ID   GitHub webhook hook id (default: 694844525)
#   WEBHOOK_REPO      owner/repo (default: detected from git remote)
#   NGROK_API_URL     ngrok local API (default: http://127.0.0.1:4040/api/tunnels)
#   SYNC_INTERVAL     seconds between syncs in --watch mode (default: 60)
#   GITHUB_WEBHOOK_SECRET  secret used on the webhook (default: change-me-secret)

set -euo pipefail

WEBHOOK_HOOK_ID="${WEBHOOK_HOOK_ID:-694844525}"
WEBHOOK_REPO="${WEBHOOK_REPO:-}"
NGROK_API_URL="${NGROK_API_URL:-http://127.0.0.1:4040/api/tunnels}"
SYNC_INTERVAL="${SYNC_INTERVAL:-60}"
GITHUB_WEBHOOK_SECRET="${GITHUB_WEBHOOK_SECRET:-change-me-secret}"

if [[ -z "$WEBHOOK_REPO" ]]; then
  WEBHOOK_REPO="$(git -C "$(git rev-parse --show-toplevel)" remote get-url origin 2>/dev/null \
    | sed -E 's#(git@|https?://)[^/]+/##; s#\.git$##')"
fi

if [[ -z "$WEBHOOK_REPO" ]]; then
  echo "ERROR: could not determine repo (set WEBHOOK_REPO=owner/repo)" >&2
  exit 1
fi

current_ngrok_url() {
  curl -s --max-time 5 "$NGROK_API_URL" \
    | python3 -c "import sys,json
try:
    d=json.load(sys.stdin)
    print(d['tunnels'][0]['public_url'] if d.get('tunnels') else '')
except Exception:
    print('')" 2>/dev/null
}

current_webhook_url() {
  gh api "repos/$WEBHOOK_REPO/hooks/$WEBHOOK_HOOK_ID" --jq '.config.url' 2>/dev/null
}

sync_once() {
  local ngrok_url webhook_url
  ngrok_url="$(current_ngrok_url)"
  if [[ -z "$ngrok_url" ]]; then
    echo "[$(date '+%F %T')] ngrok tunnel not reachable; skipping"
    return 0
  fi
  webhook_url="$(current_webhook_url)"
  if [[ "$webhook_url" == "$ngrok_url" ]]; then
    echo "[$(date '+%F %T')] already in sync -> $ngrok_url"
    return 0
  fi
  echo "[$(date '+%F %T')] updating webhook: '$webhook_url' -> '$ngrok_url'"
  gh api "repos/$WEBHOOK_REPO/hooks/$WEBHOOK_HOOK_ID" -X PATCH \
    --input - <<EOF
{"events":["issues"],"active":true,"config":{"url":"$ngrok_url","secret":"$GITHUB_WEBHOOK_SECRET","content_type":"json","insecure_ssl":"0"}}
EOF
  echo "[$(date '+%F %T')] updated."
}

if [[ "${1:-}" == "--watch" ]]; then
  echo "[$(date '+%F %T')] watching ngrok, interval ${SYNC_INTERVAL}s"
  while true; do
    sync_once || true
    sleep "$SYNC_INTERVAL"
  done
else
  sync_once
fi
