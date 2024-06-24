package com.dethlex.numberconverter;

import com.intellij.openapi.actionSystem.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConvertActionGroup extends ActionGroup {

    private final AnAction[] convertActions;

    public ConvertActionGroup() {
        convertActions = new AnAction[]{
                new Dec(),
                new Hex(),
                new Oct(),
                new Bin(),
        };
    }

    public static class Dec extends ConvertAction {
        public Dec() {
            super(NumeralSystem.DEC);
        }
    }

    public static class Hex extends ConvertAction {
        public Hex() {
            super(NumeralSystem.HEX);
        }
    }

    public static class Oct extends ConvertAction {
        public Oct() {
            super(NumeralSystem.OCT);
        }
    }

    public static class Bin extends ConvertAction {
        public Bin() {
            super(NumeralSystem.BIN);
        }
    }

    @NotNull
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        return convertActions;
    }
}
