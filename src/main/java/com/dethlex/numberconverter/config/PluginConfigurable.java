package com.dethlex.numberconverter.config;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginConfigurable implements SearchableConfigurable {

    private final PluginPersistentStateComponent instance;

    private SettingsForm gui;

    public PluginConfigurable() {
        instance = PluginPersistentStateComponent.getInstance();
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "NumberConverter";
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Number Converter";
    }

    @Override
    public @Nullable JComponent createComponent() {
        if (gui == null) {
            gui = new SettingsForm();
        }
        return gui.getRoot();
    }

    @Override
    public boolean isModified() {
        return gui != null && gui.isModified(instance);
    }

    @Override
    public void apply() throws ConfigurationException {
        gui.getData(instance);
    }

    @Override
    public void reset() {
        gui.setData(instance);
    }
}
