package com.dethlex.numberconverter;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class ConvertNumber {
    boolean negative;

    BigInteger integer;

    public ConvertNumber(String value) {
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
        var system = ConvertType.numeralSystem(value);

        if (system == NumeralSystem.DEC)
            return new BigDecimal(value).toBigInteger();

        return new BigInteger(
                SubstringNumber(value, ConvertType.startWith(system).length()),
                ConvertType.radix(system)
        );
    }

    // shifting negative non-decimal numbers
    private BigInteger ShiftForType(NumeralSystem system) {
        var integer = this.integer;
        if (negative && system != NumeralSystem.DEC) {
            var p2 = Integer.highestOneBit(integer.bitCount() - 1) * 2;
            p2 = Math.max(p2, 16);
            integer = integer.add(BigInteger.ONE.shiftLeft(p2 * 2));
        }
        return integer;
    }

    public String toString(NumeralSystem system) {
        var integer = ShiftForType(system);
        return ConvertType.startWith(system) + integer.toString(ConvertType.radix(system)).toUpperCase();
    }
}
