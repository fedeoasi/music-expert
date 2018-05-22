package com.github.fedeoasi.music;

import java.util.*;
import java.util.stream.IntStream;

public class Scale {
    private Note tonic;
    private int[] intervals;
    private Note[] notes;

    private Notes n = new Notes();

    public Scale(Note tonic, int[] intervals) {
        this.tonic = tonic;
        this.intervals = intervals;
        notes = notes(tonic, intervals);
    }

    private Note[] notes(Note tonic, int[] intervals) {
        Note[] scala = new Note[7];
        scala[0] = tonic;
        for (int i = 1; i < 7; i++) {
            Note nextNatural = n.nextNatural(scala[i - 1]);
            int distn = n.distance(scala[i - 1], nextNatural);
            if (distn == intervals[i])
                scala[i] = nextNatural;
            else if (distn > intervals[i])
                scala[i] = n.flatNotes(n.getIndex(scala[i - 1]) + intervals[i]);
            else if (distn < intervals[i])
                scala[i] = n.sharpNotes(n.getIndex(scala[i - 1]) + intervals[i]);

            Note note1 = scala[i - 1].getNaturalNoteAsNote();
            Note note2 = scala[i].getNaturalNoteAsNote();
            if (note2.equals(note1)) {
                scala[i] = n.doubleFlatNotes(n.getIndex(scala[i - 1]) + intervals[i]);
            } else if (note2.equals(n.nextNatural(n.nextNatural(note1)))) {
                scala[i] = n.doubleSharpNotes(n.getIndex(scala[i - 1]) + intervals[i]);
            }
        }
        return scala;
    }

    public Note getTonic() {
        return tonic;
    }

    public int[] getIntervals() {
        return intervals;
    }

    public Note[] getNotes() {
        return notes;
    }

    public Scale from(Note note) {
        OptionalInt indexOpt = IntStream.range(0, notes.length)
            .filter(i -> note.equals(notes[i]))
            .findFirst();

        List<Note> asList = Arrays.asList(notes);
        Collections.rotate(asList, notes.length - indexOpt.getAsInt());
        Note[] newNotes = (Note[]) asList.toArray();
        return new Scale(note, n.distances(newNotes));
    }

    @Override
    public String toString() {
        return Arrays.asList(notes).toString();
    }
}
