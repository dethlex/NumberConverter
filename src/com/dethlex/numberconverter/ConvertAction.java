package com.dethlex.numberconverter;

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

enum ConvertType {
	HEX {
		@Override
		public String toString() {
			return "HEX";
		}
	},
	BIN {
		@Override
		public String toString() {
			return "BIN";
		}
	},
	OCT {
		@Override
		public String toString() {
			return "OCT";
		}
	},
	DEC {
		@Override
		public String toString() {
			return "DEC";
		}
	},
}

public class ConvertAction extends AnAction {

	private final ConvertType type_;

	private final static String ERR_UT = "unknown type";
	private final static String ERR_CC = "can't convert";

	public ConvertAction(ConvertType type) {
		super();
		type_ = type;
	}

	private String ConvertNumber(String value) {
		value = value.strip();
		value = value.replaceAll("_", "");
		int number;
		boolean negative = value.startsWith("-");

		if (negative)
			value = value.substring(1);

		try {
			if (value.startsWith("0b")) {
				value = value.substring(2);
				number = Integer.parseInt(value, 2);
			} else
				number = Integer.decode(value);

			switch (type_) {
				case DEC:
					value = Integer.toString(number);
					break;
				case HEX:
					value = "0x" + Integer.toHexString(number).toUpperCase();
					break;
				case OCT:
					value = "0" + Integer.toOctalString(number);
					break;
				case BIN:
					value = "0b" + Integer.toBinaryString(number);
					break;
				default:
					value = ERR_UT;
			}
		} catch (NumberFormatException e) {
			return ERR_CC;
		}
		return negative ? "-" + value : value;
	}

	private Pair<Integer,String> ConvertAll(@NotNull List<Caret> caretList) {
		int count = 0;
		String value = "";

		for (Caret caret : caretList){
			value = ConvertNumber(caret.getSelectedText());
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
	public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
		final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		List<Caret> caretList = FilterCaretWithSelection(caretModel.getAllCarets());

		for (Caret caret : caretList) {
			final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);
			final Document document = editor.getDocument();

			int selectionStart = caret.getSelectionStart();
			int selectionEnd = caret.getSelectionEnd();

			CharSequence convertedNumber = ConvertNumber(caret.getSelectedText());
			WriteCommandAction.runWriteCommandAction(project, () ->
					document.replaceString(selectionStart, selectionEnd, convertedNumber)
			);

			caret.removeSelection();
			caret.moveToOffset(selectionStart);
		}
	}

	@Override
	public void update(@NotNull AnActionEvent anActionEvent) {
		final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
		List<Caret> caretList = FilterCaretWithSelection(editor.getCaretModel().getAllCarets());

		anActionEvent.getPresentation().setEnabledAndVisible(!caretList.isEmpty());
		if (!anActionEvent.getPresentation().isVisible())
			return;

		Pair<Integer,String> p = ConvertAll(caretList);
		String text = type_.toString();
		if (p.first > 1) {
			text += " ("+ p.first +")";
		} else {
			text += ": " + p.second;
		}

		anActionEvent.getPresentation().setText(text);
		anActionEvent.getPresentation().setEnabled(p.first > 0);
	}
}
