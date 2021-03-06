package com.github.fedeoasi.music;

import java.util.*;
import java.util.stream.IntStream;

public class Scale {
    private Note tonic;
    private int[] intervals;
    private Note[] notes;

    public Scale(Note tonic, int[] intervals) {
        this.tonic = tonic;
        this.intervals = intervals;
        notes = notes(tonic, intervals);
    }

    private Note[] notes(Note tonic, int[] intervals) {
        Note[] scala = new Note[7];
        scala[0] = tonic;
        for (int i = 1; i < 7; i++) {
            Note nextNatural = Notes.nextNatural(scala[i - 1]);
            int distn = Notes.distance(scala[i - 1], nextNatural);
            if (distn == intervals[i])
                scala[i] = nextNatural;
            else if (distn > intervals[i])
                scala[i] = Notes.flatNotes(Notes.getIndex(scala[i - 1]) + intervals[i]);
            else if (distn < intervals[i])
                scala[i] = Notes.sharpNotes(Notes.getIndex(scala[i - 1]) + intervals[i]);

            Note note1 = scala[i - 1].getNaturalNoteAsNote();
            Note note2 = scala[i].getNaturalNoteAsNote();
            if (note2.equals(note1)) {
                scala[i] = Notes.doubleFlatNotes(Notes.getIndex(scala[i - 1]) + intervals[i]);
            } else if (note2.equals(Notes.nextNatural(Notes.nextNatural(note1)))) {
                scala[i] = Notes.doubleSharpNotes(Notes.getIndex(scala[i - 1]) + intervals[i]);
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

        Note[] notesCopy = Arrays.copyOf(this.notes, notes.length);
        List<Note> asList = Arrays.asList(notesCopy);
        Collections.rotate(asList, this.notes.length - indexOpt.getAsInt());
        Note[] newNotes = (Note[]) asList.toArray();
        return new Scale(note, Notes.distances(newNotes));
    }

    @Override
    public String toString() {
        return Arrays.asList(notes).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scale scale = (Scale) o;
        return tonic == scale.tonic &&
            Arrays.equals(intervals, scale.intervals);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(tonic);
        result = 31 * result + Arrays.hashCode(intervals);
        return result;
    }
}
