# PurpleChat

Ein Client‑seitiger Fabric‑Mod, der Twitch‑Chat‑Nachrichten direkt im Minecraft‑Chat anzeigt. Ideal für Streaming oder das Mitlesen deiner Lieblingskanäle, während du spielst.

## Features
- Zeigt Nachrichten aus beliebigen Twitch‑Kanälen im In‑Game‑Chat
- Mehrere Kanäle gleichzeitig (kommasepariert)
- Optional: Verwende die individuelle Twitch‑Namensfarbe der Sender
- Konfigurierbare Anzeige (Template‑Feld für zukünftige Formatierung)
- Live‑Anpassung der Kanäle ohne Neustart (über den Config‑Screen)
- Keybind zum schnellen Öffnen der Konfiguration

## Voraussetzungen
- Minecraft: 1.21 / 1.21.1
- Fabric Loader: ≥ 0.15.0 (empfohlen: laut `gradle.properties` 0.17.3)
- Fabric API: passend zur Spielversion
- Java 21

## Installation
1. Fabric Loader installieren.
2. Fabric API und diese Mod‑JAR in den `mods`‑Ordner legen.
3. Spiel starten.

## Schnellstart
- Drücke standardmäßig `P`, um den PurpleChat‑Konfigurationsbildschirm zu öffnen.
- Trage unter „Kanäle (Kommagetrennt)“ eine Liste von Twitch‑Kanälen ein, z. B.: `gronkh,monstercat`.
- Optional: „Twitch‑Userfarbe verwenden“ aktivieren, damit die Namen in ihrer Twitch‑Farbe erscheinen.
- Speichern – die Kanäle werden live beigetreten oder verlassen.

## Konfiguration
Die Einstellungen können im Spiel über den YACL‑basierten Screen geändert werden.

- Kanäle: Kommagetrennte Liste (mit oder ohne `#`, Groß-/Kleinschreibung egal)
- Nachrichten‑Template: Platzhalter vorgesehen (`{channel}`, `{user}`, `{message}`); aktuelle Version formatiert die Ausgabe wie: `[Twitch | kanal] user: nachricht`
- Twitch‑Userfarbe verwenden: Färbt den Nutzernamen gemäß Twitch‑Profilfarbe (falls vorhanden)

Konfigurationsdatei: `config/purplechat.json` (wird automatisch erstellt und beim Speichern aktualisiert).

## Steuerung
- Keybind „PurpleChat: Konfiguration öffnen“: Standard `P`
  - Anpassen über Minecraft‑Steuerungsmenü.

## Verhalten im Spiel
- Eingehende Nachrichten erscheinen im Chat als: `[Twitch | <kanal>] <nutzer>: <text>` in Lila‑Prefix, grauem Doppelpunkt, weißem Nachrichtentext.
- „Chat bereinigt“ (Twitch CLEARCHAT) wird als Hinweis im Chat angezeigt.
- Deduplizierung schützt vor doppelten Nachrichten (per Message‑ID oder Fallback‑Schlüssel, begrenzter Ringpuffer).

## Bekannte Einschränkungen
- Anmeldung: Der Mod nutzt derzeit keine Authentifizierung; es werden öffentliche Kanalnachrichten gelesen.
- Nachrichten‑Template: Platzhalter sind vorgesehen, erweiterte Formatierung folgt in künftigen Versionen.

## Entwicklung
Voraussetzungen: Java 21, Gradle, Internetzugang für Abhängigkeiten.

Build‑Befehle:
```bash
./gradlew build
```
Die Artefakte findest du unter `build/libs`.

Wichtige Abhängigkeiten:
- Twitch4J (`com.github.twitch4j:twitch4j:1.25.0`)
- YetAnotherConfigLib (YACL)
- Fabric API

Einstiegspunkte und Struktur:
- Mod‑Entrypoint: `com.t0g3pii.purplechat.PurpleChatMod`
- Twitch‑Client/Events: `com.t0g3pii.purplechat.twitch.TwitchService`
- Anzeigeformatierung: `com.t0g3pii.purplechat.ui.MessageFormatter`
- Konfigurations‑UI (YACL): `com.t0g3pii.purplechat.ui.ConfigScreenProvider`
- Keybinds: `com.t0g3pii.purplechat.client.KeyBindings`
- Konfigurationsdateien: `com.t0g3pii.purplechat.config.*`

## Übersetzungen
- Deutsch (`de_de.json`) und Englisch (`en_us.json`) enthalten Basis‑Texte.

## Lizenz
MIT – siehe `LICENSE`.

## Credits
- Idee & Umsetzung: `t0g3pii`
- Twitch‑Integration via Twitch4J
- Konfigurations‑UI via YACL
