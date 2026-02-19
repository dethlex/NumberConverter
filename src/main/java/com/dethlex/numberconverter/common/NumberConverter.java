package com.dethlex.numberconverter.common;

import com.dethlex.numberconverter.config.PluginPersistentStateComponent;

import java.math.BigInteger;

public abstract class NumberConverter extends IConverter {
    protected static final String GROUPING_DELIMITERS_REGEX = "[_,\\s]";

    protected final BigInteger integer;
    protected final boolean negative;

    protected NumberConverter(String value) {
        var state = PluginPersistentStateComponent.getInstance();
        String delimiter = state.getFormatDelimiter();

        value = value.strip();
        value = stripCurrencySymbol(value, state);
        value = stripGroupingDelimiters(value, delimiter);

        this.negative = value.startsWith("-");
        this.integer = ConvertTypeParser.parse(value);
    }

    protected static String stripCurrencySymbol(String value, com.dethlex.numberconverter.config.PluginPersistentStateComponent state) {
        for (String symbol : state.getAllCurrencySymbols()) {
            if (symbol.isEmpty()) continue;
            if (value.startsWith(symbol)) return value.substring(symbol.length()).strip();
            if (value.endsWith(symbol)) return value.substring(0, value.length() - symbol.length()).strip();
        }
        return value;
    }

    protected static String stripGroupingDelimiters(String value, String delimiter) {
        value = value.replaceAll(GROUPING_DELIMITERS_REGEX, "");
        if (!delimiter.isEmpty() && !delimiter.matches(GROUPING_DELIMITERS_REGEX)) {
            value = value.replace(delimiter, "");
        }
        return value;
    }
}
