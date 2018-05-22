package com.github.fedeoasi.music;

import java.util.ArrayList;
import java.util.List;

public class Chord {
    private String sigla;
    private String chordType;
    private List<Integer> intervals = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<Integer> pitches = new ArrayList<>();
    private List<int[]> scales = new ArrayList<>();
    private Note tonic;

    public Chord(Note tonic, ChordType type) {
        this.tonic = tonic;
        this.chordType = type.toString();
        Scales s = new Scales();
        Notes n = new Notes();
        Note[] scala;

        if (type.isMajor()) {
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
                notes.add(m.getNotes()[4]);
                m = new Mode(tonic, chordType, s.scalaMinMel, 7);
                scales.add(m.getDistanze());
            }
            //aggiunge la settima minore
            if (type.isDominant()) {
                Mode m = new Mode(tonic, chordType, s.scalaMaggiore, 5);
                notes.add(m.getNotes()[6]);
                scales.add(m.getDistanze());
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
        else if (type.isMinor() || type.isSemiDiminished()) {
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
                notes.add(m.getNotes()[4]);
                scales.add(m.getDistanze());
                //notes.add(n.noteb[(n.getIndex(scala[4])-1)%12]);
            }
            if (chordType.equals("m6")) {
                notes.add(n.sharpNotes[(n.getIndex(scala[5]) + 1) % 12]);
            }
            if (chordType.equals("m7") || chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m7b5") || chordType.equals("m9b5")) {
                notes.add(scala[6]);
            }
            if (chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m9b5")) {
                notes.add(scala[8 % 7]);
            }
            if (chordType.equals("m13")) {
                notes.add(n.sharpNotes[(n.getIndex(scala[5]) + 1) % 12]);
            }
            sigla = tonic + chordType;
        }

        intervals.add(0);
        for (int i = 1; i < notes.size(); i++)
            intervals.add(n.distance(notes.get(i - 1), notes.get(i)));

        pitches.add(57 + n.getIndex(tonic));
        for (int i = 1; i < intervals.size(); i++)
            pitches.add(pitches.get(i - 1) + intervals.get(i));
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

    private boolean isNotAugmented(String name) {
        return !name.equals("7#5");
    }

    public List<Integer> getIntervals() {
        return intervals;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Integer> getPitches() {
        return pitches;
    }

    public List<int[]> getScales() {
        return scales;
    }

    public String getSigla() {
        return sigla;
    }

    public Note getTonic() {
        return tonic;
    }

    @Override
    public String toString() {
        return sigla;
    }
}
