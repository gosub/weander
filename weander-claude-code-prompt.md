# Weander — Claude Code Prompt

Build an Android app called **"Weander"** — a creative micro-adventure generator that sends users to nearby random locations with a creative mission to complete and document. The name is a wordplay on "we" + "wander" + "meander."

## Core Concept

The app randomly picks a nearby location (within walking distance) and pairs it with a random creative mission. The user goes there, completes the mission, and documents it. Over time they build a personal journal of small creative adventures.

## Key Features

### 1. Adventure Generator
- On the home screen, a single large button: **"Weander"**
- When tapped, the app:
  1. Gets the user's current GPS location
  2. Picks a random point within a configurable radius (default: 500m–2km)
  3. Randomly selects a **creative mission** from a categorized pool
  4. Presents both the destination (on a map) and the mission in a beautiful card UI
- The user can re-roll the mission (but not the location), or re-roll everything
- Show estimated walking time and distance

### 2. Creative Missions
Missions should be varied, creative, and fun — NOT athletic or scavenger-hunt-like. They fall into categories:

**📸 Photo missions:**
- "Photograph the most interesting texture you can find here"
- "Take a photo that tells a story without people in it"
- "Find and photograph something that doesn't belong"
- "Capture the light — find the most beautiful shadow or reflection"
- "Photograph this place as if it were the cover of an album"
- "Find two things that rhyme visually"
- "Take a photo that would confuse a time traveler from 1800"

**✍️ Writing missions:**
- "Write a haiku inspired by the first thing you notice"
- "Describe this place in exactly 6 words"
- "Write a tiny fictional backstory for a stranger you see (don't approach them)"
- "Name this place as if it were a location in a fantasy novel"
- "Write a one-paragraph short story that begins and ends here"
- "What would a travel guide from the year 3000 say about this spot?"

**🎧 Sound missions:**
- "Record the ambient sound for 30 seconds — title it like a music track"
- "Find the most surprising sound in this area and record it"
- "Record a 15-second 'audio postcard' to send someone"

**💬 Social missions:**
- "Ask a stranger: what's the best-kept secret of this neighbourhood?"
- "Ask someone: if this street had a theme song, what would it be?"
- "Find a local shop and ask the owner what they love most about working here"
- "Ask a stranger to describe this place in one word — photograph them holding a paper with that word (with permission)"

**🎨 Creative missions:**
- "Sketch something you see in under 2 minutes (photo the sketch)"
- "Rearrange small found objects (leaves, stones, etc.) into a tiny art piece and photograph it"
- "Find a spot where you'd place a bench if you could — photograph the view from it"
- "If you could rename this street, what would you call it? Make a sign (on paper) and photograph it in place"

**👁️ Observation missions:**
- "Count how many different colors you can see from one spot — list them"
- "Find the oldest-looking thing here and guess its age"
- "What detail here would most people walk past without noticing? Photograph it"
- "Stand still for 60 seconds. Write down everything that changes"

Include at least 10 missions per category (60+ total). Make them whimsical, thoughtful, sometimes funny. They should feel like gentle creative nudges, not chores.

### 3. Adventure Documentation
When the user completes a mission, they can submit:
- A **photo** (from camera or gallery)
- A **text entry** (for writing missions)
- An **audio recording** (for sound missions)
- Or a combination

### 4. Adventure Journal
- A chronological feed of past adventures, each showing:
  - The date and location (mini map thumbnail)
  - The mission that was given
  - The user's response (photo/text/audio)
- Tapping an entry opens a full detail view
- The journal should feel like a personal creative scrapbook

### 5. Map View
- A map showing all past adventure locations as pins
- Tapping a pin shows the mission and response
- This creates a personal "creative map" of the user's city over time

### 6. Stats & Streaks (lightweight, not gamified)
- Total adventures completed
- Categories explored (e.g., "12 photo, 5 writing, 3 sound...")
- Longest streak (consecutive days/weeks with an adventure)
- A subtle nudge if it's been a while: "It's been 5 days — the world misses your eye 👁️"

## Technical Requirements

- **Language:** Java
- **Build system:** Gradle (CLI only — no Android Studio)
- **UI:** Android XML layouts
- **Development approach:** Minimal CLI-only using `java`, `gradle`, and the Android SDK directly
- **Local storage:** Room database for adventures journal
- **Maps:** Google Maps SDK or OpenStreetMap
- **Location:** FusedLocationProviderClient for GPS
- **Media:** Camera via Intent or Camera2 API for photos, MediaRecorder for audio
- **Permissions:** Location, Camera, Microphone, Storage
- **Min SDK:** 26 (Android 8.0)
- **No backend required** — everything local-first
- **Missions stored as a local JSON file or Java enum/class structure**, easy to expand later
- **No Android Studio or IDE dependencies** — everything must build and run via `./gradlew assembleDebug` from the command line

## Design Direction

- **Minimal and warm**, not techy or gamified
- Earthy/muted color palette — think field notebook vibes
- Typography: one serif font for mission text (literary feel), one sans-serif for UI
- Subtle animations on the adventure card reveal (like turning a page)
- The overall feel should be: **"a curious friend handing you a creative dare"**
- Dark mode support
- App icon concept: a simple, hand-drawn-style compass or path that subtly forms a "W"

## Project Structure Suggestion

```
app/
├── data/
│   ├── local/          # Room DB, DAOs
│   ├── model/          # Adventure, Mission entities
│   └── repository/     # AdventureRepository
├── domain/
│   ├── model/          # Domain models
│   └── usecase/        # GenerateAdventure, SaveAdventure, etc.
├── ui/
│   ├── home/           # Main screen with "Weander" button
│   ├── adventure/      # Active adventure screen (map + mission card)
│   ├── complete/       # Completion/documentation screen
│   ├── journal/        # Journal feed
│   ├── map/            # Map view of all adventures
│   ├── stats/          # Simple stats screen
│   └── theme/          # Colors, typography, shapes
├── missions/           # Mission definitions and random selection logic
└── util/               # Location helpers, permission handling
```

## First Milestone

Get a working flow: tap "Weander" → see a random nearby point on a map + a random mission → navigate there → tap "Complete" → take a photo or write text → save to journal → see it in the journal feed.

Start with this flow end-to-end before polishing the UI or adding all mission categories.
