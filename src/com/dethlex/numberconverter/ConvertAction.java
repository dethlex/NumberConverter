package com.dethlex.numberconverter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

enum ConvertType {
	HEX{
		@Override
		public String toString() {
			return "HEX";
		}
	},
	BIN{
		@Override
		public String toString() {
			return "BIN";
		}
	},
	OCT{
		@Override
		public String toString() {
			return "OCT";
		}
	},
	DEC{
		@Override
		public String toString() {
			return "DEC";
		}
	},
}

public class ConvertAction extends AnAction {

	private final ConvertType type_;

	private final static String ERR_UT = "Unknown type";
	private final static String ERR_CC = "Can't convert";

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
				case DEC: value = Integer.toString(number); break;
				case HEX: value = "0x"+Integer.toHexString(number).toUpperCase(); break;
				case OCT: value = "0"+Integer.toOctalString(number); break;
				case BIN: value = "0b"+Integer.toBinaryString(number); break;
				default: value = ERR_UT;
			}
		} catch (NumberFormatException e) {
			return ERR_CC;
		}
		return negative ? "-"+value : value;
	}

	@Override
	public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
		final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		Caret currentCaret = caretModel.getCurrentCaret();

		if (currentCaret.hasSelection()) {
			final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);
			final Document document = editor.getDocument();

			Caret primaryCaret = caretModel.getPrimaryCaret();
			int selectionStart = primaryCaret.getSelectionStart();
			int selectionEnd = primaryCaret.getSelectionEnd();

			CharSequence convertedNumber = ConvertNumber(currentCaret.getSelectedText());
			WriteCommandAction.runWriteCommandAction(project, () ->
				document.replaceString(selectionStart, selectionEnd, convertedNumber)
			);

			primaryCaret.removeSelection();
		}
	}

	@Override
	public void update(AnActionEvent anActionEvent)
	{
		final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
		Caret currentCaret = editor.getCaretModel().getCurrentCaret();

		anActionEvent.getPresentation().setEnabledAndVisible(currentCaret.hasSelection());

		if (anActionEvent.getPresentation().isVisible()) {
			String value = ConvertNumber(currentCaret.getSelectedText());
			anActionEvent.getPresentation().setText(type_.toString()+": "+value);
			anActionEvent.getPresentation().setEnabled(!value.equals(ERR_CC));
		}
	}
}
