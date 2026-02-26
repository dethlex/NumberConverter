package com.dethlex.numberconverter;

import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ConvertAction — general behaviour")
public class ConvertActionTest extends ConvertActionTestBase {

    @Test
    @DisplayName("Surround text wraps converted output in configured brackets")
    public void testSurroundText() {
        state.setSurroundEnable(true);
        state.setSurroundLeft("[");
        state.setSurroundRight("]");
        assertConverts(ConvertType.HEX,
                new TestCase("100", "[0x64]", "DEC 100 → surrounded HEX"),
                new TestCase("255", "[0xFF]", "DEC 255 → surrounded HEX")
        );
    }

    @Test
    @DisplayName("Zero converts correctly across all number systems")
    public void testZeroAllTypes() {
        assertEquals("0", new ConvertAction(ConvertType.DEC).convertByType("0"), "Zero → DEC");
        assertEquals("0x0", new ConvertAction(ConvertType.HEX).convertByType("0"), "Zero → HEX");
        assertEquals("0b0", new ConvertAction(ConvertType.BIN).convertByType("0"), "Zero → BIN");
        assertEquals("00", new ConvertAction(ConvertType.OCT).convertByType("0"), "Zero → OCT");
    }
}
