package com.github.fedeoasi.music;

import java.util.Arrays;

public enum ChordType {
    Major("", true, false, false), MajorSeventh("M7", true, false, false), MajorNinth("M9", true, false, false),
    MajorThirteenth("M13", true, false, false),

    m("m", false, true, false), m7("m7", false, true, false), m9("m9", false, true, false), m6("m6", false, true, false),
    m13("m13", false, true, false), m7Flat5("m7b5", false, true, false), m9Flat5("m9b5", false, true, false),

    Seventh("7", true, false, true), Ninth("9", true, false, true), Thirteenth("13", true, false, true), SeventhSharp5("7#5", true, false, true),
    SeventhFlat9("7b9", true, false, true), SeventhFlat5("7b5", false, false, true);

    private String symbol;
    private boolean major;
    private boolean minor;
    private boolean dominant;

    ChordType(String symbol, boolean major, boolean minor, boolean dominant) {
        this.symbol = symbol;
        this.major = major;
        this.minor = minor;
        this.dominant = dominant;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isMajor() {
        return major;
    }

    public boolean isMinor() {
        return minor;
    }

    public boolean isDominant() {
        return dominant;
    }

    public boolean isSemiDiminished() {
        return this == m7Flat5 || this == m9Flat5;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static ChordType fromName(String name) {
        return Arrays.stream(values()).filter(n -> n.getSymbol().equals(name)).findFirst().get();
    }
}
