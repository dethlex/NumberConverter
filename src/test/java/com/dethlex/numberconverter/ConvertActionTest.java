package com.dethlex.numberconverter;

import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConvertActionTest {
    static class TestData {
        String Value;
        String Expected;
        String Error;

        public TestData(String value, String expected, String error) {
            Value = value;
            Expected = expected;
            Error = error;
        }
    }

    private void assertTestData(ConvertType system, TestData[] tests) {
        ConvertAction action = new ConvertAction(system);
        for (TestData test : tests) {
            Assertions.assertEquals(test.Expected, action.convertByType(test.Value), test.Error);
        }
    }

    @Test
    @DisplayName("To DEC")
    public void testToDec() {
        var tests = new TestData[]{
                new TestData("100", "100", "DEC != DEC"),
                new TestData("0x64", "100", "HEX != DEC"),
                new TestData("0144", "100", "OCT != DEC"),
                new TestData("0b1100100", "100", "BIN != DEC"),

                new TestData("1e2", "100", "Engineering DEC != DEC"),

                new TestData("-100", "-100", "Negative DEC != DEC"),
                new TestData("-0x64", "-100", "Negative HEX != DEC"),
                new TestData("-0144", "-100", "Negative OCT != DEC"),
                new TestData("-0b1100100", "-100", "Negative BIN != DEC"),

                new TestData("92233720368547758070", "92233720368547758070", "BIG DEC != BIG DEC"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "92233720368547758070", "BIG HEX != BIG DEC"),
                new TestData("011777777777777777777766", "92233720368547758070", "BIG OCT != BIG DEC"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "92233720368547758070", "BIG BIN != BIG DEC"),

                new TestData("qwerty", "can't convert", "Can't convert DEC"),
        };

        assertTestData(ConvertType.DEC, tests);
    }

    @Test
    @DisplayName("To HEX")
    public void testToHex() {
        var tests = new TestData[]{
                new TestData("100", "0x64", "DEC != HEX"),
                new TestData("0x64", "0x64", "HEX != HEX"),
                new TestData("0144", "0x64", "OCT != HEX"),
                new TestData("0b1100100", "0x64", "BIN != HEX"),

                new TestData("1e2", "0x64", "Engineering DEC != HEX"),

                new TestData("-100", "0xFF9C", "Negative DEC != HEX"),
                new TestData("-0x64", "0xFF9C", "Negative HEX != HEX"),
                new TestData("-0144", "0xFF9C", "Negative OCT != HEX"),
                new TestData("-0b1100100", "0xFF9C", "Negative BIN != HEX"),

                new TestData("92233720368547758070", "0x4FFFFFFFFFFFFFFF6", "BIG DEC != BIG HEX"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "0x4FFFFFFFFFFFFFFF6", "BIG HEX != BIG HEX"),
                new TestData("011777777777777777777766", "0x4FFFFFFFFFFFFFFF6", "BIG OCT != BIG HEX"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "0x4FFFFFFFFFFFFFFF6", "BIG BIN != BIG HEX"),

                new TestData("qwerty", "can't convert", "Can't convert HEX"),
        };

        assertTestData(ConvertType.HEX, tests);
    }

    @Test
    @DisplayName("To OCT")
    public void testToOct() {
        var tests = new TestData[]{
                new TestData("100", "0144", "DEC != OCT"),
                new TestData("0x64", "0144", "HEX != OCT"),
                new TestData("0144", "0144", "OCT != OCT"),
                new TestData("0b1100100", "0144", "BIN != OCT"),

                new TestData("1e2", "0144", "Engineering DEC != OCT"),

                new TestData("-100", "0177634", "Negative DEC != OCT"),
                new TestData("-0x64", "0177634", "Negative HEX != OCT"),
                new TestData("-0144", "0177634", "Negative OCT != OCT"),
                new TestData("-0b1100100", "0177634", "Negative BIN != OCT"),

                new TestData("92233720368547758070", "011777777777777777777766", "BIG DEC != BIG OCT"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "011777777777777777777766", "BIG HEX != BIG OCT"),
                new TestData("011777777777777777777766", "011777777777777777777766", "BIG OCT != BIG OCT"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "011777777777777777777766", "BIG BIN != BIG OCT"),

                new TestData("qwerty", "can't convert", "Can't convert OCT"),
        };

        assertTestData(ConvertType.OCT, tests);
    }

    @Test
    @DisplayName("To BIN")
    public void testToBin() {
        var tests = new TestData[]{
                new TestData("100", "0b1100100", "DEC != BIN"),
                new TestData("0x64", "0b1100100", "HEX != BIN"),
                new TestData("0144", "0b1100100", "OCT != BIN"),
                new TestData("0b1100100", "0b1100100", "BIN != BIN"),

                new TestData("1e2", "0b1100100", "Engineering DEC  != BIN"),

                new TestData("-100", "0b1111111110011100", "Negative DEC != BIN"),
                new TestData("-0x64", "0b1111111110011100", "Negative HEX != BIN"),
                new TestData("-0144", "0b1111111110011100", "Negative OCT != BIN"),
                new TestData("-0b1100100", "0b1111111110011100", "Negative BIN != BIN"),

                new TestData("92233720368547758070", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG DEC != BIG BIN"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG HEX != BIG BIN"),
                new TestData("011777777777777777777766", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG OCT != BIG BIN"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG BIN != BIG BIN"),

                new TestData("qwerty", "can't convert", "Can't convert BIN"),
        };

        assertTestData(ConvertType.BIN, tests);
    }

    @Test
    @DisplayName("To DATE")
    public void testToDate() {
        var tests = new TestData[]{
                new TestData("1136207045", "2006-01-02 15:04:05", "UNIX SEC != DATE"),
                new TestData("1136207045000", "2006-01-02 15:04:05", "UNIX MILLI != DATE"),
                new TestData("1136207045000", "2006-01-02 15:04:05", "UNIX MILLI != DATE"),
                new TestData("0x43B924C5", "2006-01-02 15:04:05", "HEX != DATE"),
                new TestData("0b1000011101110010010010011000101", "2006-01-02 15:04:05", "BIN != DATE"),
                new TestData("010356222305", "2006-01-02 15:04:05", "OCT != DATE"),
                new TestData("2006-01-02 15:04:05", "2006-01-02 15:04:05", "DATE != DATE"),
        };

        assertTestData(ConvertType.DATETIME, tests);
    }

    @Test
    @DisplayName("To FORMAT")
    public void testToFormat() {
        var tests = new TestData[]{
                new TestData("1234567890", "1,234,567,890", "DEC format with comma grouping"),
                new TestData("0xFF", "255", "HEX to formatted (small, no grouping)"),
                new TestData("0b11111111", "255", "BIN to formatted (small, no grouping)"),
                new TestData("-1234567", "-1,234,567", "Negative number formatting"),
                new TestData("100", "100", "Small number no grouping needed"),
                new TestData("1000", "1,000", "Exactly one group boundary"),
                new TestData("12345678", "12,345,678", "Standard grouping"),
                new TestData("1_000_000", "1,000,000", "Already underscore-formatted input"),
                new TestData("qwerty", "can't convert", "Can't convert FORMAT"),
        };

        assertTestData(ConvertType.FORMAT, tests);
    }
}