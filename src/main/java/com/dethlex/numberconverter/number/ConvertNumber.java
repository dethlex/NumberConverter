package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.ConvertTypes;
import com.dethlex.numberconverter.common.IConverter;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class ConvertNumber extends IConverter {
    boolean negative;

    BigInteger integer;

    public ConvertNumber(String value) {
        value = value.strip().replaceAll("_", "");
        this.negative = value.startsWith("-");
        this.integer = ToBigInteger(value);
    }

    private String SubstringNumber(String value, int beginIndex) {
        if (negative)
            beginIndex += 1;

        value = value.substring(beginIndex);
        return negative ? "-" + value : value;
    }

    private BigInteger ToBigInteger(String value) {
        var system = ConvertNumberType.numeralSystem(value);

        if (system == ConvertTypes.DEC)
            return new BigDecimal(value).toBigInteger();

        return new BigInteger(
                SubstringNumber(value, ConvertNumberType.startWith(system).length()),
                ConvertNumberType.radix(system)
        );
    }

    // shifting negative non-decimal numbers
    private BigInteger ShiftForType(ConvertTypes system) {
        var integer = this.integer;
        if (negative && system != ConvertTypes.DEC) {
            var p2 = Integer.highestOneBit(integer.bitCount() - 1) * 2;
            p2 = Math.max(p2, 16);
            integer = integer.add(BigInteger.ONE.shiftLeft(p2 * 2));
        }
        return integer;
    }

    public String toString(ConvertTypes system) {
        var integer = ShiftForType(system);
        return ConvertNumberType.startWith(system) + integer.toString(ConvertNumberType.radix(system)).toUpperCase();
    }
}
