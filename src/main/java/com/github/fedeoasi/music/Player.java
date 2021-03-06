package com.github.fedeoasi.music;

import javax.sound.midi.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;
import static javax.sound.midi.ShortMessage.PROGRAM_CHANGE;

public class Player {
    private Sequencer sequencer = null;
    private Track track;
    private Sequence sequence = null;
    private ShortMessage mex;

    private Synthesizer synth;
    private Instrument[] instruments;
    private MidiChannel[] channels;
    private MidiChannel channel;

    private int notapartenza = 45;

    private int chan = 10;
    private int instr = 3;

    private int volume = 100;
    private int bpm = 120;
    private int resolution = 12;

    private Tempo tempo = new Tempo(4, 4);

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Player() {
        super();
        init();
        inizializza();
    }

    private void init() {
        try {
            if (synth == null)
                synth = MidiSystem.getSynthesizer();
            if (synth == null) System.out.println("Synth non disponibile");
            else {
                synth.open();
                instruments = synth.getAvailableInstruments();
                synth.loadInstrument(instruments[0]);
                channels = synth.getChannels();
                channel = channels[5];
            }
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void inizializza() {
        try {
            sequence = new Sequence(Sequence.PPQ, resolution);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        track = sequence.createTrack();
    }

    public void costruisciTraccia(Chord a) {
        costruisciAccordo(a.getIntervals(), a.getTonic(), 0, 15);
    }

    public void costruisciGiro(List<Chord> chords) {
        for (int i = 0; i < chords.size(); i++) {
            for (int j = 0; j < tempo.getNum(); j++) {
                int start = resolution * tempo.getNum() * (i) + resolution * j;
                int end = resolution * tempo.getNum() * i + resolution * (j + 1);
                costruisciAccordo(chords.get(i).getIntervals(), chords.get(i).getTonic(), start, end);
            }
        }
    }

    public void costruisciMelodia(int[] distanze, int notapartenza, int oct) {
        this.notapartenza = notapartenza;

        try {
            inizioTraccia();
            int l = notapartenza + oct;
            for (int i = 0; i < distanze.length; i++) {
                l = l + distanze[i];
                creaNota(chan, l, resolution * i, resolution * (i + 1));
            }
            fineTraccia();
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void costruisciAccordo(List<Integer> distanze, Note tonic,
                                  int inizio, int fine) {
        try {
            //inizio
            inizioTraccia();

            //System.out.println(tonic);
            int l = notapartenza + Notes.getIndex(tonic);


            for (int i = 0; i < distanze.size(); i++) {
                l = l + distanze.get(i);
                //creaNota(chan, notapartenza+intervals[i],4*i,4*(i+1));
                creaNota(chan, l, inizio, fine);
            }

            //fine
            fineTraccia();

            sequencer.setSequence(sequence);
            notapartenza = 45;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void costruisciBatteria() {
        Sequence sequence2 = null;
        System.out.println("Apro midi file");
        File midiFile = new File("Styles/Batteria/rock1.mid");
        try {
            sequence2 = MidiSystem.getSequence(midiFile);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
        Track[] t = sequence2.getTracks();
        for (int i = 0; i < t.length; i++) {
            System.out.println(t[i]);
            for (int j = 0; j < t[0].size(); j++) {
                System.out.println(t[i].get(j));
                MidiEvent m = t[i].get(j);
                System.out.println(m.getTick());
                //System.out.println(m.getMessage().getMessage()[0]);
            }

        }
        for (int i = 0; i < t.length; i++) {
            Track bat = sequence.createTrack();
            for (int j = 0; j < t[i].size(); j++)
                bat.add(t[i].get(j));
        }
    }

    public void costruisciBatteria(int numBattute, int stile) {
        track = sequence.createTrack();

        mex = new ShortMessage();
        try {
            //mex.setMessage(192,instr,100);
            mex.setMessage(PROGRAM_CHANGE, 9, 1, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        MidiEvent me = new MidiEvent(mex, 0);
        track.add(me);


        Batteria b = new Batteria(this, numBattute, stile);


        mex = new ShortMessage();
        try {
            mex.setMessage(PROGRAM_CHANGE, 9, 1, numBattute * 16);
        } catch (InvalidMidiDataException e1) {
            e1.printStackTrace();
        }
        me = new MidiEvent(mex, 20);
        track.add(me);

        try {
            sequencer.setSequence(sequence);
        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void creaNota2(int canale, int tipo, int inizio, int fine) {
        mex = new ShortMessage();

        try {
            mex = new ShortMessage();
            mex.setMessage(NOTE_ON, canale, tipo, 100);
            MidiEvent me = new MidiEvent(mex, inizio);
            track.add(me);

            mex = new ShortMessage();
            mex.setMessage(NOTE_OFF, canale, tipo, 100);
            me = new MidiEvent(mex, fine);
            track.add(me);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inizioTraccia() throws InvalidMidiDataException {
        mex = new ShortMessage();
        mex.setMessage(PROGRAM_CHANGE, instr, 100);
        MidiEvent me = new MidiEvent(mex, 0);
        track.add(me);
    }

    private void fineTraccia() throws InvalidMidiDataException {
        mex = new ShortMessage();
        mex.setMessage(PROGRAM_CHANGE, instr, 100);
        MidiEvent me = new MidiEvent(mex, 20);
        track.add(me);
    }

    public void creaNota(int chan, int n, int inizio, int fine) throws InvalidMidiDataException {
        mex = new ShortMessage();
        mex.setMessage(NOTE_ON, n, volume);
        MidiEvent me = new MidiEvent(mex, inizio);
        track.add(me);

        mex = new ShortMessage();
        mex.setMessage(NOTE_OFF, n, volume);
        me = new MidiEvent(mex, fine);
        track.add(me);
    }

    public void start() {
        sequencer.setTempoInBPM(bpm);
        sequencer.start();
    }

    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
            inizializza();
        }
    }

    public void save() {
        File f;
        JFileChooser fc = new JFileChooser("MyMidi");
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            if (f != null)
                System.out.println(f);

            int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
            if (fileTypes.length == 0) System.out.println("Non posso salvare");
            else {
                try {
                    MidiSystem.write(sequence, fileTypes[0], f);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void setInstrument(int instr) {
        this.instr = instr;
    }

    public int getNotapartenza() {
        return notapartenza;
    }

    public void setNotapartenza(int notapartenza) {
        this.notapartenza = notapartenza;
    }

    public void costruisciMelodia(List<List<Integer>> distanze2,
                                  List<List<Integer>> durate, Note tonic,
                                  int oct, int inizio, int numAccordo) {
        int in;
        int fine;

        try {
            inizioTraccia();
            int l = notapartenza + oct + Notes.getIndex(tonic) + 12;
            in = inizio;
            for (int i = 0; i < distanze2.get(numAccordo).size(); i++) {
                fine = in + 3 * durate.get(numAccordo).get(i);
                //l=l+distanze2.get(i);
                //creaNota(chan, notapartenza+intervals[i],4*i,4*(i+1));
                creaNota(chan, l + distanze2.get(numAccordo).get(i), in, fine);
                in = fine;
            }
            fineTraccia();
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Prova");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Player p = new Player();
        Sequencer s = p.getSequencer();
        s.addMetaEventListener(new MetaEventListener() {

            public void meta(MetaMessage m) {
                System.out.println(m.getType());

            }

        });
        p.costruisciBatteria();
        f.setVisible(true);
        p.start();
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
    }

    public int getBPM() {
        return bpm;
    }
}
