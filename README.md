# ScreenCurfew

A bedtime lock app that helps you protect the hours that restore you. ScreenCurfew gently restricts phone use during scheduled sleep windows — calm and non-punitive, with emergency overrides always available.

**Android** enforces bedtime with overlay locks and Do Not Disturb. **iOS** works as a companion through Focus and sleep tracking. Built with Kotlin Multiplatform and Compose Multiplatform so most UI and business logic is shared.

## Features

- Scheduled bedtime and wake windows (weekday / weekend profiles)
- Soft reminders before full lock
- Overlay lock screen with emergency PIN pause
- Usage insights focused on progress, not shame
- Clear permission onboarding

## Project structure

* [`iosApp`](./iosApp/iosApp) — iOS entry point (SwiftUI host for shared Compose UI)
* [`androidApp`](./androidApp) — Android application module
* [`shared`](./shared/src) — shared Kotlin Multiplatform code
  - [`commonMain`](./shared/src/commonMain/kotlin) — UI, theme, and logic for all platforms
  - Platform folders (e.g. [`androidMain`](./shared/src/androidMain/kotlin), [`iosMain`](./shared/src/iosMain/kotlin)) for platform-specific APIs

## Running the apps

**Android Studio (macOS):** install the [Kotlin Multiplatform](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform) plugin, sync Gradle, then pick **iosApp** or **androidApp** in the run configuration dropdown and choose a device/simulator.

- iOS run config: [`.run/iosApp.run.xml`](./.run/iosApp.run.xml) (Xcode project `iosApp/iosApp.xcodeproj`, scheme `iosApp`)
- Android: use the generated **androidApp** configuration, or `./gradlew :androidApp:assembleDebug`
- iOS (Xcode): open [`iosApp/iosApp.xcodeproj`](./iosApp/iosApp.xcodeproj) and run the **iosApp** scheme

## Running tests

- Android: `./gradlew :shared:testAndroidHostTest`
- iOS: `./gradlew :shared:iosSimulatorArm64Test`

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
