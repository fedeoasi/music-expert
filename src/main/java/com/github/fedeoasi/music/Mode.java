package com.github.fedeoasi.music;

public class Mode {
    private String nome;
    private String[] note;
    private String tonica;
    private int[] distanze = new int[8];
    private int[] generatrice;
    private int partenza;

    public Mode(String tonica, String nome, int[] generatrice, int partenza) {
        this.tonica = tonica;
        this.nome = nome;
        this.generatrice = generatrice;
        this.partenza = partenza;
        int temp;
        int[] gen = new int[7];
        for (int i = 0; i < 7; i++)
            gen[i] = generatrice[i + 1];
        distanze[0] = 0;
        //System.out.print(intervals[0]);
        for (int i = 1; i < gen.length + 1; i++) {
            temp = (partenza - 2 + i) % 7;
            distanze[i] = gen[temp];
            //System.out.print("  " + intervals[i]);
        }
        System.out.println();

        Scales s = new Scales();
        note = s.scala(tonica, distanze);

        /*for(int i=0; i<notes.length; i++)
            System.out.print(notes[i] + "  ");
        System.out.println();
        */

    }

    public String getTonica() {
        return tonica;
    }

    public void setTonica(String tonica) {
        this.tonica = tonica;
    }

    public String getNome() {
        return nome;
    }

    public String[] getNote() {
        return note;
    }

    public int[] getDistanze() {
        return distanze;
    }

    public int[] getGeneratrice() {
        return generatrice;
    }

    public int getPartenza() {
        return partenza;
    }

}