package com.github.fedeoasi.music;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChordScaleFinder {
    private Scales scales = new Scales();
    private Notes notes = new Notes();

    private Multimap<Note, Note[]> scalesByNote = buildAllScalesByNote();

    public Collection<Note[]> findScales(Note note) {
        return scalesByNote.get(note);
    }

    public List<Note[]> findScales(Chord chord) {
        List<HashSet<Note[]>> scaleSets = chord.getNotes().stream()
            .map(note -> new HashSet<>(scalesByNote.get(note))).collect(Collectors.toList());
        Set<Note[]> intersection = scaleSets.stream().skip(1)
            .collect(() -> new HashSet<>(scaleSets.get(0)), Set::retainAll, Set::retainAll);
        return new ArrayList<>(intersection);
    }

    private Set<Note> buildAllNotes() {
        Stream<Note> naturalNotes = Arrays.stream(notes.naturalNotes);
        Stream<Note> sharpNotes = Arrays.stream(notes.sharpNotes);
        Stream<Note> flatNotes = Arrays.stream(notes.flatNotes);
        Stream<Note> allNotes = Stream.concat(naturalNotes, Stream.concat(sharpNotes, flatNotes));
        return allNotes.collect(Collectors.toSet());
    }

    private List<Note[]> buildAllScales(Set<Note> allNotes) {
        return allNotes
            .stream()
            .flatMap(note -> Stream.of(scales.scalaMaggiore(note), scales.scalaMinArm(note), scales.scalaMinMel(note)))
            .collect(Collectors.toList());
    }

    private Multimap<Note, Note[]> buildAllScalesByNote() {
        Set<Note> allNotes = buildAllNotes();
        List<Note[]> allScales = buildAllScales(allNotes);
        Multimap<Note, Note[]> multimap = ArrayListMultimap.create();
        allScales.forEach(scale -> Arrays.stream(scale).forEach(note -> multimap.put(note, scale)));
        return multimap;
    }
}
