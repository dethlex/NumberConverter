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

        assertConverts(ConvertType.DATETIME,
                new TestCase("1136207045", atSec, "Unix seconds → DATE"),
                new TestCase("0x43B924C5", atSec, "HEX timestamp → DATE"),
                new TestCase("0b1000011101110010010010011000101", atSec, "BIN timestamp → DATE"),
                new TestCase("010356222305", atSec, "OCT timestamp → DATE"),
                new TestCase(atSec, atSec, "Date string round-trip")
        );
    }

    @Test
    @DisplayName("Date strings convert to number systems (DEC, HEX, OCT, BIN)")
    public void testDateToNumberSystems() {
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String dateStr = fmt.format(new Date(1136207045L * 1000));

        assertConverts(ConvertType.DEC, new TestCase(dateStr, "1136207045", "DATE → DEC"));
        assertConverts(ConvertType.HEX, new TestCase(dateStr, "0x43B924C5", "DATE → HEX"));
        assertConverts(ConvertType.OCT, new TestCase(dateStr, "010356222305", "DATE → OCT"));
        assertConverts(ConvertType.BIN, new TestCase(dateStr, "0b1000011101110010010010011000101", "DATE → BIN"));
        assertConverts(ConvertType.FORMAT, new TestCase(dateStr, "1,136,207,045", "DATE → FORMAT"));
    }

    @Test
    @DisplayName("Milliseconds mode: timestamps interpreted as millis and date strings produce millis")
    public void testMillisecondsMode() {
        state.setDateTimeMilliseconds(true);
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String dateStr = fmt.format(new Date(1136207045L * 1000));
        String atMilli = fmt.format(new Date(1136207045000L));
        String atNegMilli = fmt.format(new Date(-1136207045000L));

        assertConverts(ConvertType.DATETIME,
                new TestCase("1136207045000", atMilli, "Unix milliseconds → DATE (ms mode)"),
                new TestCase("-1136207045000", atNegMilli, "Negative Unix milliseconds → DATE (ms mode)")
        );
        assertConverts(ConvertType.DEC, new TestCase(dateStr, "1136207045000", "DATE → DEC (ms)"));
        assertConverts(ConvertType.HEX, new TestCase(dateStr, "0x1088B37A188", "DATE → HEX (ms)"));
        assertConverts(ConvertType.OCT, new TestCase(dateStr, "020421315720610", "DATE → OCT (ms)"));
        assertConverts(ConvertType.FORMAT, new TestCase(dateStr, "1,136,207,045,000", "DATE → FORMAT (ms)"));
    }

    @Test
    @DisplayName("Custom date format with millis pattern (SSS) round-trips correctly")
    public void testCustomDateFormatWithMillis() {
        state.setDateTimeFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String dateStr = fmt.format(new Date(1136207045123L));

        assertConverts(ConvertType.DATETIME, new TestCase(dateStr, dateStr, "Date string with SSS round-trip"));
        assertConverts(ConvertType.DEC, new TestCase(dateStr, "1136207045", "Date with SSS → DEC (seconds mode)"));

        state.setDateTimeMilliseconds(true);
        assertConverts(ConvertType.DEC, new TestCase(dateStr, "1136207045123", "Date with SSS → DEC (ms mode)"));
    }

    @Test
    @DisplayName("Negative timestamps (before epoch) convert to pre-1970 dates")
    public void testNegativeTimestamps() {
        SimpleDateFormat fmt = new SimpleDateFormat(state.getDateTimeFormat());
        String atNegSec = fmt.format(new Date(-1136207045L * 1000));

        assertConverts(ConvertType.DATETIME,
                new TestCase("-1136207045", atNegSec, "Negative Unix seconds → DATE"),
                new TestCase("-0x43B924C5", atNegSec, "Negative HEX timestamp → DATE"),
                new TestCase("-0b1000011101110010010010011000101", atNegSec, "Negative BIN timestamp → DATE"),
                new TestCase("-010356222305", atNegSec, "Negative OCT timestamp → DATE")
        );
    }
}
