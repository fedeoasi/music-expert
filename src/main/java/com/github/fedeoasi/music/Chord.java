package com.github.fedeoasi.music;

import java.util.ArrayList;
import java.util.List;

public class Chord {
    private String sigla;
    private String chordType;
    private List<Integer> intervals = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private List<Integer> pitches = new ArrayList<>();
    private Note tonic;

    public Chord(Note tonic, ChordType type) {
        this.tonic = tonic;
        this.chordType = type.toString();
        Note[] scala;

        if (type.isMajor()) {
            scala = Scales.scalaMaggiore(tonic);
            //aggiunge la tonica
            notes.add(scala[0]);
            //aggiunge la terza maggiore
            notes.add(scala[2]);
            //aggiunge la quinta giusta
            if (isNotAugmented(chordType))
                notes.add(scala[4]);
            else {
                Mode m = new Mode(tonic, chordType, Scales.scalaMinMel, 3);
                notes.add(m.getNotes()[4]);
                m = new Mode(tonic, chordType, Scales.scalaMinMel, 7);
            }
            //aggiunge la settima minore
            if (type.isDominant()) {
                Mode m = new Mode(tonic, chordType, Scales.scalaMaggiore, 5);
                notes.add(m.getNotes()[6]);
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
            scala = Scales.scalaMinNat(tonic);
            //aggiunge la tonic
            notes.add(scala[0]);
            notes.add(scala[2]);
            if (!chordType.equals("m7b5") && !chordType.equals("m9b5")) {
                Mode m = new Mode(tonic, chordType, Scales.scalaMaggiore, 2);
                notes.add(scala[4]);
            } else {
                Mode m = new Mode(tonic, chordType, Scales.scalaMaggiore, 7);
                notes.add(m.getNotes()[4]);
                //notes.add(n.noteb[(n.getIndex(scala[4])-1)%12]);
            }
            if (chordType.equals("m6")) {
                notes.add(Notes.sharpNotes[(Notes.getIndex(scala[5]) + 1) % 12]);
            }
            if (chordType.equals("m7") || chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m7b5") || chordType.equals("m9b5")) {
                notes.add(scala[6]);
            }
            if (chordType.equals("m9") || chordType.equals("m13") || chordType.equals("m9b5")) {
                notes.add(scala[8 % 7]);
            }
            if (chordType.equals("m13")) {
                notes.add(Notes.sharpNotes[(Notes.getIndex(scala[5]) + 1) % 12]);
            }
            sigla = tonic + chordType;
        }

        intervals.add(0);
        for (int i = 1; i < notes.size(); i++)
            intervals.add(Notes.distance(notes.get(i - 1), notes.get(i)));

        pitches.add(57 + Notes.getIndex(tonic));
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

    public String getSigla() {
        return sigla;
    }

    public Note getTonic() {
        return tonic;
    }

    public String getChordType() {
        return chordType;
    }

    @Override
    public String toString() {
        return sigla;
    }
}
