# Focusly — Digital Wellbeing & Focus App

Focusly is a digital wellbeing and focus Android application built with Kotlin, XML, MVVM, Clean Architecture, Room Database, Navigation Component, DataStore, and Material Design 3.

## Features
- **Visual Design System**: Warm off-white background (`#F7FAF7`), Deep Forest Green cards (`#0A3C1D`), Coral CTA button (`#F07852`), and soft nature-inspired color hierarchy matching the design reference.
- **Home Screen**: Hero focus growth card ("Grow your Cedar"), "How Are You Feeling?" calm pace selector, minute adjuster, horizontal plant selection, app blocking shortcut, and custom canvas-based weekly focus distribution chart.
- **Focus Timer**: Live countdown timer with custom `ProgressRingView`, session mode toggles (Focus, Pomodoro, Quick Focus), +5 min extension, pause/resume, and session complete flow with Focus Energy rewards.
- **Main Navigation**: Custom bottom bar with 5 destinations (Home, Insights, Floating Center Focus Action, Plants/World, Profile) integrated with Navigation Component.
- **Insights & Analytics**: Focus metrics, session averages, AI Focus Coach recommendations, and time range breakdowns.
- **My Plants & World**: Botanic plant collection system (Cedar, Plumeria, Chili Pepper, Dusty Miller) powered by Focus Energy.
- **App Blocking**: Distraction management interface with usage stats wrappers and Android system settings permissions integration.
- **Routines & Habits**: Daily focus routines and habit streak tracking with completion toggles.
- **Profile & Subscription**: Level progression, total focus stats, streak counters, and premium paywall activation.

## Tech Stack & Architecture
- **Language**: Kotlin
- **UI Framework**: XML Layouts & Material Design 3 (ViewBinding)
- **Architecture**: MVVM + Clean Architecture + Repository Pattern
- **Persistence**: Room Database (Focus Sessions, Plants, Routines, Habits, Blocked Apps, Achievements) & DataStore Preferences
- **Concurrency**: Kotlin Coroutines & StateFlow
- **Navigation**: Jetpack Navigation Component
- **Custom Views**: `WeeklyChartView` & `ProgressRingView`

## Project Structure
```
app/src/main/java/com/focusly/app/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entity/
│   │   ├── preferences/
│   │   ├── AppDatabase.kt
│   │   └── DemoDataInitializer.kt
│   └── repository/
├── domain/
│   ├── model/
│   └── repository/
├── presentation/
│   ├── blocking/
│   ├── focus/
│   ├── habits/
│   ├── home/
│   ├── insights/
│   ├── profile/
│   ├── routines/
│   ├── subscription/
│   └── world/
├── service/
├── utils/
├── views/
├── FocuslyApp.kt
└── MainActivity.kt
```

## How to Run
1. Open the project in Android Studio (Ladybug or newer recommended).
2. Sync Project with Gradle Files.
3. Run the `app` module on an Android Device or Emulator (API 24+).
