package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.ConvertActionTestBase.TestCase;
import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("→ DEC")
public class ConvertDecTest extends ConvertActionTestBase {

    @Test
    @DisplayName("All number systems convert to decimal")
    public void testToDec() {
        assertConverts(ConvertType.DEC,
                new TestCase("100",         "100",                  "DEC → DEC"),
                new TestCase("0x64",         "100",                  "HEX → DEC"),
                new TestCase("0144",         "100",                  "OCT → DEC"),
                new TestCase("0b1100100",    "100",                  "BIN → DEC"),

                new TestCase("1e2",          "100",                  "Engineering notation → DEC"),

                new TestCase("-100",         "-100",                 "Negative DEC → DEC"),
                new TestCase("-0x64",        "-100",                 "Negative HEX → DEC"),
                new TestCase("-0144",        "-100",                 "Negative OCT → DEC"),
                new TestCase("-0b1100100",   "-100",                 "Negative BIN → DEC"),

                new TestCase("92233720368547758070",                                                          "92233720368547758070", "Big DEC → DEC"),
                new TestCase("0x4FFFFFFFFFFFFFFF6",                                                           "92233720368547758070", "Big HEX → DEC"),
                new TestCase("011777777777777777777766",                                                       "92233720368547758070", "Big OCT → DEC"),
                new TestCase("0b1001111111111111111111111111111111111111111111111111111111111110110",           "92233720368547758070", "Big BIN → DEC"),

                new TestCase("qwerty",       "can't convert",        "Invalid input → DEC")
        );
    }

    @Test
    @DisplayName("Input prefix detection is case-insensitive")
    public void testCaseInsensitivePrefixInput() {
        assertConverts(ConvertType.DEC,
                new TestCase("0XFF",      "255", "Uppercase 0X prefix"),
                new TestCase("0B1100100", "100", "Uppercase 0B prefix"),
                new TestCase("0xff",      "255", "Lowercase 0xff")
        );
    }

    @Test
    @DisplayName("Grouping delimiters (comma, underscore, space) are stripped from input")
    public void testGroupingDelimitersStripped() {
        assertConverts(ConvertType.DEC,
                new TestCase("1,234",     "1234",    "Comma-grouped"),
                new TestCase("1_234",     "1234",    "Underscore-grouped"),
                new TestCase("1 234",     "1234",    "Space-grouped"),
                new TestCase("1,234,567", "1234567", "Multi-group comma")
        );
    }

    @Test
    @DisplayName("Leading and trailing whitespace is stripped from input")
    public void testWhitespaceStripping() {
        assertConverts(ConvertType.DEC,
                new TestCase("  100  ",  "100",  "Leading/trailing spaces"),
                new TestCase(" 0x64 ",   "100",  "Spaces around hex literal"),
                new TestCase("  -42  ",  "-42",  "Spaces around negative number")
        );
    }

    @Test
    @DisplayName("Zero converts to decimal zero")
    public void testZero() {
        assertConverts(ConvertType.DEC, new TestCase("0", "0", "Zero → DEC"));
    }
}
