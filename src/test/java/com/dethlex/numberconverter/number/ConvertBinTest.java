package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.ConvertActionTestBase.TestCase;
import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("→ BIN")
public class ConvertBinTest extends ConvertActionTestBase {

    @Test
    @DisplayName("All number systems convert to binary")
    public void testToBin() {
        assertConverts(ConvertType.BIN,
                new TestCase("100",         "0b1100100",              "DEC → BIN"),
                new TestCase("0x64",         "0b1100100",              "HEX → BIN"),
                new TestCase("0144",         "0b1100100",              "OCT → BIN"),
                new TestCase("0b1100100",    "0b1100100",              "BIN → BIN"),

                new TestCase("1e2",          "0b1100100",              "Engineering notation → BIN"),

                new TestCase("-100",         "0b1111111110011100",      "Negative DEC → BIN"),
                new TestCase("-0x64",        "0b1111111110011100",      "Negative HEX → BIN"),
                new TestCase("-0144",        "0b1111111110011100",      "Negative OCT → BIN"),
                new TestCase("-0b1100100",   "0b1111111110011100",      "Negative BIN → BIN"),

                new TestCase("92233720368547758070",                                                          "0b1001111111111111111111111111111111111111111111111111111111111110110", "Big DEC → BIN"),
                new TestCase("0x4FFFFFFFFFFFFFFF6",                                                           "0b1001111111111111111111111111111111111111111111111111111111111110110", "Big HEX → BIN"),
                new TestCase("011777777777777777777766",                                                       "0b1001111111111111111111111111111111111111111111111111111111111110110", "Big OCT → BIN"),
                new TestCase("0b1001111111111111111111111111111111111111111111111111111111111110110",           "0b1001111111111111111111111111111111111111111111111111111111111110110", "Big BIN → BIN"),

                new TestCase("qwerty",       "can't convert",          "Invalid input → BIN")
        );
    }

    @Test
    @DisplayName("Zero converts to 0b0")
    public void testZero() {
        assertConverts(ConvertType.BIN, new TestCase("0", "0b0", "Zero → BIN"));
    }
}
