package com.github.fedeoasi.music;

import java.util.HashMap;
import java.util.Map;

public class Notes {
    public Note[] naturalNotes = {Note.A, Note.B, Note.C, Note.D, Note.E, Note.F, Note.G};
    public Note[] sharpNotes = {Note.A, Note.ASharp, Note.B, Note.BSharp, Note.CSharp, Note.D, Note.DSharp, Note.E, Note.ESharp, Note.FSharp, Note.G, Note.GSharp};
    public Note[] flatNotes = {Note.A, Note.BFlat, Note.CFlat, Note.C, Note.DFlat, Note.D, Note.EFlat, Note.FFlat, Note.F, Note.GFlat, Note.G, Note.AFlat};
    public Note[] doubleSharpNotes = {Note.GDoubleSharp, Note.ASharp, Note.ADoubleSharp, Note.BSharp, Note.CSharp, Note.CDoubleSharp, Note.DSharp, Note.DDoubleSharp,
        Note.ESharp, Note.FSharp, Note.FDoubleSharp, Note.GSharp};
    public Note[] doubleFlatNotes = {Note.BDoubleFlat, Note.BFlat, Note.CFlat, Note.DDoubleFlat, Note.DFlat, Note.EDoubleFlat, Note.EFlat, Note.FFlat,
        Note.GDoubleFlat, Note.GFlat, Note.ADoubleFlat, Note.AFlat};

    private Map<Note, Integer> indexByNote;

    public boolean isNatural(Note nota) {
        for (int i = 0; i < naturalNotes.length; i++)
            if (naturalNotes[i].equals(nota))
                return true;
        return false;
    }

    public Note nextNatural(Note nota) {
        for (int i = 0; i < naturalNotes.length; i++) {
            if (naturalNotes[i].equals(nota.getNaturalNoteAsNote())) {
                return naturalNotes[(i + 1) % 7];
            }
        }
        throw new RuntimeException("Note not found: " + nota);
    }

    public int indexOf(Note nota, Note[] note) {
        for (int i = 0; i < note.length; i++)
            if (note[i].equals(nota))
                return i;
        return -1;
    }

    public int distance(Note from, Note to) {
        int n1 = getIndex(from);
        int n2 = getIndex(to);
        int diff = n2 - n1;
        if (diff >= 0) {
            return diff;
        }
        return 12 + diff;
    }

    public int[] distances(Note[] notes) {
        int[] distances = new int[notes.length + 1];
        for (int i = 1; i <= notes.length; i++) {
            distances[i] = distance(notes[i - 1], notes[i % notes.length]);
        }
        return distances;
    }

    public int getIndexInNaturalScale(Note nota) {
        int indice = indexOf(nota, naturalNotes);
        if (indice == -1) {
            System.out.println("bu!Errore nota " + nota);
            return -1;
        }
        return indice;
    }

    public int getIndex(Note note) {
        ensureIndexByNote();
        Integer index = indexByNote.get(note);
        if(index == null) {
            index = -1;
        }
        return index;
    }

    public Note flatNotes(int i) {
        if (i < 12) return flatNotes[i];
        else return flatNotes[i % 12];
    }

    public Note sharpNotes(int i) {
        if (i < 12) return sharpNotes[i];
        else return sharpNotes[i % 12];
    }

    public Note doubleFlatNotes(int i) {
        if (i < 12) return doubleFlatNotes[i];
        else return doubleFlatNotes[i % 12];
    }

    public Note doubleSharpNotes(int i) {
        if (i < 12) return doubleSharpNotes[i];
        else return doubleSharpNotes[i % 12];
    }

    public static void main(String[] args) {
        Notes n = new Notes();
        Scales s = new Scales();

        System.out.println("Giro delle Quinte");
        s.printScala(s.scalaMaggiore(Note.C));
        s.printScala(s.scalaMaggiore(Note.G));
        s.printScala(s.scalaMaggiore(Note.D));
        s.printScala(s.scalaMaggiore(Note.A));
        s.printScala(s.scalaMaggiore(Note.E));
        s.printScala(s.scalaMaggiore(Note.B));
        s.printScala(s.scalaMaggiore(Note.FSharp));
        s.printScala(s.scalaMaggiore(Note.CSharp));

        System.out.println("Giro delle quarte");
        s.printScala(s.scalaMaggiore(Note.C));
        s.printScala(s.scalaMaggiore(Note.F));
        s.printScala(s.scalaMaggiore(Note.BFlat));
        s.printScala(s.scalaMaggiore(Note.EFlat));
        s.printScala(s.scalaMaggiore(Note.AFlat));
        s.printScala(s.scalaMaggiore(Note.DFlat));
        s.printScala(s.scalaMaggiore(Note.GFlat));
        s.printScala(s.scalaMaggiore(Note.CFlat));

        System.out.println("Scale minori di la");
        s.printScala(s.scalaMinNat(Note.A));
        s.printScala(s.scalaMinArm(Note.A));
        s.printScala(s.scalaMinMel(Note.A));

        System.out.println("Scale minori di sol");
        s.printScala(s.scalaMinNat(Note.G));
        s.printScala(s.scalaMinArm(Note.G));
        s.printScala(s.scalaMinMel(Note.G));

        System.out.println("Scale minori di re");
        s.printScala(s.scalaMinNat(Note.D));
        s.printScala(s.scalaMinArm(Note.D));
        s.printScala(s.scalaMinMel(Note.D));
    }

    public int ottava(Note note, int altezza) {
        Accidental accidental = note.getAccidental();
        if (isNatural(note))
            return ((altezza - 45) / 12);
        if (accidental.equals(Accidental.Flat))
            return ((altezza - 44) / 12);
        if (accidental.equals(Accidental.Sharp))
            return ((altezza - 46) / 12);
        if (accidental.equals(Accidental.DoubleFlat))
            return ((altezza - 43) / 12);
        if (accidental.equals(Accidental.DoubleSharp))
            return ((altezza - 47) / 12);
        return 0;
    }

    private void ensureIndexByNote() {
        if(indexByNote == null) {
            indexByNote = new HashMap<>();
            indexNotes(flatNotes);
            indexNotes(sharpNotes);
            indexNotes(doubleFlatNotes);
            indexNotes(doubleSharpNotes);
        }
    }

    private void indexNotes(Note[] notes) {
        for (int i = 0; i < notes.length; i++) {
            indexByNote.put(notes[i], i);
        }
    }
}