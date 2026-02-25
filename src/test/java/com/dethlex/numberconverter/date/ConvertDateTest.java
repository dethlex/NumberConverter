package com.dethlex.numberconverter.date;

import com.dethlex.numberconverter.ConvertActionTestBase;
import com.dethlex.numberconverter.common.ConvertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@DisplayName("→ DATE")
public class ConvertDateTest extends ConvertActionTestBase {

    @Test
    @DisplayName("Numeric timestamps (sec, milli, hex, bin, oct) and date strings convert to DATE")
    public void testToDate() {
        // Compute expected values using the same formatter ConvertDate uses so the test
        // is timezone-independent — both sides use the same default timezone.
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String atSec = fmt.format(new Date(1136207045L * 1000));
        String atMilli = fmt.format(new Date(1136207045000L));

        assertConverts(ConvertType.DATETIME,
                new TestCase("1136207045", atSec, "Unix seconds → DATE"),
                new TestCase("1136207045000", atMilli, "Unix milliseconds → DATE"),
                new TestCase("0x43B924C5", atSec, "HEX timestamp → DATE"),
                new TestCase("0b1000011101110010010010011000101", atSec, "BIN timestamp → DATE"),
                new TestCase("010356222305", atSec, "OCT timestamp → DATE"),
                new TestCase(atSec, atSec, "Date string round-trip")
        );
    }

    @Test
    @DisplayName("Negative timestamps (before epoch) convert to pre-1970 dates")
    public void testNegativeTimestamps() {
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String atNegSec = fmt.format(new Date(-1136207045L * 1000));
        String atNegMilli = fmt.format(new Date(-1136207045000L));

        assertConverts(ConvertType.DATETIME,
                new TestCase("-1136207045", atNegSec, "Negative Unix seconds → DATE"),
                new TestCase("-1136207045000", atNegMilli, "Negative Unix milliseconds → DATE"),
                new TestCase("-0x43B924C5", atNegSec, "Negative HEX timestamp → DATE"),
                new TestCase("-0b1000011101110010010010011000101", atNegSec, "Negative BIN timestamp → DATE"),
                new TestCase("-010356222305", atNegSec, "Negative OCT timestamp → DATE")
        );
    }
}
