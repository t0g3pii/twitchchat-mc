# PurpleChat

A client‑side Fabric mod that displays Twitch chat messages directly in the Minecraft chat. Perfect for streaming or casually following your favorite channels while you play.

## Features
- Show messages from any Twitch channels in the in‑game chat
- Multiple channels at the same time (comma‑separated)
- Optional: use the sender’s individual Twitch name color
- Configurable display (template field planned for extended formatting)
- Apply channel changes live without restart (via the config screen)
- Keybind to quickly open the config

## Requirements
- Minecraft: 1.21 / 1.21.1
- Fabric Loader: ≥ 0.15.0 (project uses 0.17.x in `gradle.properties`)
- Fabric API: matching the game version
- Java 21

## Installation
1. Install Fabric Loader.
2. Put Fabric API and this mod’s JAR into your `mods` folder.
3. Start the game.

## Quick start
- Press `P` (default) to open the PurpleChat configuration screen.
- Under “Channels (comma‑separated)”, enter a list of Twitch channels, e.g. `gronkh,monstercat`.
- Optionally enable “Use Twitch user color” so usernames render in their Twitch color.
- Save – the mod will join/leave channels live.

## Configuration
Settings are adjusted in‑game via a YACL‑based screen.

- Channels: Comma‑separated list (with or without `#`, case‑insensitive)
- Message template: Placeholders are planned (`{channel}`, `{user}`, `{message}`); the current build renders messages like: `[Twitch | channel] user: message`
- Use Twitch user color: Colors the username based on the Twitch profile color (if present)

Config file: `config/purplechat.json` (created automatically and updated on save).

## Controls
- Keybind “PurpleChat: Open configuration”: default `P`
  - Can be changed in Minecraft’s controls menu.

## In‑game behavior
- Incoming messages appear as `[Twitch | <channel>] <user>: <text>` with a purple prefix, gray colon, and white message text.
- Twitch CLEARCHAT is shown as a small notice in the chat.
- De‑duplication avoids duplicate messages (via message ID or fallback key, bounded ring buffer).

## Known limitations
- Authentication: No login is used currently; the mod reads public channel messages.
- Message template: Placeholders are provided for upcoming releases; extended formatting is WIP.

## Development
Prerequisites: Java 21, Gradle, internet access for dependencies.

Build commands:
```bash
./gradlew build
```
(Windows):
```bat
gradlew.bat build
```
Artifacts are placed under `build/libs`.

Key dependencies:
- Twitch4J (`com.github.twitch4j:twitch4j:1.25.0`)
- YetAnotherConfigLib (YACL)
- Fabric API

Entrypoints & structure:
- Mod entrypoint: `com.t0g3pii.purplechat.PurpleChatMod`
- Twitch client/events: `com.t0g3pii.purplechat.twitch.TwitchService`
- Message formatting: `com.t0g3pii.purplechat.ui.MessageFormatter`
- Config UI (YACL): `com.t0g3pii.purplechat.ui.ConfigScreenProvider`
- Keybinds: `com.t0g3pii.purplechat.client.KeyBindings`
- Config files: `com.t0g3pii.purplechat.config.*`

## Translations
- German (`de_de.json`) and English (`en_us.json`) provide basic strings.

## License
MIT — see `LICENSE`.

## Credits
- Idea & implementation: `t0g3pii`
- Twitch integration via Twitch4J
- Config UI via YACL
