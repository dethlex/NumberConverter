package com.dethlex.numberconverter.common;

import com.dethlex.numberconverter.date.ConvertDate;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class ConvertTypeParser {
    // Indexed by ConvertType ordinal: HEX=0, BIN=1, OCT=2, DEC=3, DATETIME=4, FORMAT=5
    private static final String[] typeStarts = {"0x", "0b", "0", "", "", ""};

    private static final int[] typeRadixes = {16, 2, 8, 0, 0, 0};

    public static String startWith(ConvertType type) {
        return typeStarts[type.ordinal()];
    }

    public static int radix(ConvertType type) {
        return typeRadixes[type.ordinal()];
    }

    public static BigInteger parse(String value) {
        var system = determineType(value);

        if (system == ConvertType.DATETIME) {
            String dateStr = value.startsWith("-") ? value.substring(1) : value;
            value = String.valueOf(ConvertDate.fromString(dateStr).getTime() / 1000);
            system = ConvertType.DEC;
        }

        if (system == ConvertType.DEC)
            return new BigDecimal(value).toBigInteger();

        return new BigInteger(substringNumber(value, startWith(system).length()), radix(system));
    }

    private static ConvertType determineType(String value) {
        boolean negative = value.startsWith("-");

        if (negative)
            value = value.substring(1);

        // at first try to convert from date
        if (ConvertDate.fromString(value) != null)
            return ConvertType.DATETIME;

        value = value.toUpperCase();
        for (int i = 0; i < typeStarts.length; i++) {
            var prefix = typeStarts[i].toUpperCase();
            if (!prefix.isEmpty() && value.startsWith(prefix) && value.length() > prefix.length()) {
                return ConvertType.values()[i];
            }
        }
        return ConvertType.DEC;
    }

    private static String substringNumber(String value, int beginIndex) {
        boolean negative = value.startsWith("-");

        if (negative)
            beginIndex += 1;

        value = value.substring(beginIndex);
        return negative ? "-" + value : value;
    }
}
