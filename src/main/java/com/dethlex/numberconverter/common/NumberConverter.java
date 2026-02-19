package com.dethlex.numberconverter.common;

import com.dethlex.numberconverter.config.PluginPersistentStateComponent;

import java.math.BigInteger;

public abstract class NumberConverter extends IConverter {
    protected static final String GROUPING_DELIMITERS_REGEX = "[_,\\s]";

    protected final BigInteger integer;
    protected final boolean negative;

    protected NumberConverter(String value) {
        var state = PluginPersistentStateComponent.getInstance();

        value = value.strip();
        value = stripCurrencySymbol(value, state.getAllCurrencySymbols());
        value = stripGroupingDelimiters(value, state.getFormatDelimiter());

        this.negative = value.startsWith("-");
        this.integer = ConvertTypeParser.parse(value);
    }

    protected static String stripCurrencySymbol(String value, java.util.List<String> allCurrencySymbols) {
        for (String symbol : allCurrencySymbols) {
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
