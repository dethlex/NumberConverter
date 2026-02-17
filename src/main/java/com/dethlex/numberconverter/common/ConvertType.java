package com.dethlex.numberconverter.common;

// ConvertTypes - possible conversion types
public enum ConvertType {
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
    FORMAT {
        @Override
        public String toString() {
            return "FORMAT";
        }
    },
}

