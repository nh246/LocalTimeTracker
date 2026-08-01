# Focus Ledger (local-only time tracker)

An original Android app inspired by the general concept of a project/task time
tracker, built from scratch — not decompiled or copied from any existing app.

## What's different from a typical "premium" version of this kind of app
- **No internet permission at all.** Check `AndroidManifest.xml` — there's no
  `INTERNET` permission, so the app cannot make network calls even if some
  library tried to.
- **No analytics/ads/tracking SDKs.** No Firebase, no AdMob, no Install
  Referrer, no Advertising ID.
- **No task limit.** Add as many tasks/projects as you want.
- **CSV export is free**, writes straight to your device's Downloads folder
  via `CsvExporter.kt`.
- **All stats/reports are free** — see `StatsFragment.kt`.
- **Notifications are local only**, scheduled with `AlarmManager`
  (`AlarmScheduler.kt` / `AlarmReceiver.kt`) — no push service.
- All data lives in a local Room database (`local_time_tracker.db`) on your
  phone. Nothing syncs anywhere.

## How to build
1. Open this folder in Android Studio (or Gemini/Android Studio once your PC
   is back up).
2. Let Gradle sync (needs internet the first time to download Gradle/AndroidX
   dependencies — after that it's fine offline).
3. Build > Generate Signed Bundle/APK, or just Run on your connected phone
   over USB.

## What's stubbed / left for you to extend
This is a working skeleton covering the core flows you described (Projects,
Tasks, Sessions, Stats, Settings) — a few things are intentionally simple so
you can adapt them to your exact taste:
- Project detail screen / per-project stats screen (tap a project card).
- A project picker when adding a task (currently assigns `projectId = 0`).
- Full JSON backup export/import (stub button included in Settings).
- Editing/deleting projects, custom colors picker.
- Persisting an in-progress timer across app restarts (currently in-memory).

None of these gaps are paywall-related — they're just scope trims to get you
a real, working, fully local app quickly.
