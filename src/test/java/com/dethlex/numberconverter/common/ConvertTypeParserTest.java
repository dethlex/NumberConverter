package com.dethlex.numberconverter.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class ConvertTypeParserTest {

    @Test
    @DisplayName("startWith returns correct output prefixes")
    public void testStartWith() {
        Assertions.assertEquals("0x", ConvertTypeParser.startWith(ConvertType.HEX));
        Assertions.assertEquals("0b", ConvertTypeParser.startWith(ConvertType.BIN));
        Assertions.assertEquals("0", ConvertTypeParser.startWith(ConvertType.OCT));
        Assertions.assertEquals("", ConvertTypeParser.startWith(ConvertType.DEC));
        Assertions.assertEquals("", ConvertTypeParser.startWith(ConvertType.DATETIME));
        Assertions.assertEquals("", ConvertTypeParser.startWith(ConvertType.FORMAT));
    }

    @Test
    @DisplayName("radix returns correct base for each type")
    public void testRadix() {
        Assertions.assertEquals(16, ConvertTypeParser.radix(ConvertType.HEX));
        Assertions.assertEquals(2, ConvertTypeParser.radix(ConvertType.BIN));
        Assertions.assertEquals(8, ConvertTypeParser.radix(ConvertType.OCT));
        // DEC radix is 0; BigInteger.toString(0) falls back to base-10 per the Java spec
        Assertions.assertEquals(0, ConvertTypeParser.radix(ConvertType.DEC));
    }

    @Test
    @DisplayName("Parse hexadecimal: lowercase, uppercase, mixed prefix")
    public void testParseHex() {
        Assertions.assertEquals(BigInteger.valueOf(255), ConvertTypeParser.parse("0xff"));
        Assertions.assertEquals(BigInteger.valueOf(255), ConvertTypeParser.parse("0xFF"));
        Assertions.assertEquals(BigInteger.valueOf(255), ConvertTypeParser.parse("0XFF"));  // uppercase prefix
        Assertions.assertEquals(BigInteger.valueOf(16), ConvertTypeParser.parse("0x10"));
        Assertions.assertEquals(BigInteger.ZERO, ConvertTypeParser.parse("0x0"));
        Assertions.assertEquals(BigInteger.valueOf(-255), ConvertTypeParser.parse("-0xFF"));
    }

    @Test
    @DisplayName("Parse binary: lowercase and uppercase prefix")
    public void testParseBin() {
        Assertions.assertEquals(BigInteger.valueOf(5), ConvertTypeParser.parse("0b101"));
        Assertions.assertEquals(BigInteger.valueOf(5), ConvertTypeParser.parse("0B101"));  // uppercase prefix
        Assertions.assertEquals(BigInteger.ONE, ConvertTypeParser.parse("0b1"));
        Assertions.assertEquals(BigInteger.valueOf(-5), ConvertTypeParser.parse("-0b101"));
    }

    @Test
    @DisplayName("Parse octal: various magnitudes")
    public void testParseOct() {
        Assertions.assertEquals(BigInteger.valueOf(8), ConvertTypeParser.parse("010"));
        Assertions.assertEquals(BigInteger.valueOf(64), ConvertTypeParser.parse("0100"));
        Assertions.assertEquals(BigInteger.valueOf(100), ConvertTypeParser.parse("0144"));
        Assertions.assertEquals(BigInteger.valueOf(-8), ConvertTypeParser.parse("-010"));
    }

    @Test
    @DisplayName("Parse decimal: plain, negative, engineering notation")
    public void testParseDec() {
        Assertions.assertEquals(BigInteger.ZERO, ConvertTypeParser.parse("0"));    // single zero must NOT be OCT
        Assertions.assertEquals(BigInteger.valueOf(100), ConvertTypeParser.parse("100"));
        Assertions.assertEquals(BigInteger.valueOf(-42), ConvertTypeParser.parse("-42"));
        Assertions.assertEquals(BigInteger.valueOf(100), ConvertTypeParser.parse("1e2"));   // engineering notation
        Assertions.assertEquals(BigInteger.valueOf(1_000_000), ConvertTypeParser.parse("1e6"));
    }

    @Test
    @DisplayName("Bare prefix without digits falls to decimal and throws (not a valid number)")
    public void testBarePrefixWithoutDigitsThrows() {
        // "0x" and "0b" alone are not valid decimal numbers either
        Assertions.assertThrows(Exception.class, () -> ConvertTypeParser.parse("0x"));
        Assertions.assertThrows(Exception.class, () -> ConvertTypeParser.parse("0b"));
        Assertions.assertThrows(Exception.class, () -> ConvertTypeParser.parse("qwerty"));
    }
}
