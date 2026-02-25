package com.dethlex.numberconverter;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

public abstract class ConvertActionTestBase {

    protected final PluginPersistentStateComponent state = PluginPersistentStateComponent.getInstance();

    @AfterEach
    void resetState() {
        state.setUpperCase(true);
        state.setSurroundEnable(false);
        state.setSurroundLeft("");
        state.setSurroundRight("");
        state.setFormatDelimiter(",");
        state.setFormatGroupSize(3);
        state.setFormatDecimalEnabled(false);
        state.setFormatDecimalPlaces(2);
        state.setFormatCurrencySymbol("");
        state.setFormatCurrencyPrefix(true);
    }

    public record TestCase(String input, String expected, String message) {
    }

    public void assertConverts(ConvertType type, TestCase... cases) {
        ConvertAction action = new ConvertAction(type);
        for (TestCase tc : cases) {
            Assertions.assertEquals(tc.expected(), action.convertByType(tc.input()), tc.message());
        }
    }
}
