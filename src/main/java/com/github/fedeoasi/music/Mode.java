package com.github.fedeoasi.music;

public class Mode {
    private String nome;
    private Note[] notes;
    private Note tonic;
    private int[] distanze = new int[8];
    private int[] generatrice;
    private int oneIndexedStart;

    public Mode(Note tonic, String nome, int[] generatrice, int oneIndexedStart) {
        this.tonic = tonic;
        this.nome = nome;
        this.generatrice = generatrice;
        this.oneIndexedStart = oneIndexedStart;
        int temp;
        int[] gen = new int[7];
        for (int i = 0; i < 7; i++)
            gen[i] = generatrice[i + 1];
        distanze[0] = 0;
        for (int i = 1; i < gen.length + 1; i++) {
            temp = (oneIndexedStart - 2 + i) % 7;
            distanze[i] = gen[temp];
        }
        System.out.println();

        Scales s = new Scales();
        notes = s.scala(tonic, distanze);
    }

    public Note getTonic() {
        return tonic;
    }

    public String getNome() {
        return nome;
    }

    public Note[] getNotes() {
        return notes;
    }

    public int[] getDistanze() {
        return distanze;
    }

    public int[] getGeneratrice() {
        return generatrice;
    }

    public int getOneIndexedStart() {
        return oneIndexedStart;
    }
}
