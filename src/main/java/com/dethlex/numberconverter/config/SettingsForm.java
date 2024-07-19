package com.dethlex.numberconverter.config;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.labels.LinkLabel;
import com.intellij.ui.components.labels.LinkListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsForm {
    private JTextField fieldDateTime;
    private JPanel root;
    private LinkLabel helpDateTime;
    private JCheckBox enableCheckBox;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel testDateTime;
    private JLabel labelDateHelp;

    private boolean canSave = false;

    public SettingsForm() {
        helpDateTime.setListener((aSource, aLinkData) -> {
            try {
                // open the link in the default browser
                Desktop.getDesktop().browse(new URI("https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, null);

        fieldDateTime.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                canSave = true;
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat(fieldDateTime.getText());
                    testDateTime.setText(formatter.format(new Date()));
                } catch (Exception ex) {
                    testDateTime.setText("Invalid format");
                    canSave = false;
                }
            }
        });
    }

    public JPanel getRoot() {
        return root;
    }

    public void getData(PluginPersistentStateComponent data) {
        data.setDateTimeFormat(fieldDateTime.getText());
    }

    public void setData(PluginPersistentStateComponent data) {
        fieldDateTime.setText(data.getDateTimeFormat());
    }

    public boolean isModified(PluginPersistentStateComponent data) {
        if (!canSave) {
            return false;
        }
        return !fieldDateTime.getText().equals(data.getDateTimeFormat());
    }

}
