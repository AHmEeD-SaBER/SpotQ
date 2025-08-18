# SpotQ

A modular **Android** app showcasing a clean, scalable architecture built with **Kotlin** and **Jetpack Compose**. SpotQ is organized by features and layered into *domain*, *data*, and *UI* modules to keep code easy to test and evolve.

> **Why this README?**
> To give new contributors (and future you) a fast, confident tour of the repo: what each module does, how data flows, how to run and extend the app, and where to add new features safely.

---

## ✨ Highlights

* **Jetpack Compose** UI (declarative, state-driven)
* **Multi‑module** structure with feature isolation
* **Clean Architecture** (Domain ⇄ Data ⇄ UI)
* **Hilt** for dependency injection
* **Coroutines / Flows** for async work
* **Navigation** via a centralized graph
* **Location & Geocoding** utilities
* **Onboarding → Auth → Main** user journey
* **Favorites & Places** feature slices

> **Tech note:** Exact library versions intentionally omitted here to avoid drift. See Gradle files for the source of truth.

---

## 🗂️ Repository Map

> Root level folders you’ll see in `master`:

```
app/
authentication/
buildSrc/
core-data/
core-domain/
core-ui/
errors/
favorites/
gradle/
location-provider/
main-navigation/
onboarding/
places/
profile/
splash/
```

Below is what each module is responsible for and *typical* classes you’ll find inside. (Names may differ slightly—follow the patterns.)

### `app/`

**The Android application module.**

* AndroidManifest, app theme setup, and the top-level `@HiltAndroidApp` application class.
* Wires the **navigation host** & app‑wide scaffolding (top bars, snackbars, etc.).
* Depends on feature modules and `core-*` modules.

### `core-domain/`

**Business rules and pure Kotlin types.** Framework‑free.

* Entities / models (immutable, business-facing)
* Use cases / interactors (e.g., `GetCurrentUser`, `SearchPlaces`)
* Repository interfaces (contracts only)

### `core-data/`

**Implements the domain contracts.**

* Repository implementations (map domain ↔ data sources)
* Mappers from DTO ↔ domain models
* Data sources (local and/or remote). If remote exists, expect Retrofit or similar; if local, expect Room/DataStore.

### `core-ui/`

**Reusable Compose components & design system.**

* Typography, colors, spacing, shapes
* Common UI elements: buttons, app bars, lists, loaders, empty/error states
* Utility composables and extensions

### `main-navigation/`

**Central navigation graph & routes.**

* Nav destinations, deep links, and type‑safe route arguments
* Feature graph integration (e.g., `splash → onboarding/auth → home`)
* Back‑stack handling & app‑level navigation utilities

### `location-provider/`

**Location + Geocoder utilities.**

* Safe wrappers around Android’s location services
* Geocoding helpers (forward/reverse) using `Geocoder`
* Coroutine‑friendly APIs (`suspend`/`Flow`), cancellation‑safe

### `onboarding/`

**First‑run experience.**

* Compose screens for the onboarding carousel
* Localized strings, small state machine for steps
* “Enable location”, “Allow notifications”, etc.

### `authentication/`

**Auth UI and state.**

* Screens for sign‑in/up or token restoration
* ViewModels that talk to domain auth use cases

### `places/`

**Browse/search places.**

* Screens, ViewModels, and domain calls to fetch places
* Sorting/filters, details screens, and error/empty handling

### `favorites/`

**Save & recall user favorites.**

* Persistent store integration via domain `FavoritesRepository`
* UI to add/remove favorites, list management

### `profile/`

**User profile & settings.**

* Profile screen, edit profile, language/theme toggles
* Hooks to sign‑out / clear data

### `splash/`

**Launch splash and app warm‑up.**

* Minimal ViewModel that waits for bootstrapping tasks (e.g., token check/first‑run flags) then navigates to the right start destination

### `errors/`

**Error taxonomy shared across the app.**

* Sealed error types (e.g., `NetworkError`, `LocationError`, `AuthError`)
* Mappers to user‑friendly messages in `core-ui`

### `buildSrc/`

**Gradle constants and convention plugins.**

* Centralized dependency versions & build logic

> Tip: New features should become **their own module** to keep build times low and responsibilities clear. Depend **inward**: feature → core‑domain/core‑data/core‑ui.

---

## 🔁 Data Flow (Clean Architecture)

```
[UI (Compose + ViewModel)]
        │  emits user intents and collects state
        ▼
[Domain (UseCases + Repos contracts)]
        │  framework‑free logic
        ▼
[Data (Repos impl + DataSources)]
        │  maps to DTO / DB, handles errors
        ▼
[Platform (Android services: Location/Geocoder, etc.)]
```

* **ViewModel** exposes `State` (immutable) + `Events` (one‑offs)
* **UseCases** are small, focused `operator fun invoke(...)`
* **Repositories** hide IO, expose `Flow<T>` or `suspend` functions

---

## 🚀 Getting Started

1. **Prerequisites**

   * Android Studio (Koala or newer)
   * Android SDK 24+
   * JDK 17
2. **Clone**

   ```bash
   git clone https://github.com/AHmEeD-SaBER/SpotQ.git
   cd SpotQ
   ```
3. **Open in Android Studio** and let Gradle sync.
4. **Run** the `app` configuration on a device/emulator.

### Environment / API keys

If you integrate external APIs (e.g., Places/Maps), add your keys through **Gradle properties** or **local `secrets.properties`** and read them via BuildConfig. This repo is structured to make it easy but doesn’t hardcode secrets.

---

## 🧭 Navigation Overview

* A single **NavHost** in `app/` (or a `MainNavigation` module entry) registers feature routes.
* Features provide their **entry Composables** and route constants; the nav graph composes them.
* Back handling is consolidated (e.g., double‑press to exit on top‑level destinations).

---

## 📍 Location & Geocoding

* `location-provider/` exposes suspend/Flow APIs for reading last known location, requesting updates, and reverse‑geocoding into human‑readable addresses.
* Implementations handle **permission checks**, **cancellations**, and **Android version quirks**.

---

## 🌐 Localization

* Strings live inside each feature (`res/values/strings.xml`) with language‑specific variants (e.g., `values-ar/`).
* Use composition‑local or state to switch languages at runtime where applicable.

---

## 🧪 Testing Strategy (suggested layout)

* **Domain**: pure unit tests for use cases
* **Data**: repository tests with fakes; instrumented tests if using Room
* **UI**: Compose tests for critical screens; snapshot/semantics checks

> Run tests with Gradle:
>
> ```bash
> ./gradlew test
> ./gradlew connectedAndroidTest  # instrumented
> ```

---

## 📦 Build & CI

* Kotlin DSL Gradle (`build.gradle.kts` at root)
* Centralized versions in `buildSrc/`
* Release builds use standard `proguard-rules.pro` and signing config via Gradle properties

---

## 🧱 Adding a New Feature (Template)

1. Create a module `feature-name/`.
2. Define domain contracts in `core-domain/` (if new behavior).
3. Implement data wiring in `core-data/`.
4. Build UI in `feature-name/` using `core-ui` components.
5. Register routes in `main-navigation/`.

> Keep dependencies pointing **towards** core modules. Avoid cross‑feature coupling.

---

## 🗺️ Roadmap

* [ ] Offline first for Places/Favorites
* [ ] Dark mode theming polish in `core-ui`
* [ ] UI tests for onboarding/auth flows
* [ ] Crash/ANR reporting hook (e.g., Firebase Crashlytics)
* [ ] In‑app language switcher settings

---

## 🤝 Contributing

1. Create a topic branch from `master`.
2. Write/adjust tests where reasonable.
3. Run `./gradlew assembleDebug` and lint checks.
4. Open a PR with a clear description and screenshots for UI changes.

---

## 🙌 Acknowledgements

* Android + Kotlin community for battle‑tested patterns
* Jetpack teams for Compose, Hilt, Navigation, Coroutines


---

## 📎 Notes for Maintainers

* Keep `buildSrc/` as the single source of truth for versions.
* Prefer **small use cases** and **thin ViewModels**.
* Drive UI with **immutable state** + **events**.
* Add new error types in `errors/` and map them to UI messages in `core-ui`.
* Keep module APIs small; expose only what features need.

> Have questions or found mismatches? Open an issue and tag maintainers.
