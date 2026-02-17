package com.dethlex.numberconverter.number;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.ConvertTypeParser;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;

import java.math.BigInteger;

public final class ConvertNumber extends IConverter {
    private final boolean negative;
    private final BigInteger integer;

    public ConvertNumber(String value) {
        var state = PluginPersistentStateComponent.getInstance();
        String delimiter = state.getFormatDelimiter();

        // Strip common formatting characters plus the configured delimiter
        value = value.strip();
        if (!delimiter.isEmpty()) {
            value = value.replace(delimiter, "");
        }
        value = value.replaceAll("[_,\\s]", "");

        this.negative = value.startsWith("-");
        this.integer = ConvertTypeParser.parse(value);
    }

    // shifting negative non-decimal numbers
    private BigInteger shiftForType(ConvertType system) {
        var integer = this.integer;
        if (negative && system != ConvertType.DEC) {
            var bit = Integer.highestOneBit(integer.bitCount() - 1) * 2;
            var multiplier = 2;
            bit = Math.max(bit, 16);
            if (bit <= 32) {
                multiplier = 1;
            }
            integer = integer.add(BigInteger.ONE.shiftLeft(bit * multiplier));
        }
        return integer;
    }

    public String toString(ConvertType system) {
        var state = PluginPersistentStateComponent.getInstance();
        var integer = shiftForType(system);
        var number = integer.toString(ConvertTypeParser.radix(system));
        number =  state.isUpperCase() ? number.toUpperCase() : number.toLowerCase();
        return ConvertTypeParser.startWith(system) + number;
    }
}
