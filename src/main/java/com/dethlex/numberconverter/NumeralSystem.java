package com.dethlex.numberconverter;

public enum NumeralSystem {
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