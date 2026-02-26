# **Number Converter**

The plugin to convert numbers from one system to another in any JetBrains IDE.
For floating point numbers, the fractional part will be discarded.

### Features:

- big integers
- engineering types
- negative conversion (bit shifting)
- multi carets
- shortcuts
- date string to number system conversion (select a date string and convert to DEC/HEX/OCT/BIN/FORMAT)
- configurable seconds/milliseconds timestamp output
- number formatting with grouping, currency symbols, and decimal places
- settings – date format, surrounding, etc.

### Current supported systems:

- DEC - Decimal
- HEX - Hexadecimal
- OCT - Octal
- BIN - Binary
- DATE - Date/Time
- FORMAT - Number formatting (grouping, currency)

### Usage

1. Select a number or date string in the editor
2. Right-click and choose **Convert To...** from the context menu
3. Pick the target system (DEC, HEX, OCT, BIN, DATE, FORMAT)

The plugin auto-detects the input format:
- `0x` prefix → HEX
- `0b` prefix → BIN
- `0` prefix → OCT
- Date strings (matching your configured format) → parsed as date
- Everything else → DEC

Multiple carets are supported — each selection is converted independently.

### Shortcuts

| Shortcut | Target |
|---|---|
| `Ctrl+Alt+N`, `D` | DEC |
| `Ctrl+Alt+N`, `H` | HEX |
| `Ctrl+Alt+N`, `O` | OCT |
| `Ctrl+Alt+N`, `B` | BIN |
| `Ctrl+Alt+N`, `T` | DATE |
| `Ctrl+Alt+N`, `F` | FORMAT |

### Settings

Open **Settings → Tools → Number Converter** to configure the plugin.

**Surrounding**
- Wrap converted output in configurable left/right strings (e.g. `[` and `]`)

**Date format**
- **Format pattern** — `SimpleDateFormat` pattern used for date display and parsing (default: `yyyy-MM-dd HH:mm:ss`). Patterns like `yyyy-MM-dd HH:mm:ss.SSS` are supported for millisecond precision.
- **UTC** — use UTC timezone instead of local
- **Milliseconds** — when enabled, timestamps are interpreted and produced as milliseconds instead of seconds. Affects all conversions: number → date and date → number/format.

**Formatting**
- **Upper/Lower Case** — controls letter case for HEX output (e.g. `0xFF` vs `0xff`)

**Number format** (for FORMAT conversion)
- **Delimiter** — grouping character (default: `,`)
- **Group size** — digits per group (default: `3`)
- **Decimals** — enable fixed decimal places
- **Currency** — symbol to prepend or append. To add a custom symbol, type it in the currency dropdown and right-click → **Add**. To remove a custom symbol, select it and right-click → **Remove**.

### Build

Prerequisites:

- JDK 17

Build plugin ZIP:

```bash
./gradlew buildPlugin
```

Build artifact:

- `build/distributions/NumberConverter-<version>.zip`
