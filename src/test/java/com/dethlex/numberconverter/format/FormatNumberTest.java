package com.dethlex.numberconverter.format;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormatNumberTest {

    private final PluginPersistentStateComponent state = PluginPersistentStateComponent.getInstance();

    @AfterEach
    public void resetDefaults() {
        state.setFormatDelimiter(",");
        state.setFormatGroupSize(3);
        state.setFormatDecimalEnabled(false);
        state.setFormatDecimalPlaces(2);
        state.setFormatCurrencySymbol("");
        state.setFormatCurrencyPrefix(true);
    }

    @Test
    @DisplayName("Underscore delimiter")
    public void testUnderscoreDelimiter() {
        state.setFormatDelimiter("_");
        var formatter = new FormatNumber("12345678");
        Assertions.assertEquals("12_345_678", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Space delimiter")
    public void testSpaceDelimiter() {
        state.setFormatDelimiter(" ");
        var formatter = new FormatNumber("12345678");
        Assertions.assertEquals("12 345 678", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Dot delimiter (European grouping)")
    public void testDotDelimiter() {
        state.setFormatDelimiter(".");
        var formatter = new FormatNumber("12345678");
        Assertions.assertEquals("12.345.678", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Currency prefix formatting")
    public void testCurrencyPrefix() {
        state.setFormatCurrencySymbol("$");
        state.setFormatCurrencyPrefix(true);
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(2);
        var formatter = new FormatNumber("1234");
        Assertions.assertEquals("$1,234.00", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Currency suffix formatting")
    public void testCurrencySuffix() {
        state.setFormatCurrencySymbol("\u20AC");
        state.setFormatCurrencyPrefix(false);
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(2);
        var formatter = new FormatNumber("1234");
        Assertions.assertEquals("1,234.00\u20AC", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("European format: dot grouping, comma decimal, euro suffix")
    public void testEuropeanFormat() {
        state.setFormatDelimiter(".");
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(2);
        state.setFormatCurrencySymbol("\u20AC");
        state.setFormatCurrencyPrefix(false);
        var formatter = new FormatNumber("1234");
        Assertions.assertEquals("1.234,00\u20AC", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Custom group size 4")
    public void testGroupSizeFour() {
        state.setFormatGroupSize(4);
        var formatter = new FormatNumber("1234567890");
        Assertions.assertEquals("12,3456,7890", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Group size 2 (Indian-like grouping)")
    public void testGroupSizeTwo() {
        state.setFormatGroupSize(2);
        var formatter = new FormatNumber("1234567");
        Assertions.assertEquals("1,23,45,67", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Negative number with currency")
    public void testNegativeWithCurrency() {
        state.setFormatCurrencySymbol("$");
        state.setFormatCurrencyPrefix(true);
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(2);
        var formatter = new FormatNumber("-1234");
        Assertions.assertEquals("-$1,234.00", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Hex input to formatted")
    public void testHexInput() {
        var formatter = new FormatNumber("0xFFFF");
        Assertions.assertEquals("65,535", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Big number formatting")
    public void testBigNumber() {
        var formatter = new FormatNumber("92233720368547758070");
        Assertions.assertEquals("92,233,720,368,547,758,070", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Empty delimiter means no grouping")
    public void testEmptyDelimiter() {
        state.setFormatDelimiter("");
        var formatter = new FormatNumber("1234567");
        Assertions.assertEquals("1234567", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Already formatted input with commas")
    public void testAlreadyFormattedInput() {
        var formatter = new FormatNumber("1,234,567");
        Assertions.assertEquals("1,234,567", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Decimal places with no currency")
    public void testDecimalPlacesNoCurrency() {
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(3);
        var formatter = new FormatNumber("1234");
        Assertions.assertEquals("1,234.000", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Zero decimal places")
    public void testZeroDecimalPlaces() {
        state.setFormatDecimalEnabled(true);
        state.setFormatDecimalPlaces(0);
        var formatter = new FormatNumber("1234");
        Assertions.assertEquals("1,234", formatter.toString(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("Single digit number")
    public void testSingleDigit() {
        var formatter = new FormatNumber("5");
        Assertions.assertEquals("5", formatter.toString(ConvertType.FORMAT));
    }
}
