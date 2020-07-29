package com.dethlex.numberconverter;

import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConvertActionGroup extends ActionGroup {

	@NotNull
	@Override
	public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
		return new AnAction[]{
			new ConvertAction(ConvertType.DEC),
			new ConvertAction(ConvertType.HEX),
			new ConvertAction(ConvertType.OCT),
			new ConvertAction(ConvertType.BIN),
		};
	}
}
