package com.dethlex.numberconverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.Assert;

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

    private void assertTestData(ConvertTypes system, TestData[] tests) {
        ConvertAction action = new ConvertAction(system);
        for (TestData test : tests) {
            Assert.assertEquals(test.Error, test.Expected, action.ConvertByType(test.Value));
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

        assertTestData(ConvertTypes.DEC, tests);
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

                new TestData("-100", "0xFFFFFF9C", "Negative DEC != HEX"),
                new TestData("-0x64", "0xFFFFFF9C", "Negative HEX != HEX"),
                new TestData("-0144", "0xFFFFFF9C", "Negative OCT != HEX"),
                new TestData("-0b1100100", "0xFFFFFF9C", "Negative BIN != HEX"),

                new TestData("92233720368547758070", "0x4FFFFFFFFFFFFFFF6", "BIG DEC != BIG HEX"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "0x4FFFFFFFFFFFFFFF6", "BIG HEX != BIG HEX"),
                new TestData("011777777777777777777766", "0x4FFFFFFFFFFFFFFF6", "BIG OCT != BIG HEX"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "0x4FFFFFFFFFFFFFFF6", "BIG BIN != BIG HEX"),

                new TestData("qwerty", "can't convert", "Can't convert HEX"),
        };

        assertTestData(ConvertTypes.HEX, tests);
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

                new TestData("-100", "037777777634", "Negative DEC != OCT"),
                new TestData("-0x64", "037777777634", "Negative HEX != OCT"),
                new TestData("-0144", "037777777634", "Negative OCT != OCT"),
                new TestData("-0b1100100", "037777777634", "Negative BIN != OCT"),

                new TestData("92233720368547758070", "011777777777777777777766", "BIG DEC != BIG OCT"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "011777777777777777777766", "BIG HEX != BIG OCT"),
                new TestData("011777777777777777777766", "011777777777777777777766", "BIG OCT != BIG OCT"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "011777777777777777777766", "BIG BIN != BIG OCT"),

                new TestData("qwerty", "can't convert", "Can't convert OCT"),
        };

        assertTestData(ConvertTypes.OCT, tests);
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

                new TestData("-100", "0b11111111111111111111111110011100", "Negative DEC != BIN"),
                new TestData("-0x64", "0b11111111111111111111111110011100", "Negative HEX != BIN"),
                new TestData("-0144", "0b11111111111111111111111110011100", "Negative OCT != BIN"),
                new TestData("-0b1100100", "0b11111111111111111111111110011100", "Negative BIN != BIN"),

                new TestData("92233720368547758070", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG DEC != BIG BIN"),
                new TestData("0x4FFFFFFFFFFFFFFF6", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG HEX != BIG BIN"),
                new TestData("011777777777777777777766", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG OCT != BIG BIN"),
                new TestData("0b1001111111111111111111111111111111111111111111111111111111111110110", "0b1001111111111111111111111111111111111111111111111111111111111110110", "BIG BIN != BIG BIN"),

                new TestData("qwerty", "can't convert", "Can't convert BIN"),
        };

        assertTestData(ConvertTypes.BIN, tests);
    }

    @Test
    @DisplayName("To DATE")
    public void testToDate() {
        var tests = new TestData[]{
                new TestData("1721073026", "2024-07-15 22:50:26", "UNIX SEC != DATE"),
                new TestData("1721073026000", "2024-07-15 22:50:26", "UNIX MILLI != DATE"),
        };

        assertTestData(ConvertTypes.DATETIME, tests);
    }
}