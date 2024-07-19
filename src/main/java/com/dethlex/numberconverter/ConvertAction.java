package com.dethlex.numberconverter;

import com.dethlex.numberconverter.common.IConverter;
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

import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertAction extends AnAction {

    private final ConvertTypes type;

    private final static String ERR_CC = "can't convert";
    private final static String ERR_DT = "wrong date format";

    public ConvertAction(ConvertTypes type) {
        super();
        this.type = type;
    }

    public String ConvertByType(String value) {
        IConverter converter;

        try {
            switch (type) {
                case DATETIME:
                    converter = new ConvertDate(value); break;
                default:
                    converter = new ConvertNumber(value); break;
            }
        } catch (NumberFormatException e) {
            return ERR_CC;
        } catch (DateTimeException e) {
            return ERR_DT;
        }

        return converter.toString(type);
    }

    private Pair<Integer, String> ConvertAll(@NotNull List<Caret> caretList) {
        int count = 0;
        String value = "";

        for (Caret caret : caretList) {
            value = ConvertByType(caret.getSelectedText());
            if (value.equals(ERR_CC)) {
                return new Pair<>(0, ERR_CC);
            }
            count++;
        }

        return new Pair<>(count, value);
    }

    private List<Caret> FilterCaretWithSelection(@NotNull List<Caret> caretList) {
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
        List<Caret> caretList = FilterCaretWithSelection(caretModel.getAllCarets());

        WriteCommandAction.runWriteCommandAction(project, () ->
                caretList.forEach(caret -> {

                    int selectionStart = caret.getSelectionStart();
                    int selectionEnd = caret.getSelectionEnd();
                    CharSequence convertedNumber = ConvertByType(caret.getSelectedText());

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
        List<Caret> caretList = FilterCaretWithSelection(editor.getCaretModel().getAllCarets());

        anActionEvent.getPresentation().setEnabledAndVisible(!caretList.isEmpty());
        if (!anActionEvent.getPresentation().isVisible())
            return;

        Pair<Integer, String> p = ConvertAll(caretList);
        String text = type.toString();
        if (p.first > 1) {
            text += " (" + p.first + ")";
        } else {
            text += ": " + p.second;
        }

        anActionEvent.getPresentation().setText(text);
        anActionEvent.getPresentation().setEnabled(p.first > 0);
    }
}
