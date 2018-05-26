package com.github.fedeoasi.music;

import java.util.HashMap;
import java.util.Map;

public class Notes {
    public static Note[] naturalNotes = {Note.A, Note.B, Note.C, Note.D, Note.E, Note.F, Note.G};
    public static Note[] sharpNotes = {Note.A, Note.ASharp, Note.B, Note.BSharp, Note.CSharp, Note.D, Note.DSharp, Note.E, Note.ESharp, Note.FSharp, Note.G, Note.GSharp};
    public static Note[] flatNotes = {Note.A, Note.BFlat, Note.CFlat, Note.C, Note.DFlat, Note.D, Note.EFlat, Note.FFlat, Note.F, Note.GFlat, Note.G, Note.AFlat};
    public static Note[] doubleSharpNotes = {Note.GDoubleSharp, Note.ASharp, Note.ADoubleSharp, Note.BSharp, Note.CSharp, Note.CDoubleSharp, Note.DSharp, Note.DDoubleSharp,
        Note.ESharp, Note.FSharp, Note.FDoubleSharp, Note.GSharp};
    public static Note[] doubleFlatNotes = {Note.BDoubleFlat, Note.BFlat, Note.CFlat, Note.DDoubleFlat, Note.DFlat, Note.EDoubleFlat, Note.EFlat, Note.FFlat,
        Note.GDoubleFlat, Note.GFlat, Note.ADoubleFlat, Note.AFlat};

    private static Map<Note, Integer> indexByNote;

    public static Note nextNatural(Note nota) {
        for (int i = 0; i < naturalNotes.length; i++) {
            if (naturalNotes[i].equals(nota.getNaturalNoteAsNote())) {
                return naturalNotes[(i + 1) % 7];
            }
        }
        throw new RuntimeException("Note not found: " + nota);
    }

    public static int indexOf(Note nota, Note[] note) {
        for (int i = 0; i < note.length; i++)
            if (note[i].equals(nota))
                return i;
        return -1;
    }

    public static int distance(Note from, Note to) {
        int n1 = getIndex(from);
        int n2 = getIndex(to);
        int diff = n2 - n1;
        if (diff >= 0) {
            return diff;
        }
        return 12 + diff;
    }

    public static int[] distances(Note[] notes) {
        int[] distances = new int[notes.length + 1];
        for (int i = 1; i <= notes.length; i++) {
            distances[i] = distance(notes[i - 1], notes[i % notes.length]);
        }
        return distances;
    }

    public static int getIndexInNaturalScale(Note nota) {
        int indice = indexOf(nota, naturalNotes);
        if (indice == -1) {
            System.out.println("bu!Errore nota " + nota);
            return -1;
        }
        return indice;
    }

    public static int getIndex(Note note) {
        ensureIndexByNote();
        Integer index = indexByNote.get(note);
        if(index == null) {
            index = -1;
        }
        return index;
    }

    public static Note flatNotes(int i) {
        if (i < 12) return flatNotes[i];
        else return flatNotes[i % 12];
    }

    public static Note sharpNotes(int i) {
        if (i < 12) return sharpNotes[i];
        else return sharpNotes[i % 12];
    }

    public static Note doubleFlatNotes(int i) {
        if (i < 12) return doubleFlatNotes[i];
        else return doubleFlatNotes[i % 12];
    }

    public static Note doubleSharpNotes(int i) {
        if (i < 12) return doubleSharpNotes[i];
        else return doubleSharpNotes[i % 12];
    }

    public static void main(String[] args) {
        System.out.println("Giro delle Quinte");
        Scales.printScala(Scales.scalaMaggiore(Note.C));
        Scales.printScala(Scales.scalaMaggiore(Note.G));
        Scales.printScala(Scales.scalaMaggiore(Note.D));
        Scales.printScala(Scales.scalaMaggiore(Note.A));
        Scales.printScala(Scales.scalaMaggiore(Note.E));
        Scales.printScala(Scales.scalaMaggiore(Note.B));
        Scales.printScala(Scales.scalaMaggiore(Note.FSharp));
        Scales.printScala(Scales.scalaMaggiore(Note.CSharp));

        System.out.println("Giro delle quarte");
        Scales.printScala(Scales.scalaMaggiore(Note.C));
        Scales.printScala(Scales.scalaMaggiore(Note.F));
        Scales.printScala(Scales.scalaMaggiore(Note.BFlat));
        Scales.printScala(Scales.scalaMaggiore(Note.EFlat));
        Scales.printScala(Scales.scalaMaggiore(Note.AFlat));
        Scales.printScala(Scales.scalaMaggiore(Note.DFlat));
        Scales.printScala(Scales.scalaMaggiore(Note.GFlat));
        Scales.printScala(Scales.scalaMaggiore(Note.CFlat));

        System.out.println("Scale minori di la");
        Scales.printScala(Scales.scalaMinNat(Note.A));
        Scales.printScala(Scales.scalaMinArm(Note.A));
        Scales.printScala(Scales.scalaMinMel(Note.A));

        System.out.println("Scale minori di sol");
        Scales.printScala(Scales.scalaMinNat(Note.G));
        Scales.printScala(Scales.scalaMinArm(Note.G));
        Scales.printScala(Scales.scalaMinMel(Note.G));

        System.out.println("Scale minori di re");
        Scales.printScala(Scales.scalaMinNat(Note.D));
        Scales.printScala(Scales.scalaMinArm(Note.D));
        Scales.printScala(Scales.scalaMinMel(Note.D));
    }

    public static int ottava(Note note, int altezza) {
        Accidental accidental = note.getAccidental();
        if (note.isNatural())
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

    private static void ensureIndexByNote() {
        if(indexByNote == null) {
            indexByNote = new HashMap<>();
            indexNotes(flatNotes);
            indexNotes(sharpNotes);
            indexNotes(doubleFlatNotes);
            indexNotes(doubleSharpNotes);
        }
    }

    private static void indexNotes(Note[] notes) {
        for (int i = 0; i < notes.length; i++) {
            indexByNote.put(notes[i], i);
        }
    }
}