package com.dethlex.numberconverter.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "NumberConverterState", storages = {@Storage("numberConverter.xml")})
public class PluginPersistentStateComponent implements PersistentStateComponent<PluginPersistentStateComponent> {
    private static PluginPersistentStateComponent unitTestComponent;

    private boolean surroundEnable = false;
    private String surroundLeft = "";
    private String surroundRight = "";

    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private boolean dateTimeUTC = false;

    private boolean isUpperCase = true;

    private String formatDelimiter = ",";
    private int formatGroupSize = 3;
    private boolean formatDecimalEnabled = false;
    private int formatDecimalPlaces = 2;
    private String formatCurrencySymbol = "";
    private boolean formatCurrencyPrefix = true;

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public boolean isDateTimeUTC() {
        return dateTimeUTC;
    }

    public void setDateTimeUTC(boolean dateTimeUTC) {
        this.dateTimeUTC = dateTimeUTC;
    }

    public boolean isSurroundEnable() {
        return surroundEnable;
    }

    public void setSurroundEnable(boolean surroundEnable) {
        this.surroundEnable = surroundEnable;
    }

    public String getSurroundLeft() {
        return surroundLeft;
    }

    public void setSurroundLeft(String surroundLeft) {
        this.surroundLeft = surroundLeft;
    }

    public String getSurroundRight() {
        return surroundRight;
    }

    public String surroundText(String text) {
        if (!surroundEnable) {
            return text;
        }
        return surroundLeft + text + surroundRight;
    }

    public void setSurroundRight(String surroundRight) {
        this.surroundRight = surroundRight;
    }

    public boolean isUpperCase() {
        return isUpperCase;
    }

    public void setUpperCase(boolean isUpperCase) {
        this.isUpperCase = isUpperCase;
    }

    public String getFormatDelimiter() {
        return formatDelimiter;
    }

    public void setFormatDelimiter(String formatDelimiter) {
        this.formatDelimiter = formatDelimiter;
    }

    public int getFormatGroupSize() {
        return formatGroupSize;
    }

    public void setFormatGroupSize(int formatGroupSize) {
        this.formatGroupSize = formatGroupSize;
    }

    public boolean isFormatDecimalEnabled() {
        return formatDecimalEnabled;
    }

    public void setFormatDecimalEnabled(boolean formatDecimalEnabled) {
        this.formatDecimalEnabled = formatDecimalEnabled;
    }

    public int getFormatDecimalPlaces() {
        return formatDecimalPlaces;
    }

    public void setFormatDecimalPlaces(int formatDecimalPlaces) {
        this.formatDecimalPlaces = formatDecimalPlaces;
    }

    public String getFormatCurrencySymbol() {
        return formatCurrencySymbol;
    }

    public void setFormatCurrencySymbol(String formatCurrencySymbol) {
        this.formatCurrencySymbol = formatCurrencySymbol;
    }

    public boolean isFormatCurrencyPrefix() {
        return formatCurrencyPrefix;
    }

    public void setFormatCurrencyPrefix(boolean formatCurrencyPrefix) {
        this.formatCurrencyPrefix = formatCurrencyPrefix;
    }

    public static PluginPersistentStateComponent getInstance() {
        if (ApplicationManager.getApplication() == null) {
            if (unitTestComponent == null) {
                unitTestComponent = new PluginPersistentStateComponent();
            }
            return unitTestComponent;
        }

        return ApplicationManager.getApplication().getService(PluginPersistentStateComponent.class);
    }

    @Override
    public @Nullable PluginPersistentStateComponent getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginPersistentStateComponent pluginPersistentStateComponent) {
        XmlSerializerUtil.copyBean(pluginPersistentStateComponent, this);
    }
}
