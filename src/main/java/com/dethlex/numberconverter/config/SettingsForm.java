package com.dethlex.numberconverter.config;

import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.labels.LinkLabel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
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
    private JCheckBox dateTimeMillisecondsCheckBox;
    private JRadioButton lowerCaseRadioButton;
    private JRadioButton upperCaseRadioButton;
    private JTextField formatDelimiterTextField;
    private JSpinner formatGroupSizeSpinner;
    private JCheckBox formatDecimalCheckBox;
    private JSpinner formatDecimalPlacesSpinner;
    private JComboBox<String> formatCurrencySymbolComboBox;
    private JRadioButton formatCurrencyPrefixRadio;
    private JRadioButton formatCurrencySuffixRadio;
    private JLabel formatPreviewLabel;

    private boolean canSaveDateTime = false;
    private boolean canSaveFormat = true;

    private boolean canSave() {
        return canSaveDateTime && canSaveFormat;
    }

    private static final String[] BUILTIN_CURRENCIES = PluginPersistentStateComponent.BUILTIN_CURRENCIES;

    public SettingsForm() {
        dateTimeHelpLink.setListener((aSource, aLinkData) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, null);

        dateTimeTextField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                canSaveDateTime = true;
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat(dateTimeTextField.getText());
                    var date = new java.util.Date(1136214245000L);
                    if (dateTimeUTCCheckBox.isSelected()) {
                        formatter.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                    }
                    dateTimeTestLabel.setText(formatter.format(date) + " " +
                            (dateTimeUTCCheckBox.isSelected() ? "(UTC)" : "(Local)")
                    );
                } catch (Exception ex) {
                    dateTimeTestLabel.setText("Invalid format");
                    canSaveDateTime = false;
                }
            }
        });

        surroundEnableCheckBox.addChangeListener(e -> {
            surroundLeftTextField.setEnabled(surroundEnableCheckBox.isSelected());
            surroundRightTextField.setEnabled(surroundEnableCheckBox.isSelected());
        });

        dateTimeUTCCheckBox.addChangeListener(e -> dateTimeTextField.setText(dateTimeTextField.getText()));

        // Number format section
        formatGroupSizeSpinner.setModel(new SpinnerNumberModel(3, 1, 20, 1));
        formatDecimalPlacesSpinner.setModel(new SpinnerNumberModel(2, 0, 10, 1));
        formatDecimalPlacesSpinner.setEnabled(false);

        formatDecimalCheckBox.addChangeListener(e -> {
            formatDecimalPlacesSpinner.setEnabled(formatDecimalCheckBox.isSelected());
            updateFormatPreview();
        });

        ChangeListener formatPreviewListener = e -> updateFormatPreview();
        formatGroupSizeSpinner.addChangeListener(formatPreviewListener);
        formatDecimalPlacesSpinner.addChangeListener(formatPreviewListener);
        formatCurrencyPrefixRadio.addChangeListener(formatPreviewListener);
        formatCurrencySuffixRadio.addChangeListener(formatPreviewListener);

        DocumentAdapter formatDocListener = new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent documentEvent) {
                updateFormatPreview();
            }
        };
        formatDelimiterTextField.getDocument().addDocumentListener(formatDocListener);
        ((AbstractDocument) formatDelimiterTextField.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(1));

        for (String s : BUILTIN_CURRENCIES) {
            formatCurrencySymbolComboBox.addItem(s);
        }

        formatCurrencySymbolComboBox.addActionListener(e -> updateFormatPreview());
        JTextField comboEditor = (JTextField) formatCurrencySymbolComboBox.getEditor().getEditorComponent();
        comboEditor.getDocument().addDocumentListener(formatDocListener);
        ((AbstractDocument) comboEditor.getDocument()).setDocumentFilter(new MaxLengthDocumentFilter(5));

        java.awt.event.MouseAdapter currencyContextMenu = new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) showCurrencyContextMenu(e);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (e.isPopupTrigger()) showCurrencyContextMenu(e);
            }
        };
        formatCurrencySymbolComboBox.addMouseListener(currencyContextMenu);
        comboEditor.addMouseListener(currencyContextMenu);
    }

    private void showCurrencyContextMenu(java.awt.event.MouseEvent e) {
        String symbol = getCurrencySymbol();
        if (symbol.isEmpty()) return;

        java.util.Set<String> builtins = new java.util.HashSet<>(java.util.Arrays.asList(BUILTIN_CURRENCIES));
        JPopupMenu menu = new JPopupMenu();

        if (lacksItem(symbol)) {
            JMenuItem addItem = new JMenuItem("Add \"" + symbol + "\"");
            addItem.addActionListener(ev -> {
                formatCurrencySymbolComboBox.addItem(symbol);
                formatCurrencySymbolComboBox.setSelectedItem(symbol);
            });
            menu.add(addItem);
        } else if (!builtins.contains(symbol)) {
            JMenuItem removeItem = new JMenuItem("Remove \"" + symbol + "\"");
            removeItem.addActionListener(ev -> {
                formatCurrencySymbolComboBox.removeItem(symbol);
                formatCurrencySymbolComboBox.setSelectedIndex(0);
            });
            menu.add(removeItem);
        } else {
            return;
        }

        menu.show((java.awt.Component) e.getSource(), e.getX(), e.getY());
    }

    private boolean lacksItem(String symbol) {
        for (int i = 0; i < formatCurrencySymbolComboBox.getItemCount(); i++) {
            if (formatCurrencySymbolComboBox.getItemAt(i).equals(symbol)) return false;
        }
        return true;
    }

    private java.util.List<String> getCustomCurrencies() {
        java.util.List<String> customs = new java.util.ArrayList<>();
        java.util.Set<String> builtins = new java.util.HashSet<>(java.util.Arrays.asList(BUILTIN_CURRENCIES));
        for (int i = 0; i < formatCurrencySymbolComboBox.getItemCount(); i++) {
            String item = formatCurrencySymbolComboBox.getItemAt(i);
            if (!builtins.contains(item)) customs.add(item);
        }
        return customs;
    }

    private String getCurrencySymbol() {
        Object item = formatCurrencySymbolComboBox.getEditor().getItem();
        return item == null ? "" : item.toString().trim();
    }

    private void setCurrencySymbol(String symbol) {
        for (int i = 0; i < formatCurrencySymbolComboBox.getItemCount(); i++) {
            if (formatCurrencySymbolComboBox.getItemAt(i).equals(symbol)) {
                formatCurrencySymbolComboBox.setSelectedIndex(i);
                return;
            }
        }

        if (!symbol.isEmpty()) {
            formatCurrencySymbolComboBox.addItem(symbol);
        }
        formatCurrencySymbolComboBox.setSelectedItem(symbol);
    }

    private void updateFormatPreview() {
        try {
            String delimiter = formatDelimiterTextField.getText();
            int groupSize = (Integer) formatGroupSizeSpinner.getValue();
            boolean enableDecimals = formatDecimalCheckBox.isSelected();
            int decimalPlaces = (Integer) formatDecimalPlacesSpinner.getValue();
            String currencySymbol = getCurrencySymbol();
            boolean currencyPrefix = formatCurrencyPrefixRadio.isSelected();

            for (char c : currencySymbol.toCharArray()) {
                if (Character.isDigit(c)) {
                    canSaveFormat = false;
                    formatPreviewLabel.setText("Currency symbol must not contain digits");
                    return;
                }
                if (Character.isWhitespace(c)) {
                    canSaveFormat = false;
                    formatPreviewLabel.setText("Currency symbol must not contain whitespace");
                    return;
                }
            }

            canSaveFormat = true;

            String warning = "";
            if (!delimiter.isEmpty()) {
                char ch = delimiter.charAt(0);
                if (Character.isDigit(ch) || ch == '-') {
                    warning = " (warning: delimiter may be confused with number characters)";
                }
            }

            String sample = "1234567890";
            StringBuilder grouped = new StringBuilder();
            int len = sample.length();
            int firstGroup = len % groupSize;
            if (firstGroup > 0) grouped.append(sample, 0, firstGroup);
            for (int i = firstGroup; i < len; i += groupSize) {
                if (!grouped.isEmpty()) grouped.append(delimiter);
                grouped.append(sample, i, Math.min(i + groupSize, len));
            }

            StringBuilder preview = new StringBuilder();
            if (currencyPrefix && !currencySymbol.isEmpty()) preview.append(currencySymbol);
            preview.append(grouped);
            if (enableDecimals && decimalPlaces > 0) {
                String decSep = delimiter.equals(".") ? "," : ".";
                preview.append(decSep);
                preview.append("0".repeat(decimalPlaces));
            }
            if (!currencyPrefix && !currencySymbol.isEmpty()) preview.append(currencySymbol);

            formatPreviewLabel.setText("Preview: " + preview + warning);
        } catch (Exception ex) {
            canSaveFormat = false;
            formatPreviewLabel.setText("Preview: (invalid settings)");
        }
    }

    public JPanel getRoot() {
        return root;
    }

    public void getData(PluginPersistentStateComponent data) {
        data.setDateTimeFormat(dateTimeTextField.getText());
        data.setDateTimeUTC(dateTimeUTCCheckBox.isSelected());
        data.setDateTimeMilliseconds(dateTimeMillisecondsCheckBox.isSelected());
        data.setSurroundEnable(surroundEnableCheckBox.isSelected());
        data.setSurroundLeft(surroundLeftTextField.getText());
        data.setSurroundRight(surroundRightTextField.getText());
        data.setUpperCase(upperCaseRadioButton.isSelected());
        data.setFormatDelimiter(formatDelimiterTextField.getText());
        data.setFormatGroupSize((Integer) formatGroupSizeSpinner.getValue());
        data.setFormatDecimalEnabled(formatDecimalCheckBox.isSelected());
        data.setFormatDecimalPlaces((Integer) formatDecimalPlacesSpinner.getValue());
        data.setFormatCurrencySymbol(getCurrencySymbol());
        data.setFormatCurrencyPrefix(formatCurrencyPrefixRadio.isSelected());
        data.setFormatCustomCurrencies(getCustomCurrencies());
    }

    public void setData(PluginPersistentStateComponent data) {
        dateTimeTextField.setText(data.getDateTimeFormat());
        dateTimeUTCCheckBox.setSelected(data.isDateTimeUTC());
        dateTimeMillisecondsCheckBox.setSelected(data.isDateTimeMilliseconds());
        surroundEnableCheckBox.setSelected(data.isSurroundEnable());
        surroundLeftTextField.setText(data.getSurroundLeft());
        surroundRightTextField.setText(data.getSurroundRight());
        lowerCaseRadioButton.setSelected(!data.isUpperCase());
        upperCaseRadioButton.setSelected(data.isUpperCase());
        formatDelimiterTextField.setText(data.getFormatDelimiter());
        formatGroupSizeSpinner.setValue(data.getFormatGroupSize());
        formatDecimalCheckBox.setSelected(data.isFormatDecimalEnabled());
        formatDecimalPlacesSpinner.setValue(data.getFormatDecimalPlaces());

        java.util.Set<String> builtins = new java.util.HashSet<>(java.util.Arrays.asList(BUILTIN_CURRENCIES));
        for (int i = formatCurrencySymbolComboBox.getItemCount() - 1; i >= 0; i--) {
            if (!builtins.contains(formatCurrencySymbolComboBox.getItemAt(i))) {
                formatCurrencySymbolComboBox.removeItemAt(i);
            }
        }
        for (String custom : data.getFormatCustomCurrencies()) {
            if (lacksItem(custom)) formatCurrencySymbolComboBox.addItem(custom);
        }
        setCurrencySymbol(data.getFormatCurrencySymbol());
        formatCurrencyPrefixRadio.setSelected(data.isFormatCurrencyPrefix());
        formatCurrencySuffixRadio.setSelected(!data.isFormatCurrencyPrefix());
    }

    private static class MaxLengthDocumentFilter extends DocumentFilter {
        private final int maxLength;

        MaxLengthDocumentFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (fb.getDocument().getLength() + string.length() <= maxLength) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (fb.getDocument().getLength() - length + text.length() <= maxLength) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public boolean isModified(PluginPersistentStateComponent data) {
        if (!canSave()) {
            return false;
        }

        return !dateTimeTextField.getText().equals(data.getDateTimeFormat()) ||
                dateTimeUTCCheckBox.isSelected() != data.isDateTimeUTC() ||
                dateTimeMillisecondsCheckBox.isSelected() != data.isDateTimeMilliseconds() ||
                surroundEnableCheckBox.isSelected() != data.isSurroundEnable() ||
                !surroundLeftTextField.getText().equals(data.getSurroundLeft()) ||
                !surroundRightTextField.getText().equals(data.getSurroundRight()) ||
                upperCaseRadioButton.isSelected() != data.isUpperCase() ||
                !formatDelimiterTextField.getText().equals(data.getFormatDelimiter()) ||
                !formatGroupSizeSpinner.getValue().equals(data.getFormatGroupSize()) ||
                formatDecimalCheckBox.isSelected() != data.isFormatDecimalEnabled() ||
                !formatDecimalPlacesSpinner.getValue().equals(data.getFormatDecimalPlaces()) ||
                !getCurrencySymbol().equals(data.getFormatCurrencySymbol()) ||
                formatCurrencyPrefixRadio.isSelected() != data.isFormatCurrencyPrefix() ||
                !getCustomCurrencies().equals(data.getFormatCustomCurrencies());
    }
}
