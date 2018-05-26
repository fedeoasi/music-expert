package com.github.fedeoasi.music;

public class Scales {
    public static int[] scalaMaggiore = {0, 2, 2, 1, 2, 2, 2, 1};
    public static int[] scalaMinNat = {0, 2, 1, 2, 2, 1, 2, 2};
    public static int[] scalaMinArm = {0, 2, 1, 2, 2, 1, 3, 1};
    public static int[] scalaMinMel = {0, 2, 1, 2, 2, 2, 2, 1};
    public static int[] pentaMaggiore = {0, 2, 2, 3, 2, 3};
    public static int[] pentaMinore = {0, 3, 2, 2, 3, 2};
    public static int[] esatonale = {0, 2, 2, 2, 2, 2};
    public static int[] tonoSemitono = {0, 2, 1, 2, 1, 2, 1, 2, 1};
    public static int[] semitonoTono = {0, 1, 2, 1, 2, 1, 2, 1, 2};

    public static Note[] scala(Note nota, int[] ts) {
        Note[] scala = new Note[7];
        scala[0] = nota;
        for (int i = 1; i < 7; i++) {
            Note nextNatural = Notes.nextNatural(scala[i - 1]);
            int distn = Notes.distance(scala[i - 1], nextNatural);
            if (distn == ts[i])
                scala[i] = nextNatural;
            else if (distn > ts[i])
                scala[i] = Notes.flatNotes(Notes.getIndex(scala[i - 1]) + ts[i]);
            else if (distn < ts[i])
                scala[i] = Notes.sharpNotes(Notes.getIndex(scala[i - 1]) + ts[i]);

            Note note1 = scala[i - 1].getNaturalNoteAsNote();
            Note note2 = scala[i].getNaturalNoteAsNote();
            if (note2.equals(note1)) {
                scala[i] = Notes.doubleFlatNotes(Notes.getIndex(scala[i - 1]) + ts[i]);
            } else if (note2.equals(Notes.nextNatural(Notes.nextNatural(note1)))) {
                scala[i] = Notes.doubleSharpNotes(Notes.getIndex(scala[i - 1]) + ts[i]);
            }
        }
        return scala;
    }

    public static Note[] scalaMaggiore(Note nota) {
        return scala(nota, scalaMaggiore);
    }

    public static Note[] scalaMinNat(Note note) {
        return scala(note, scalaMinNat);
    }

    public static Note[] scalaMinArm(Note note) {
        return scala(note, scalaMinArm);
    }

    public static Note[] scalaMinMel(Note note) {
        return scala(note, scalaMinMel);
    }

    private static int getNumAlterazioni(Note[] scala) {
        if (scala == null) return -1;
        int num = 0;
        for (int i = 0; i < scala.length; i++)
            if (!Notes.isNatural(scala[i]))
                num++;
        return num;
    }

    static void printScala(Note[] scala) {
        for (int i = 0; i < scala.length; i++)
            System.out.print(scala[i] + "  ");
        System.out.print(getNumAlterazioni(scala));
        System.out.println();
    }
}
