package com.github.fedeoasi.music;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChordScaleFinder {
    private Scales scales = new Scales();
    private Notes notes = new Notes();

    private Multimap<Note, Scale> scalesByNote = buildAllScalesByNote();

    public Collection<Scale> findScales(Note note) {
        return scalesByNote.get(note);
    }

    public List<Scale> findScales(Chord chord) {
        Stream<Note> notesStream = chord.getNotes().stream();
        Set<Scale> intersection = scalesForNotes(notesStream);
        return adjustScales(chord.getTonic(), intersection);
    }

    private List<Scale> adjustScales(Note tonic, Set<Scale> intersection) {
        return intersection.stream().map(scale -> scale.from(tonic)).collect(Collectors.toList());
    }

    public List<Scale> findScales(Optional<Chord> prior, Chord chord, Optional<Chord> after) {
        Stream<Note> allNotes = Stream.concat(toNoteStream(prior), chord.getNotes().stream());
        List<Scale> scalesForChordAndPrior = adjustScales(chord.getTonic(), scalesForNotes(allNotes));
        //Only use the chord after if there is ambiguity
        if (scalesForChordAndPrior.size() > 1) {
            Stream<Note> streamWithPrior = Stream.concat(toNoteStream(prior), chord.getNotes().stream());
            Stream<Note> noteStream = Stream.concat(streamWithPrior, toNoteStream(after));
            return adjustScales(chord.getTonic(), (scalesForNotes(noteStream)));
        }
        return scalesForChordAndPrior;
    }

    private Stream<Note> toNoteStream(Optional<Chord> chord) {
        Stream<Note> notes = Stream.empty();
        if (chord.isPresent()) {
            notes = chord.get().getNotes().stream();
        }
        return notes;
    }

    private Set<Scale> scalesForNotes(Stream<Note> notesStream) {
        List<HashSet<Scale>> scaleSets = notesStream
            .map(note -> new HashSet<>(scalesByNote.get(note))).collect(Collectors.toList());
        return scaleSets.stream().skip(1)
            .collect(() -> new HashSet<>(scaleSets.get(0)), Set::retainAll, Set::retainAll);
    }

    private Set<Note> buildAllNotes() {
        Stream<Note> naturalNotes = Arrays.stream(notes.naturalNotes);
        Stream<Note> sharpNotes = Arrays.stream(notes.sharpNotes);
        Stream<Note> flatNotes = Arrays.stream(notes.flatNotes);
        Stream<Note> allNotes = Stream.concat(naturalNotes, Stream.concat(sharpNotes, flatNotes));
        return allNotes.collect(Collectors.toSet());
    }

    //TODO represent scales as first class objects

    private List<Scale> buildAllScales(Set<Note> allNotes) {
        return allNotes
            .stream()
            .flatMap(note -> Stream.of(
                new Scale(note, scales.scalaMaggiore),
                new Scale(note, scales.scalaMinArm),
                new Scale(note, scales.scalaMinMel)))
            .collect(Collectors.toList());
    }

    private Multimap<Note, Scale> buildAllScalesByNote() {
        Set<Note> allNotes = buildAllNotes();
        List<Scale> allScales = buildAllScales(allNotes);
        Multimap<Note, Scale> multimap = ArrayListMultimap.create();
        allScales.forEach(scale -> Arrays.stream(scale.getNotes()).forEach(note -> multimap.put(note, scale)));
        return multimap;
    }
}
