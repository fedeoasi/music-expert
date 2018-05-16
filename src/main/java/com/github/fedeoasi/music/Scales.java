package com.github.fedeoasi.music;

public class Scales {
    public int[] scalaMaggiore = {0, 2, 2, 1, 2, 2, 2, 1};
    public int[] scalaMinNat = {0, 2, 1, 2, 2, 1, 2, 2};
    public int[] scalaMinArm = {0, 2, 1, 2, 2, 1, 3, 1};
    public int[] scalaMinMel = {0, 2, 1, 2, 2, 2, 2, 1};
    public int[] pentaMaggiore = {0, 2, 2, 3, 2, 3};
    public int[] pentaMinore = {0, 3, 2, 2, 3, 2};
    public int[] esatonale = {0, 2, 2, 2, 2, 2};
    public int[] tonoSemitono = {0, 2, 1, 2, 1, 2, 1, 2, 1};
    public int[] semitonoTono = {0, 1, 2, 1, 2, 1, 2, 1, 2};
    private Notes n;

    public Scales() {
        n = new Notes();
    }

    public Note[] scala(Note nota, int[] ts) {
        Note[] scala = new Note[7];
        scala[0] = nota;
        for (int i = 1; i < 7; i++) {
            Note nextNatural = n.nextNatural(scala[i - 1]);
            int distn = n.distance(scala[i - 1], nextNatural);
            if (distn == ts[i])
                scala[i] = nextNatural;
            else if (distn > ts[i])
                scala[i] = n.flatNotes(n.getIndex(scala[i - 1]) + ts[i]);
            else if (distn < ts[i])
                scala[i] = n.sharpNotes(n.getIndex(scala[i - 1]) + ts[i]);

            Note note1 = scala[i - 1].getNaturalNoteAsNote();
            Note note2 = scala[i].getNaturalNoteAsNote();
            if (note2.equals(note1)) {
                scala[i] = n.doubleFlatNotes(n.getIndex(scala[i - 1]) + ts[i]);
            } else if (note2.equals(n.nextNatural(n.nextNatural(note1)))) {
                scala[i] = n.doubleSharpNotes(n.getIndex(scala[i - 1]) + ts[i]);
            }
        }
        return scala;
    }

    public Note[] scalaMaggiore(Note nota) {
        return scala(nota, scalaMaggiore);
    }

    public Note[] scalaMinNat(Note note) {
        return scala(note, scalaMinNat);
    }

    public Note[] scalaMinArm(Note note) {
        return scala(note, scalaMinArm);
    }

    public Note[] scalaMinMel(Note note) {
        return scala(note, scalaMinMel);
    }

    public int getNumAlterazioni(Note[] scala) {
        if (scala == null) return -1;
        int num = 0;
        for (int i = 0; i < scala.length; i++)
            if (!n.isNatural(scala[i]))
                num++;
        return num;
    }

    public void printScala(Note[] scala) {
        for (int i = 0; i < scala.length; i++)
            System.out.print(scala[i] + "  ");
        System.out.print(getNumAlterazioni(scala));
        System.out.println();
    }
}
