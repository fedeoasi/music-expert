package com.github.fedeoasi.music;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChordScaleFinder {
    private Multimap<Note, Scale> scalesByNote = buildAllScalesByNote();

    public List<List<ScoredScale>> progression(List<Chord> chords) {
        List<Chord> chordChanges = new ArrayList<>();
        List<Integer> changeIndices = new ArrayList<>();
        if (!chords.isEmpty()) {
            chordChanges.add(chords.get(0));
            changeIndices.add(0);
        }
        for (int i = 1; i < chords.size(); i++) {
            Chord currentChord = chords.get(i);
            Chord priorChord = chords.get(i - 1);
            if (!currentChord.getSigla().equals(priorChord.getSigla())) {
                chordChanges.add(currentChord);
            }
            changeIndices.add(chordChanges.size() - 1);
        }
        Collection<ScoredScale> overallScales = rankScales(chordChanges);

        List<List<ScoredScale>> returnValue = new ArrayList<>();
        for (int i = 0; i < chords.size(); i++) {
            final Chord currentChord = chords.get(i);
            Integer changeIndex = changeIndices.get(i);
            int priorChordIndex = changeIndex == 0 ? chordChanges.size() - 1 : changeIndex - 1;
            Optional<Chord> priorChord = Optional.of(chordChanges.get(priorChordIndex));
            int nextChordIndex = changeIndex == chordChanges.size() - 1 ? 0 : changeIndex + 1;
            Optional<Chord> nextChord = Optional.of(chordChanges.get(nextChordIndex));

            List<ScoredScale> scales = findScales(priorChord, chords.get(i), nextChord).stream()
                .map(s -> {
                    Optional<ScoredScale> first = overallScales.stream().filter(o -> o.getScale() == s).findFirst();
                    return new ScoredScale(s.from(currentChord.getTonic()), first.get().getRank());
                }).collect(Collectors.toList());
            returnValue.add(scales);
        }
        return returnValue;
    }

    public Collection<ScoredScale> rankScales(List<Chord> chords) {
        List<Scale> allScales = new ArrayList<>();
        for (int i = 0; i < chords.size(); i++) {
            Chord currentChord = chords.get(i);
            int priorChordIndex = i == 0 ? chords.size() - 1 : i - 1;
            Optional<Chord> priorChord = Optional.of(chords.get(priorChordIndex));
            int nextChordIndex = i == chords.size() - 1 ? 0 : i + 1;
            Optional<Chord> nextChord = Optional.of(chords.get(nextChordIndex));
            allScales.addAll(findScales(priorChord, currentChord, nextChord));
        }
        Map<Scale, Long> counted = allScales.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return counted.entrySet().stream()
            .map(e -> new ScoredScale(e.getKey(), e.getValue().intValue()))
            .sorted(Comparator.comparing(ScoredScale::getRank).reversed())
            .collect(Collectors.toList());
    }

    public Collection<Scale> findScales(Note note) {
        return scalesByNote.get(note);
    }

    public List<Scale> findScales(Chord chord) {
        Stream<Note> notesStream = chord.getNotes().stream();
        Set<Scale> intersection = scalesForNotes(notesStream);
        return new ArrayList<>(intersection);
    }

    public List<Scale> findModes(Chord chord) {
        Stream<Note> notesStream = chord.getNotes().stream();
        Set<Scale> intersection = scalesForNotes(notesStream);
        return adjustScales(chord.getTonic(), intersection);
    }


    public List<Scale> findScales(Optional<Chord> prior, Chord chord, Optional<Chord> next) {
        Stream<Note> allNotes = Stream.concat(toNoteStream(prior), chord.getNotes().stream());
        List<Scale> scalesForChordAndPrior = new ArrayList<>(scalesForNotes(allNotes));

        if (scalesForChordAndPrior.isEmpty()) {
            Stream<Note> notesWithNext = Stream.concat(toNoteStream(next), chord.getNotes().stream());
            List<Scale> scalesForChordAndNext = new ArrayList<>(scalesForNotes(notesWithNext));
            if (scalesForChordAndNext.isEmpty()) {
                return new ArrayList<>(scalesForChordAndNext);
            } else {
                return new ArrayList<>(scalesForNotes(chord.getNotes().stream()));
            }
        } else if (scalesForChordAndPrior.size() > 1) {
            //Use the prior, chord, and after to try and resolve the ambiguity
            Stream<Note> streamWithPrior = Stream.concat(toNoteStream(prior), chord.getNotes().stream());
            Stream<Note> noteStream = Stream.concat(streamWithPrior, toNoteStream(next));
            ArrayList<Scale> scalesWithPriorAndNext = new ArrayList<>(scalesForNotes(noteStream));
            if (scalesWithPriorAndNext.isEmpty()) {
                return new ArrayList<>(scalesForChordAndPrior);
            } else {
                return scalesWithPriorAndNext;
            }
        } else {
            //found exactly one scale for chord and prior
            return scalesForChordAndPrior;
        }
    }

    public List<Scale> findModes(Optional<Chord> prior, Chord chord, Optional<Chord> after) {
        return adjustScales(chord.getTonic(), findScales(prior, chord, after));
    }

    private List<Scale> adjustScales(Note tonic, Collection<Scale> intersection) {
        return intersection.stream().map(scale -> scale.from(tonic)).collect(Collectors.toList());
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
        Stream<Note> naturalNotes = Arrays.stream(Notes.naturalNotes);
        Stream<Note> sharpNotes = Arrays.stream(Notes.sharpNotes);
        Stream<Note> flatNotes = Arrays.stream(Notes.flatNotes);
        Stream<Note> allNotes = Stream.concat(naturalNotes, Stream.concat(sharpNotes, flatNotes));
        return allNotes.collect(Collectors.toSet());
    }

    //TODO represent scales as first class objects

    private List<Scale> buildAllScales(Set<Note> allNotes) {
        return allNotes
            .stream()
            .flatMap(note -> Stream.of(
                new Scale(note, Scales.scalaMaggiore),
                new Scale(note, Scales.scalaMinArm),
                new Scale(note, Scales.scalaMinMel)))
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
