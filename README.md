# Recipe App

A simple Android app (Kotlin + Jetpack Compose) that organizes cooking recipes.
Recipes are supplied as JSON in a format defined together with the agent.
Viewing a recipe shows the required ingredients clearly and step-by-step
cooking instructions.

## Status

Scaffold in progress: model, in-memory repository, view model, and recipe
screen are in place. JSON format and exact UI are defined after the app can
add and read saved JSON files.

## Building

```bash
./gradlew :app:assembleDebug
```

Requires the Android SDK. Generate the Gradle wrapper with `gradle wrapper` if it is missing.
