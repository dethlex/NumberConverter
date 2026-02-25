package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("→ HEX")
public class ConvertHexTest extends ConvertActionTestBase {

    @Test
    @DisplayName("All number systems convert to hex")
    public void testToHex() {
        assertConverts(ConvertType.HEX,
                new TestCase("100", "0x64", "DEC → HEX"),
                new TestCase("0x64", "0x64", "HEX → HEX"),
                new TestCase("0144", "0x64", "OCT → HEX"),
                new TestCase("0b1100100", "0x64", "BIN → HEX"),

                new TestCase("1e2", "0x64", "Engineering notation → HEX"),

                new TestCase("-100", "0xFF9C", "Negative DEC → HEX"),
                new TestCase("-0x64", "0xFF9C", "Negative HEX → HEX"),
                new TestCase("-0144", "0xFF9C", "Negative OCT → HEX"),
                new TestCase("-0b1100100", "0xFF9C", "Negative BIN → HEX"),

                new TestCase("92233720368547758070", "0x4FFFFFFFFFFFFFFF6", "Big DEC → HEX"),
                new TestCase("0x4FFFFFFFFFFFFFFF6", "0x4FFFFFFFFFFFFFFF6", "Big HEX → HEX"),
                new TestCase("011777777777777777777766", "0x4FFFFFFFFFFFFFFF6", "Big OCT → HEX"),
                new TestCase("0b1001111111111111111111111111111111111111111111111111111111111110110", "0x4FFFFFFFFFFFFFFF6", "Big BIN → HEX"),

                new TestCase("qwerty", "can't convert", "Invalid input → HEX")
        );
    }

    @Test
    @DisplayName("Lowercase output when isUpperCase is false")
    public void testLowerCaseOutput() {
        state.setUpperCase(false);
        assertConverts(ConvertType.HEX,
                new TestCase("255", "0xff", "DEC 255 → lowercase HEX"),
                new TestCase("0xABCD", "0xabcd", "HEX uppercase letters → lowercase HEX"),
                new TestCase("-256", "0xff00", "Negative DEC → lowercase HEX")
        );
    }

    @Test
    @DisplayName("Comma-grouped input is stripped before hex conversion")
    public void testGroupingDelimiterStripped() {
        assertConverts(ConvertType.HEX,
                new TestCase("1,234", "0x4D2", "Comma-grouped → HEX")
        );
    }

    @Test
    @DisplayName("Zero converts to 0x0")
    public void testZero() {
        assertConverts(ConvertType.HEX, new TestCase("0", "0x0", "Zero → HEX"));
    }
}
