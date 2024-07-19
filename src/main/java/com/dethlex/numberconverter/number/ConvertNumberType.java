package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertTypes;

public final class ConvertNumberType {
    private static final String[] typeStarts = {"0x", "0b", "0", ""};

    private static final int[] typeRadixes = {16, 2, 8, 0};

    public static String startWith(ConvertTypes type) {
        return typeStarts[type.ordinal()];
    }

    public static int radix(ConvertTypes type) {
        return typeRadixes[type.ordinal()];
    }

    public static ConvertTypes numeralSystem(String value) {
        if (value.startsWith("-"))
            value = value.substring(1);

        value = value.toUpperCase();
        for (int i = 0; i < typeStarts.length; i++) {
            if (value.startsWith(typeStarts[i].toUpperCase())) {
                return ConvertTypes.values()[i];
            }
        }
        return ConvertTypes.DEC;
    }
}
