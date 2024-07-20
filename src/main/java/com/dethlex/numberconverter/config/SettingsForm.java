package com.dethlex.numberconverter.config;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.labels.LinkLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.Desktop;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingsForm {
    private JTextField dateTimeTextField;
    private JPanel root;
    private LinkLabel dateTimeHelpLink;
    private JCheckBox surroundEnableCheckBox;
    private JTextField surroundLeftTextField;
    private JTextField surroundRightTextField;
    private JLabel dateTimeTestLabel;

    private boolean canSave = false;

    public SettingsForm() {
        dateTimeHelpLink.setListener((aSource, aLinkData) -> {
            try {
                // open the link in the default browser
                Desktop.getDesktop().browse(new URI("https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, null);

        dateTimeTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                canSave = true;
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat(dateTimeTextField.getText());
                    dateTimeTestLabel.setText(formatter.format(new Date()));
                } catch (Exception ex) {
                    dateTimeTestLabel.setText("Invalid format");
                    canSave = false;
                }
            }
        });

        surroundEnableCheckBox.addChangeListener(e -> {
            surroundLeftTextField.setEnabled(surroundEnableCheckBox.isSelected());
            surroundRightTextField.setEnabled(surroundEnableCheckBox.isSelected());
        });
    }

    public JPanel getRoot() {
        return root;
    }

    public void getData(PluginPersistentStateComponent data) {
        data.setDateTimeFormat(dateTimeTextField.getText());
        data.setSurroundEnable(surroundEnableCheckBox.isSelected());
        data.setSurroundLeft(surroundLeftTextField.getText());
        data.setSurroundRight(surroundRightTextField.getText());
    }

    public void setData(PluginPersistentStateComponent data) {
        dateTimeTextField.setText(data.getDateTimeFormat());
        surroundEnableCheckBox.setSelected(data.isSurroundEnable());
        surroundLeftTextField.setText(data.getSurroundLeft());
        surroundRightTextField.setText(data.getSurroundRight());
    }

    public boolean isModified(PluginPersistentStateComponent data) {
        if (!canSave) {
            return false;
        }

        return !dateTimeTextField.getText().equals(data.getDateTimeFormat()) ||
                surroundEnableCheckBox.isSelected() != data.isSurroundEnable() ||
                !surroundLeftTextField.getText().equals(data.getSurroundLeft()) ||
                !surroundRightTextField.getText().equals(data.getSurroundRight());
    }
}
