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

    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private boolean surroundEnable = false;
    private String surroundLeft = "";
    private String surroundRight = "";

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
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

    public static PluginPersistentStateComponent getInstance() {
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
