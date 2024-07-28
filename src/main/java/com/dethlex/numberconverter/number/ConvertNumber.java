package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.ConvertTypeParser;
import com.dethlex.numberconverter.common.IConverter;

import java.math.BigInteger;

public final class ConvertNumber extends IConverter {
    private final boolean negative;
    private final BigInteger integer;

    public ConvertNumber(String value) {
        value = value.strip().replaceAll("_", "");
        this.negative = value.startsWith("-");
        this.integer = ConvertTypeParser.parse(value);
    }

    // shifting negative non-decimal numbers
    private BigInteger shiftForType(ConvertType system) {
        var integer = this.integer;
        if (negative && system != ConvertType.DEC) {
            var p2 = Integer.highestOneBit(integer.bitCount() - 1) * 2;
            p2 = Math.max(p2, 16);
            integer = integer.add(BigInteger.ONE.shiftLeft(p2 * 2));
        }
        return integer;
    }

    public String toString(ConvertType system) {
        var integer = shiftForType(system);
        return ConvertTypeParser.startWith(system) + integer.toString(ConvertTypeParser.radix(system)).toUpperCase();
    }
}
