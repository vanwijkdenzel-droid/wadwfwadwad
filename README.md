# Wave Client (safe utility starter)

A client-side Fabric starter for Minecraft Java 1.21.11 with an original purple/blue menu, local menu profile selection, HUD/visual option rows, and a GitHub Actions build that publishes the mod JAR as a downloadable workflow artifact.

## Build from GitHub

1. Extract this ZIP and create a new GitHub repository.
2. Upload the files and folders from this project to the repository root, then commit them to `main`.
3. Open **Actions**. The workflow also runs after pushes to `main` or `master`; choose **Build Wave Client** and click **Run workflow** for a manual build.
4. When it completes, open the run and download the `wave-client-1.21.11` artifact. Extract that artifact to get the JAR.
5. Install Fabric Loader for Minecraft Java 1.21.11, then place the JAR in `.minecraft/mods`. Fabric API is also required.

## In game

Press **Right Shift** to open the menu. The panel contains Overview, HUD, Visual, Profiles, and Settings pages. The options and profile presets are menu scaffolding for future fair-play, client-side preferences.

## Scope

This starter intentionally contains a menu shell and display preference placeholders. It does not include combat automation, reach/velocity changes, player/entity/storage ESP, tracers, freecam, or other multiplayer advantage features. A monochrome texture pack and Wave icon can be added as separate cosmetic assets later.

## Versions

- Minecraft Java: 1.21.11
- Java: 21
- Fabric Loader: 0.18.1
- Fabric API: 0.141.6+1.21.11
- Fabric Loom: 1.14.1

Build locally with Gradle 9.2.1 using `gradle build`. The finished mod JAR is written to `build/libs`.
