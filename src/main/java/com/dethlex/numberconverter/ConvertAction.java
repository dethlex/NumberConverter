package com.dethlex.numberconverter;

import com.dethlex.numberconverter.common.ConvertType;
import com.dethlex.numberconverter.common.IConverter;
import com.dethlex.numberconverter.config.PluginPersistentStateComponent;
import com.dethlex.numberconverter.date.ConvertDate;
import com.dethlex.numberconverter.number.ConvertNumber;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertAction extends AnAction {

    private final ConvertType type;

    private final static String ERR_CC = "can't convert";

    private final PluginPersistentStateComponent config;

    public ConvertAction(ConvertType type) {
        super();
        this.type = type;
        this.config = PluginPersistentStateComponent.getInstance();
    }

    public String convertByType(String value) {
        IConverter converter;

        try {
            switch (type) {
                case DATETIME:
                    converter = new ConvertDate(value);
                    break;
                default:
                    converter = new ConvertNumber(value);
                    break;
            }
        } catch (Exception e) {
            return ERR_CC;
        }

        return config.surroundText(converter.toString(type));
    }

    private Pair<Integer, String> convertAll(@NotNull List<Caret> caretList) {
        int count = 0;
        String firstValue = "";

        for (Caret caret : caretList) {
            String value = convertByType(caret.getSelectedText());
            if (value.equals(ERR_CC)) {
                return new Pair<>(0, ERR_CC);
            }

            if (count == 0) {
                firstValue = value;
            }

            count++;
        }

        return new Pair<>(count, firstValue);
    }

    private List<Caret> filterCaretWithSelection(@NotNull List<Caret> caretList) {
        return caretList.stream().filter(Caret::hasSelection).collect(Collectors.toList());
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();
        final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);

        CaretModel caretModel = editor.getCaretModel();
        List<Caret> caretList = filterCaretWithSelection(caretModel.getAllCarets());

        WriteCommandAction.runWriteCommandAction(project, () ->
                caretList.forEach(caret -> {

                    int selectionStart = caret.getSelectionStart();
                    int selectionEnd = caret.getSelectionEnd();
                    CharSequence convertedNumber = convertByType(caret.getSelectedText());

                    document.replaceString(selectionStart, selectionEnd, convertedNumber);

                    caret.removeSelection();
                    caret.moveToOffset(selectionStart);

                })
        );
    }

    @Override
    public void update(@NotNull AnActionEvent anActionEvent) {
        super.update(anActionEvent);

        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        List<Caret> caretList = filterCaretWithSelection(editor.getCaretModel().getAllCarets());

        anActionEvent.getPresentation().setEnabledAndVisible(!caretList.isEmpty());
        if (!anActionEvent.getPresentation().isVisible())
            return;

        Pair<Integer, String> p = convertAll(caretList);
        String text = type.toString();
        if (p.first > 1) {
            text += ": " + p.second + " (+" + (p.first - 1) + ")";
        } else {
            text += ": " + p.second;
        }

        anActionEvent.getPresentation().setText(text);
        anActionEvent.getPresentation().setEnabled(p.first > 0);
    }
}
