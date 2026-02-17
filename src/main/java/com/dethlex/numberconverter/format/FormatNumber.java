package com.dethlex.numberconverter.format;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.ConvertTypeParser;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;

import java.math.BigInteger;

public final class FormatNumber extends IConverter {
    private final BigInteger integer;
    private final boolean negative;

    public FormatNumber(String value) {
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

    @Override
    public String toString(ConvertType type) {
        var state = PluginPersistentStateComponent.getInstance();

        String delimiter = state.getFormatDelimiter();
        int groupSize = state.getFormatGroupSize();
        boolean enableDecimals = state.isFormatDecimalEnabled();
        int decimalPlaces = state.getFormatDecimalPlaces();
        String currencySymbol = state.getFormatCurrencySymbol();
        boolean currencyPrefix = state.isFormatCurrencyPrefix();

        BigInteger absValue = integer.abs();
        String digits = absValue.toString();

        String grouped = applyGrouping(digits, delimiter, groupSize);

        StringBuilder result = new StringBuilder();

        if (negative) {
            result.append("-");
        }

        if (currencyPrefix && !currencySymbol.isEmpty()) {
            result.append(currencySymbol);
        }

        result.append(grouped);

        if (enableDecimals && decimalPlaces > 0) {
            String decSep = delimiter.equals(".") ? "," : ".";
            result.append(decSep);
            result.append("0".repeat(decimalPlaces));
        }

        if (!currencyPrefix && !currencySymbol.isEmpty()) {
            result.append(currencySymbol);
        }

        return result.toString();
    }

    private String applyGrouping(String digits, String delimiter, int groupSize) {
        if (groupSize <= 0 || delimiter.isEmpty()) {
            return digits;
        }

        StringBuilder sb = new StringBuilder();
        int length = digits.length();
        int firstGroupSize = length % groupSize;

        if (firstGroupSize > 0) {
            sb.append(digits, 0, firstGroupSize);
        }

        for (int i = firstGroupSize; i < length; i += groupSize) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(digits, i, i + groupSize);
        }

        return sb.toString();
    }
}
