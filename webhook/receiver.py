#!/usr/bin/env python3
"""GitHub webhook receiver for the recipe-app workflow lines.

Receives GitHub `issues` webhooks, routes by label onto the station's
workflow lines:
  - engineering -> p138  (ENGINEER -> TESTER -> REVIEWER -> OUTBOX)
  - devops      -> p158  (ENGINEER -> TESTER -> REVIEWER -> DEVOPS)

Writes each handled event to jobs/ so the station can pick it up.
"""
import json
import os
import time
import hmac
import hashlib
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer

WEBHOOK_SECRET = os.environ.get("GITHUB_WEBHOOK_SECRET", "change-me-secret")
JOBS_DIR = os.path.join(os.path.dirname(os.path.abspath(__file__)), "jobs")
os.makedirs(JOBS_DIR, exist_ok=True)

LABEL_TO_LINE = {
    "engineering": "p138",
    "devops": "p158",
}


def verify_signature(payload: bytes, signature: str) -> bool:
    if not signature.startswith("sha256="):
        return False
    digest = hmac.new(WEBHOOK_SECRET.encode(), payload, hashlib.sha256).hexdigest()
    return hmac.compare_digest(f"sha256={digest}", signature)


class Handler(BaseHTTPRequestHandler):
    def _send(self, code, obj):
        body = json.dumps(obj).encode()
        self.send_response(code)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def do_POST(self):
        length = int(self.headers.get("Content-Length", 0))
        payload = self.rfile.read(length)
        event = self.headers.get("X-GitHub-Event", "")
        sig = self.headers.get("X-Hub-Signature-256", "")

        if event == "ping":
            self._send(200, {"status": "pong"})
            return

        if not verify_signature(payload, sig):
            self._send(401, {"status": "bad signature"})
            return

        data = json.loads(payload)
        action = data.get("action")
        issue = data.get("issue", {})
        labels = [l["name"] for l in issue.get("labels", [])]
        matched = [l for l in labels if l in LABEL_TO_LINE]

        if action not in ("labeled", "unlabeled", "opened", "closed"):
            self._send(200, {"status": "ignored", "reason": "no label action"})
            return

        job = {
            "ts": time.time(),
            "event": event,
            "action": action,
            "issue_number": issue.get("number"),
            "issue_title": issue.get("title"),
            "issue_url": issue.get("html_url"),
            "labels": labels,
            "matched_lines": matched,
        }
        path = os.path.join(JOBS_DIR, f"{int(time.time()*1000)}.json")
        with open(path, "w") as f:
            json.dump(job, f, indent=2)

        print(f"[webhook] {action} #{issue.get('number')} {issue.get('title')} -> {matched or 'none'}")
        self._send(200, {"status": "ok", "job": path, "lines": matched})

    def do_GET(self):
        self._send(200, {"status": "alive", "ts": time.time()})

    def log_message(self, *args):
        print(f"[webhook] {args[0]}")


if __name__ == "__main__":
    port = int(os.environ.get("PORT", "8080"))
    server = ThreadingHTTPServer(("127.0.0.1", port), Handler)
    print(f"[webhook] listening on 127.0.0.1:{port}")
    server.serve_forever()
