package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("→ OCT")
public class ConvertOctTest extends ConvertActionTestBase {

    @Test
    @DisplayName("All number systems convert to octal")
    public void testToOct() {
        assertConverts(ConvertType.OCT,
                new TestCase("100", "0144", "DEC → OCT"),
                new TestCase("0x64", "0144", "HEX → OCT"),
                new TestCase("0144", "0144", "OCT → OCT"),
                new TestCase("0b1100100", "0144", "BIN → OCT"),

                new TestCase("1e2", "0144", "Engineering notation → OCT"),

                new TestCase("-100", "0177634", "Negative DEC → OCT"),
                new TestCase("-0x64", "0177634", "Negative HEX → OCT"),
                new TestCase("-0144", "0177634", "Negative OCT → OCT"),
                new TestCase("-0b1100100", "0177634", "Negative BIN → OCT"),

                new TestCase("92233720368547758070", "011777777777777777777766", "Big DEC → OCT"),
                new TestCase("0x4FFFFFFFFFFFFFFF6", "011777777777777777777766", "Big HEX → OCT"),
                new TestCase("011777777777777777777766", "011777777777777777777766", "Big OCT → OCT"),
                new TestCase("0b1001111111111111111111111111111111111111111111111111111111111110110", "011777777777777777777766", "Big BIN → OCT"),

                new TestCase("qwerty", "can't convert", "Invalid input → OCT")
        );
    }

    @Test
    @DisplayName("Zero converts to 00 (octal prefix + digit)")
    public void testZero() {
        assertConverts(ConvertType.OCT, new TestCase("0", "00", "Zero → OCT"));
    }
}
