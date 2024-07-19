package com.dethlex.numberconverter;

// ConvertTypes - possible conversion types
public enum ConvertTypes {
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
    DATETIME {
        @Override
        public String toString() {
            return "DATE";
        }
    },
}

