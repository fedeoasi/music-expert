package com.github.fedeoasi.music;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChordScaleFinder {
    private Scales scales = new Scales();
    private Notes notes = new Notes();

    private Multimap<String, String[]> scalesByNote = buildAllScalesByNote();

    public Collection<String[]> findScales(String note) {
        return scalesByNote.get(note);
    }

    public List<String[]> findScales(Chord chord) {
        List<HashSet<String[]>> scaleSets = chord.getNotes().stream()
            .map(note -> new HashSet<>(scalesByNote.get(note))).collect(Collectors.toList());
        Set<String[]> intersection = scaleSets.stream().skip(1)
            .collect(() -> new HashSet<>(scaleSets.get(0)), Set::retainAll, Set::retainAll);
        return new ArrayList<>(intersection);
    }

    private Set<String> buildAllNotes() {
        Stream<String> naturalNotes = Arrays.stream(notes.noteNaturali);
        Stream<String> sharpNotes = Arrays.stream(notes.noted);
        Stream<String> flatNotes = Arrays.stream(notes.noteb);
        Stream<String> allNotes = Stream.concat(naturalNotes, Stream.concat(sharpNotes, flatNotes));
        return allNotes.collect(Collectors.toSet());
    }

    private List<String[]> buildAllScales(Set<String> allNotes) {
        return allNotes
            .stream()
            .flatMap(nota -> Stream.of(scales.scalaMaggiore(nota), scales.scalaMinArm(nota), scales.scalaMinMel(nota)))
            .collect(Collectors.toList());
    }

    private Multimap<String, String[]> buildAllScalesByNote() {
        Set<String> allNotes = buildAllNotes();
        List<String[]> allScales = buildAllScales(allNotes);
        Multimap<String, String[]> multimap = ArrayListMultimap.create();
        allScales.forEach(scale -> Arrays.stream(scale).forEach(note -> multimap.put(note, scale)));
        return multimap;
    }
}
