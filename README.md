# Weander

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

A creative micro-adventure generator for Android. Tap a button, get sent somewhere nearby with a creative mission. Go there. Do the thing. Document it. Build a personal journal of small adventures.

> "a curious friend handing you a creative dare"

---

## What it does

1. **Weander** — tap the button, get a random nearby destination (500m–2km) paired with a random creative mission
2. **Go** — walk there following the OSMDroid map; re-roll the mission if it doesn't spark anything
3. **Complete** — document with a photo, text, or audio recording
4. **Journal** — browse your past adventures in a chronological feed

Missions span six categories: Photo, Writing, Sound, Social, Creative, Observation. 72 missions total, weighted and composable.

---

## Building

The project runs entirely from a Nix shell — no Android Studio, no system-wide installs.

```bash
nix-shell          # enter the environment (JDK 17, Gradle, Android SDK)
make               # build debug APK
make install       # build and install on connected device
make run           # build, install, and launch
make logcat        # stream filtered logs (tag: Weander)
make clean         # remove build outputs
make distclean     # clean + wipe local Gradle cache
```

The debug APK lands at `app/build/outputs/apk/debug/app-debug.apk`.

---

## Tech stack

| Thing | Choice |
|---|---|
| Language | Java |
| Build | Gradle (via Nix shell) |
| UI | XML layouts, earthy palette, dark mode |
| Maps | OSMDroid 6.1.18 |
| Location | FusedLocationProviderClient |
| Storage | Room 2.6.1 |
| Min SDK | 26 (Android 8.0) |
| No backend | everything local-first |

---

## Project layout

```
app/src/main/java/it/lo/exp/weander/
  WeanderApp.java
  data/local/          # Room DB, DAO
  data/model/          # Adventure entity
  data/repository/
  missions/            # Mission, MissionCategory, MissionPool (72 missions)
  ui/home/             # HomeActivity — the "Weander" button
  ui/adventure/        # AdventureActivity — map + mission card
  ui/complete/         # CompleteActivity — camera / gallery / audio / text
  ui/journal/          # JournalActivity, AdventureDetailActivity
  util/                # Haversine, random nearby point, walk time
```

---

## Permissions

`ACCESS_FINE_LOCATION`, `CAMERA`, `RECORD_AUDIO`, `READ/WRITE_EXTERNAL_STORAGE`

All requested at runtime; the app degrades gracefully if any are denied.

---

## License

Weander is free software, released under the [GNU General Public License v3.0](LICENSE).
