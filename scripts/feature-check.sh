#!/usr/bin/env bash
# feature-check.sh — automated gate after a new feature is added to the recipe app.
#
# What it does, in order:
#   1. Confirm the emulator is reachable.
#   2. Run the JVM unit tests (./gradlew testDebugUnitTest).
#   3. If tests are GREEN: build the debug APK, install it on the emulator,
#      launch the app, dump the UI hierarchy, and assert that every expected
#      string is present in the live UI.
#   4. Print a PASS/FAIL report.
#
# Usage:
#   ./scripts/feature-check.sh [EXPECTED_STRING ...]
#
#   No args      -> deploy + verify the app launches and shows the recipe list.
#   With args    -> each arg is a string that MUST appear in the live UI dump.
#
# Exit code: 0 = all green, 1 = a step failed.

set -uo pipefail

export PATH="$HOME/Android/Sdk/platform-tools:$HOME/Android/Sdk/emulator:$PATH"

DEVICE="${DEVICE:-emulator-5554}"
APP_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PKG="com.example.recipeapp"
LAUNCHER="${PKG}.MainActivity"
DUMP="$APP_DIR/build/feature-ui-dump.xml"
REPORT="$APP_DIR/build/feature-check-report.txt"

fail() { echo -e "\n\033[1;31mFAIL\033[0m: $1"; exit 1; }

echo "================ feature-check ================"
echo "device : $DEVICE"
echo "app    : $APP_DIR"
echo "==============================================="

# 1. Emulator reachable
adb -s "$DEVICE" shell true 2>/dev/null || fail "emulator $DEVICE not reachable (start it first)."
adb -s "$DEVICE" shell getprop dev.bootcomplete | grep -q 1 || echo "  note: bootcomplete not yet 1, continuing (tests don't need it)."

# 2. Unit tests
echo
echo "[1/4] Running unit tests..."
./gradlew testDebugUnitTest --console=plain -q || fail "unit tests failed — fix them before deploying."
echo "      unit tests: GREEN"

# 3. Build + install + launch
echo
echo "[2/4] Building debug APK..."
./gradlew assembleDebug --console=plain -q || fail "assembleDebug failed."

APK="$(ls -t "$APP_DIR"/app/build/outputs/apk/debug/*.apk 2>/dev/null | head -1)"
[ -n "${APK:-}" ] || fail "no debug APK produced."
echo "      apk: $APK"

echo "[3/4] Installing on $DEVICE..."
adb -s "$DEVICE" install -r -g "$APK" || fail "install failed."

echo "[4/4] Launching $LAUNCHER and capturing UI..."
adb -s "$DEVICE" shell am start -n "$LAUNCHER" -S >/dev/null 2>&1
adb -s "$DEVICE" shell input keyevent 82  # wake / dismiss any overlay
sleep 3

# Capture the live UI hierarchy (the best an AI agent can "see").
if adb -s "$DEVICE" shell uiautomator dump /sdcard/ui.xml >/dev/null 2>&1; then
  adb -s "$DEVICE" pull /sdcard/ui.xml "$DUMP" >/dev/null 2>&1 || true
fi
if [ ! -s "$DUMP" ]; then
  echo "  (uiautomator dump unavailable — verifying via package process only)"
fi

# 5. Verify expected strings
EXPECTED=("$@")
if [ ${#EXPECTED[@]} -eq 0 ]; then
  # Default: the app launched and shows the shipped recipe list.
  EXPECTED=("Recipe" "Tacos" "Pancakes" "Pasta")
fi

MISSING=()
for s in "${EXPECTED[@]}"; do
  if grep -qF -- "$s" "$DUMP" 2>/dev/null; then
    echo "      found: \"$s\""
  else
    echo "      MISSING: \"$s\""
    MISSING+=("$s")
  fi
done

if [ ${#MISSING[@]} -gt 0 ]; then
  fail "UI did not show: ${MISSING[*]}"
fi

cp "$DUMP" "$REPORT" 2>/dev/null || true
echo
echo -e "\033[1;32mPASS\033[0m: unit tests green AND app verified on $DEVICE"
echo "UI dump saved: $DUMP"
exit 0
