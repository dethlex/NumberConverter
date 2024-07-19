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

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public static PluginPersistentStateComponent getInstance() {
//        if (ApplicationManager.getApplication() == null) {
//            if (unitTestComponent == null) {
//                unitTestComponent = new PluginPersistentStateComponent();
//            }
//            return unitTestComponent;
//        }
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
