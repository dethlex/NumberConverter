package com.dethlex.numberconverter.format;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.ConvertActionTestBase.TestCase;
import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("→ FORMAT (via ConvertAction)")
public class ConvertFormatActionTest extends ConvertActionTestBase {

    private final PluginPersistentStateComponent formatState = PluginPersistentStateComponent.getInstance();

    @AfterEach
    void resetFormatState() {
        formatState.setFormatDelimiter(",");
        formatState.setFormatGroupSize(3);
        formatState.setFormatDecimalEnabled(false);
        formatState.setFormatDecimalPlaces(2);
        formatState.setFormatCurrencySymbol("");
        formatState.setFormatCurrencyPrefix(true);
    }

    @Test
    @DisplayName("Numbers in various formats are grouped with the configured delimiter")
    public void testToFormat() {
        assertConverts(ConvertType.FORMAT,
                new TestCase("1234567890",  "1,234,567,890", "DEC with comma grouping"),
                new TestCase("0xFF",         "255",           "HEX (small, no grouping)"),
                new TestCase("0b11111111",   "255",           "BIN (small, no grouping)"),
                new TestCase("-1234567",     "-1,234,567",    "Negative number"),
                new TestCase("100",          "100",           "Small number (no grouping)"),
                new TestCase("1000",         "1,000",         "Exactly one group boundary"),
                new TestCase("12345678",     "12,345,678",    "Standard grouping"),
                new TestCase("1_000_000",    "1,000,000",     "Underscore-formatted input"),
                new TestCase("qwerty",       "can't convert", "Invalid input → FORMAT")
        );
    }
}
