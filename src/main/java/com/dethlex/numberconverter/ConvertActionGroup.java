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
                new DateTime(),
        };
    }

    public static class Dec extends ConvertAction {
        public Dec() {
            super(ConvertTypes.DEC);
        }
    }

    public static class Hex extends ConvertAction {
        public Hex() {
            super(ConvertTypes.HEX);
        }
    }

    public static class Oct extends ConvertAction {
        public Oct() {
            super(ConvertTypes.OCT);
        }
    }

    public static class Bin extends ConvertAction {
        public Bin() {
            super(ConvertTypes.BIN);
        }
    }

    public static class DateTime extends ConvertAction {
        public DateTime() {
            super(ConvertTypes.DATETIME);
        }
    }

    @NotNull
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent anActionEvent) {
        return convertActions;
    }
}
