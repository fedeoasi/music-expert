package com.github.fedeoasi.music;

public enum Accidental {
    None(""), Flat("b"), Sharp("#"), DoubleFlat("bb"), DoubleSharp("##");

    private String symbol;

    Accidental(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
