package com.dethlex.numberconverter;

public final class ConvertType {
    private static final String[] typeStarts = {"0x", "0b", "0", ""};

    private static final int[] typeRadixes = {16, 2, 8, 0};

    public static String startWith(NumeralSystem type) {
        return typeStarts[type.ordinal()];
    }

    public static int radix(NumeralSystem type) {
        return typeRadixes[type.ordinal()];
    }

    public static NumeralSystem numeralSystem(String value) {
        if (value.startsWith("-"))
            value = value.substring(1);

        value = value.toUpperCase();
        for (int i = 0; i < typeStarts.length; i++) {
            if (value.startsWith(typeStarts[i].toUpperCase())) {
                return NumeralSystem.values()[i];
            }
        }
        return NumeralSystem.DEC;
    }
}
