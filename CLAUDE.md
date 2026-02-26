# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

NumberConverter is an IntelliJ IDEA plugin that converts numbers between different systems (DEC, HEX, OCT, BIN), converts Unix timestamps to dates, and formats numbers with grouping/currency. It targets IntelliJ Platform 2024.1.7 and supports multi-caret selections.

## Build & Test Commands

```bash
# Build plugin ZIP
./gradlew buildPlugin
# Output: build/distributions/NumberConverter-<version>.zip

# Run all tests
./gradlew test

# Clean + test
./gradlew clean test

# Run IDE with plugin loaded (for manual testing)
./gradlew runIde
```

There is no way to run a single test class directly with Gradle — `./gradlew test` runs all tests.

## Architecture

### Action Flow

```
Right-click context menu / Ctrl+Alt+N+[D/H/O/B/T/F]
  → ConvertActionGroup (6 sub-actions: Dec, Hex, Oct, Bin, DateTime, Format)
    → ConvertAction.actionPerformed()
      → convertByType() routes to converter
        → ConvertNumber | ConvertDate | FormatNumber
          → result replaces selected text in editor
```

### Key Components

**`ConvertAction`** — Main IntelliJ `AnAction`. Handles multi-caret support, text replacement in the editor document, and routes to the correct converter via `convertByType()`. The `update()` method populates the popup menu item with a preview of the converted value.

**`ConvertActionGroup`** — Defines 6 nested static subclasses (Dec, Hex, Oct, Bin, DateTime, Format), each instantiating a `ConvertAction` with the appropriate `ConvertType` enum value.

**`IConverter`** interface — Implemented by all converters. Contains a single `convert(String)` method.

**`ConvertNumber`** — Number system conversions using `BigInteger`. Strips delimiters from input, auto-detects input format via `ConvertTypeParser`, handles negative numbers with bit-shifting.

**`ConvertDate`** — Unix timestamp (seconds or milliseconds) ↔ formatted date string. Uses `SimpleDateFormat` with configurable pattern and UTC/local timezone.

**`FormatNumber`** — Number grouping/formatting. Strips existing formatting, applies configurable delimiter, group size, decimal places, and currency prefix/suffix.

**`ConvertTypeParser`** — Detects number format from prefix (`0x`→HEX, `0b`→BIN, `0`→OCT, none→DEC) and provides the radix and output prefix.

**`PluginPersistentStateComponent`** — IntelliJ `PersistentStateComponent` singleton. Stores all user settings (18 fields) serialized to `numberConverter.xml`. Use `getInstance()` to access settings from converters.

**`PluginConfigurable`** — Bridges the `SettingsForm` Swing UI to `PluginPersistentStateComponent`. Implements `SearchableConfigurable` so it appears in IDE Settings.

**`SettingsForm`** — Swing `JPanel` with live preview for date format and number formatting. State managed via `getData()`, `setData()`, `isModified()`.

### Plugin Registration (plugin.xml)

- **Application service:** `PluginPersistentStateComponent`
- **Configurable:** `PluginConfigurable` (appears under IDE Settings)
- **Action group:** `NumberConverter.Group` in `EditorPopupMenu`
- **Shortcuts:** `Ctrl+Alt+N` + `D/H/O/B/T/F` for each conversion type

## Configuration

Key values in `gradle.properties`:
- `pluginVersion` — bump this for releases
- `platformVersion = 2024.1.7` (IC — IntelliJ Community)
- `pluginSinceBuild = 241`, `pluginUntilBuild` is empty (no upper limit)

## Test Structure

- `ConvertActionTest` — 13 tests covering all 6 conversion types, including negative numbers, big integers, and engineering notation inputs
- `FormatNumberTest` — 15 tests covering delimiter variations, group sizes, currency symbols, decimal places, and European formatting

Tests use JUnit 5 (Jupiter). `PluginPersistentStateComponent` exposes a test constructor that accepts a pre-configured state object to avoid needing a running IDE during tests.
