# **Number Converter**

The plugin to convert numbers from one system to another in any JetBrains IDE.
For floating point numbers, the fractional part will be discarded.

### Features:

- big integers
- engineering types
- negative conversion (bit shifting)
- multi carets
- shortcuts
- settings – date format, surrounding, etc.

### Current supported systems:

- DEC - Decimal
- HEX - Hexadecimal
- OCT - Octal
- BIN - Binary
- DATE - Date/Time

### Build

Prerequisites:

- JDK 17

Build plugin ZIP:

```bash
./gradlew buildPlugin
```

Build artifact:

- `build/distributions/NumberConverter-<version>.zip`
