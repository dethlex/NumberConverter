package com.dethlex.numberconverter.config;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.labels.LinkLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.Desktop;
import java.net.URI;
import java.text.SimpleDateFormat;

public class SettingsForm {
    private JTextField dateTimeTextField;
    private JPanel root;
    private LinkLabel dateTimeHelpLink;
    private JCheckBox surroundEnableCheckBox;
    private JTextField surroundLeftTextField;
    private JTextField surroundRightTextField;
    private JLabel dateTimeTestLabel;
    private JCheckBox dateTimeUTCCheckBox;
    private JRadioButton lowerCaseRadioButton;
    private JRadioButton upperCaseRadioButton;

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
                    var date = formatter.parse("2006-01-02 15:04:05");
                    if (dateTimeUTCCheckBox.isSelected()) {
                        formatter.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                    }
                    dateTimeTestLabel.setText(formatter.format(date) + " " +
                            (dateTimeUTCCheckBox.isSelected() ? "(UTC)" : "(Local)")
                    );
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

        dateTimeUTCCheckBox.addChangeListener(e -> {
            dateTimeTextField.setText(dateTimeTextField.getText());
        });
    }

    public JPanel getRoot() {
        return root;
    }

    public void getData(PluginPersistentStateComponent data) {
        data.setDateTimeFormat(dateTimeTextField.getText());
        data.setDateTimeUTC(dateTimeUTCCheckBox.isSelected());
        data.setSurroundEnable(surroundEnableCheckBox.isSelected());
        data.setSurroundLeft(surroundLeftTextField.getText());
        data.setSurroundRight(surroundRightTextField.getText());
        data.setUpperCase(upperCaseRadioButton.isSelected());
    }

    public void setData(PluginPersistentStateComponent data) {
        dateTimeTextField.setText(data.getDateTimeFormat());
        dateTimeUTCCheckBox.setSelected(data.isDateTimeUTC());
        surroundEnableCheckBox.setSelected(data.isSurroundEnable());
        surroundLeftTextField.setText(data.getSurroundLeft());
        surroundRightTextField.setText(data.getSurroundRight());
        lowerCaseRadioButton.setSelected(!data.isUpperCase());
        upperCaseRadioButton.setSelected(data.isUpperCase());
    }

    public boolean isModified(PluginPersistentStateComponent data) {
        if (!canSave) {
            return false;
        }

        return !dateTimeTextField.getText().equals(data.getDateTimeFormat()) ||
                dateTimeUTCCheckBox.isSelected() != data.isDateTimeUTC() ||
                surroundEnableCheckBox.isSelected() != data.isSurroundEnable() ||
                !surroundLeftTextField.getText().equals(data.getSurroundLeft()) ||
                !surroundRightTextField.getText().equals(data.getSurroundRight()) ||
                upperCaseRadioButton.isSelected() != data.isUpperCase();
    }
}
