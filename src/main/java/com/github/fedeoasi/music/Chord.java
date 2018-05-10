package com.github.fedeoasi.music;

import java.util.ArrayList;

public class Chord {
    private String sigla;
    private String chordType;
    private ArrayList<Integer> intervals = new ArrayList<Integer>();
    private ArrayList<String> notes = new ArrayList<String>();
    private ArrayList<Integer> pitches = new ArrayList<Integer>();
    private ArrayList<int[]> scales = new ArrayList<int[]>();
    private String tonic;

    public Chord(String tonic, String chordType) {
        this.tonic = tonic;
        this.chordType = chordType;
        boolean esiste = false;
        Scales s = new Scales();
        Notes n = new Notes();
        String[] scala;

        if (isMajor(chordType) || isDominant(chordType)) {
            esiste = true;
            scala = s.scalaMaggiore(tonic);
            //aggiunge la tonica
            notes.add(scala[0]);
            //aggiunge la terza maggiore
            notes.add(scala[2]);
            //aggiunge la quinta giusta
            if (isNotAugmented(chordType))
                notes.add(scala[4]);
            else {
                Mode m = new Mode(tonic, chordType, s.scalaMinMel, 3);
                notes.add(m.getNote()[4]);
                m = new Mode(tonic, chordType, s.scalaMinMel, 7);
                scales.add(m.getDistanze());
            }
            //aggiunge la settima minore
            if (isDominant(chordType)) {
                Mode m = new Mode(tonic, chordType, s.scalaMaggiore, 5);
                notes.add(m.getNote()[6]);
                scales.add(m.getDistanze());
                //notes.add(n.noteb[n.getIndex(scala[6])-1]);
            } else {
                scales.add(s.scalaMaggiore);
            }
            //aggiunge la settima maggiore
            if (hasMajorSeventh(chordType))
                notes.add(scala[6]);
            //aggiunge la nona maggiore
            if (hasMajorNinth(chordType))
                notes.add(scala[8 % 7]);
            //aggiunge la sesta o tredicesima maggiore
            if (hasThirteenth(chordType))
                notes.add(scala[12 % 7]);
            sigla = tonic + chordType;
            //printAccordo();
        }

        //Accordi minori e semidiminuiti 
        else if (isMinor(chordType) || isSemiDiminished(chordType)) {
            esiste = true;
            scala = s.scalaMinNat(tonic);
            //aggiunge la tonic
            notes.add(scala[0]);
            notes.add(scala[2]);
            if (!chordType.equals("m7b5") && !chordType.equals("m9b5")) {
                Mode m = new Mode(tonic, chordType, s.scalaMaggiore, 2);
                scales.add(m.getDistanze());
                scales.add(s.scalaMinNat);
                notes.add(scala[4]);
            } else {
                Mode m = new Mode(tonic, chordType, s.scalaMaggiore, 7);
                notes.add(m.getNote()[4]);
                scales.add(m.getDistanze());
                //notes.add(n.noteb[(n.getIndex(scala[4])-1)%12]);
            }
            if (chordType.equals("m6")) {
                notes.add(n.noted[(n.getIndex(scala[5]) + 1) % 12]);
            }
            if (chordType.equals("m7") || chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m7b5") || chordType.equals("m9b5")) {
                notes.add(scala[6]);
            }
            if (chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m9b5")) {
                notes.add(scala[8 % 7]);
            }
            if (chordType.equals("m13")) {
                notes.add(n.noted[(n.getIndex(scala[5]) + 1) % 12]);
            }
            sigla = tonic + chordType;
        }

        if (!esiste) {
            System.out.println("Errore accordo inesistente");
        }
        else {
            intervals.add(0);
            for (int i = 1; i < notes.size(); i++)
                intervals.add(n.distance(notes.get(i - 1), notes.get(i)));

            pitches.add(57 + n.getIndex(tonic));
            for (int i = 1; i < intervals.size(); i++)
                pitches.add(pitches.get(i - 1) + intervals.get(i));
        }
    }

    private boolean hasThirteenth(String name) {
        return name.equals("M13") || name.equals("13");
    }

    private boolean hasMajorNinth(String name) {
        return name.equals("M9") || name.equals("M13") || name.equals("9") || name.equals("13");
    }

    private boolean hasMajorSeventh(String name) {
        return name.equals("M7") || name.equals("M9") || name.equals("M13");
    }

    private boolean isSemiDiminished(String name) {
        return name.equals("m7b5") || name.equals("m9b5");
    }

    private boolean isMinor(String name) {
        return name.equals("m") || name.equals("m7") || name.equals("m9") || name.equals("m13") || name.equals("m6");
    }

    private boolean isNotAugmented(String name) {
        return !name.equals("7#5");
    }

    private boolean isDominant(String nome) {
        return nome.equals("7") || nome.equals("9") || nome.equals("13")
                || nome.equals("7#5") || nome.equals("7b9") || nome.equals("7#5");
    }

    private boolean isMajor(String nome) {
        return nome.equals("") || nome.equals("M7") || nome.equals("M9") || nome.equals("M13");
    }

    public ArrayList<Integer> getIntervals() {
        return intervals;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public ArrayList<Integer> getPitches() {
        return pitches;
    }

    public ArrayList<int[]> getScales() {
        return scales;
    }

    public String getSigla() {
        return sigla;
    }

    public String getChordType() {
        return chordType;
    }

    public String getTonic() {
        return tonic;
    }
}
