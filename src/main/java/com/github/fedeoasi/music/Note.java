package com.github.fedeoasi.music;

import java.util.Arrays;

public enum Note {
    A(NaturalNote.A, Accidental.None),
    B(NaturalNote.B, Accidental.None),
    C(NaturalNote.C, Accidental.None),
    D(NaturalNote.D, Accidental.None),
    E(NaturalNote.E, Accidental.None),
    F(NaturalNote.F, Accidental.None),
    G(NaturalNote.G, Accidental.None),

    ASharp(NaturalNote.A, Accidental.Sharp),
    BSharp(NaturalNote.B, Accidental.Sharp),
    CSharp(NaturalNote.C, Accidental.Sharp),
    DSharp(NaturalNote.D, Accidental.Sharp),
    ESharp(NaturalNote.E, Accidental.Sharp),
    FSharp(NaturalNote.F, Accidental.Sharp),
    GSharp(NaturalNote.G, Accidental.Sharp),

    AFlat(NaturalNote.A, Accidental.Flat),
    BFlat(NaturalNote.B, Accidental.Flat),
    CFlat(NaturalNote.C, Accidental.Flat),
    DFlat(NaturalNote.D, Accidental.Flat),
    EFlat(NaturalNote.E, Accidental.Flat),
    FFlat(NaturalNote.F, Accidental.Flat),
    GFlat(NaturalNote.G, Accidental.Flat),

    ADoubleSharp(NaturalNote.A, Accidental.DoubleSharp),
    BDoubleSharp(NaturalNote.B, Accidental.DoubleSharp),
    CDoubleSharp(NaturalNote.C, Accidental.DoubleSharp),
    DDoubleSharp(NaturalNote.D, Accidental.DoubleSharp),
    EDoubleSharp(NaturalNote.E, Accidental.DoubleSharp),
    FDoubleSharp(NaturalNote.F, Accidental.DoubleSharp),
    GDoubleSharp(NaturalNote.G, Accidental.DoubleSharp),


    ADoubleFlat(NaturalNote.A, Accidental.DoubleFlat),
    BDoubleFlat(NaturalNote.B, Accidental.DoubleFlat),
    CDoubleFlat(NaturalNote.C, Accidental.DoubleFlat),
    DDoubleFlat(NaturalNote.D, Accidental.DoubleFlat),
    EDoubleFlat(NaturalNote.E, Accidental.DoubleFlat),
    FDoubleFlat(NaturalNote.F, Accidental.DoubleFlat),
    GDoubleFlat(NaturalNote.G, Accidental.DoubleFlat);

    private NaturalNote naturalNote;
    private Accidental accidental;

    Note(NaturalNote naturalNote, Accidental accidental) {
        this.naturalNote = naturalNote;
        this.accidental = accidental;
    }

    public NaturalNote getNaturalNote() {
        return naturalNote;
    }

    public Note getNaturalNoteAsNote() { return Note.fromName(naturalNote.toString()); }

    public Accidental getAccidental() {
        return accidental;
    }

    public String getName() {
        return getNaturalNote().toString() + getAccidental().getSymbol();
    }

    public static Note fromName(String name) {
        return Arrays.stream(values()).filter(n -> n.getName().equals(name)).findFirst().get();
    }

    public boolean isNatural() {
        return accidental.equals(Accidental.None);
    }

    @Override
    public String toString() {
        return getName();
    }
}