package com.github.fedeoasi.music;

import javax.sound.midi.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private Sequencer sequencer = null;
    private Track track;
    private Sequence sequence = null;
    private ShortMessage mex;

    private Synthesizer synth;
    private Instrument[] instruments;
    private MidiChannel[] channels;
    private MidiChannel channel;

    private JPanel buttons = new JPanel();
    private JPanel center = new JPanel();

    private JTextField tf = new JTextField(10);
    private JLabel label = new JLabel();
    private JButton changeInstr = new JButton("Change");

    private JButton b = new JButton("Start");
    private JButton stop = new JButton("Stop");
    private JButton save = new JButton("Save");

    private int notapartenza = 45;
    private static int[] distanze = {0, 2, 4, 0, 0, 2, 4, 0, 4, 5, 7};

    private int chan = 10;
    private int instr = 3;

    private Notes n = new Notes();

    private int volume = 100;
    private int bpm = 120;

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

    public Player(int[] distanze) {
        super();
        init();
        inizializza();
        this.distanze = distanze;
    }

    public Player(Chord accordo) {
        super();
        init();
        inizializza();
        costruisciTraccia(accordo);
    }

    public Player(Chord accordo, int oct, int instr) {
        super();
        this.instr = instr;
        notapartenza += oct;
        init();
        inizializza();
        costruisciTraccia(accordo);
    }

    private void init() {
        try {
            if (synth == null)
                synth = MidiSystem.getSynthesizer();
            if (synth == null) System.out.println("Synth non disponibile");
            else {
            synth.open();
            instruments = synth.getAvailableInstruments();
            
            /*Causa Problemi su alcuni computer e rende inutilizzabile il synth 
            Soundbank sb= synth.getDefaultSoundbank();
            if(sb!=null) instruments = sb.getInstruments();
            */

            //for(int i=0; i<instruments.length; i++)
            //System.out.println(instruments[i]);
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
            sequence = new Sequence(Sequence.PPQ, 12);
            //System.out.println(sequence.getResolution());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        track = sequence.createTrack();
        //System.out.println("crea traccia :" + track.size());
    }

    public void costruisciTraccia(Chord a) {
        costruisciAccordo(a.getIntervals(), a.getTonic(), 0, 15);
    }

    public void costruisciGiro(ArrayList<Chord> a){
            for(int i=0; i<a.size(); i++)
            for(int j=0; j< tempo.getNum(); j++)
            costruisciAccordo(a.get(i).getIntervals(),a.get(i).getTonic(),
                    12 * tempo.getNum()*(i)+12*j,12*tempo.getNum()*i+12*(j+1));

    }


    public void costruisciMelodia(int[] distanze, int notapartenza, int oct) {
        this.distanze = distanze;
        this.notapartenza = notapartenza;

        try {
            //inizio
            inizioTraccia();

            int l = notapartenza + oct;
            for (int i = 0; i < distanze.length; i++) {
                l = l + distanze[i];
                //creaNota(chan, notapartenza+intervals[i],4*i,4*(i+1));
                creaNota(chan, l, 4 * i, 4 * (i + 1));
            }

            //fine
            fineTraccia();

            sequencer.setSequence(sequence);

            /*
            //sequencer.setTempoInBPM(60);
            System.out.println(sequencer.getTempoInBPM());
            System.out.println(sequence.getResolution());
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void costruisciAccordo(ArrayList<Integer> distanze, String tonica,
                                  int inizio, int fine) {
        try {
            Notes n = new Notes();
            //inizio
            inizioTraccia();

            //System.out.println(tonic);
            int l = notapartenza + n.getIndex(tonica);


            for (int i = 0; i < distanze.size(); i++) {
                l = l + distanze.get(i);
                //creaNota(chan, notapartenza+intervals[i],4*i,4*(i+1));
                creaNota(chan, l, inizio, fine);
            }

            //fine
            fineTraccia();

            sequencer.setSequence(sequence);
            notapartenza = 45;
            /*
            //sequencer.setTempoInBPM(60);
            System.out.println(sequencer.getTempoInBPM());
            System.out.println(sequence.getResolution());
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void costruisciScala(int[] dist) {
        try {
            //inizio
            inizioTraccia();

            for (int i = 0; i < dist.length; i++) {
                creaNota(chan, notapartenza + dist[i], 4 * i, 4 * (i + 1));
            }

            //fine
            fineTraccia();

            sequencer.setSequence(sequence);

        /*
        //sequencer.setTempoInBPM(60);
        System.out.println(sequencer.getTempoInBPM());
        System.out.println(sequence.getResolution());
        */

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

public void costruisciBatteria(int numBattute){
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
        for(int i=0; i<t.length; i++){
            System.out.println(t[i]);
            for(int j=0; j<t[0].size(); j++){
                System.out.println(t[i].get(j));
                MidiEvent m = t[i].get(j);
                System.out.println(m.getTick());
                //System.out.println(m.getMessage().getMessage()[0]);
            }
            
        }
        for(int i=0; i<t.length; i++){
            Track bat = sequence.createTrack();
            for(int j=0; j<t[i].size(); j++)
                bat.add(t[i].get(j));
        }
    }
    
    public void costruisciBatteria(int numBattute, int stile){
        track = sequence.createTrack();
        
        mex = new ShortMessage();
        try {
            //mex.setMessage(192,instr,100);
            mex.setMessage(192, 9, 1, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        MidiEvent me = new MidiEvent(mex, 0);
        track.add(me);
    
        
        Batteria b = new Batteria(this,numBattute,stile);
        
        
        mex = new ShortMessage();
            try {
                mex.setMessage(192, 9, 1, numBattute*16);
            } catch (InvalidMidiDataException e1) {
                e1.printStackTrace();
            }
        me = new MidiEvent(mex,20);
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
            mex.setMessage(144, canale, tipo, 100);
            //mex.setMessage(144,n,volume);
            MidiEvent me = new MidiEvent(mex, inizio);
            track.add(me);

            mex = new ShortMessage();
            mex.setMessage(128, canale, tipo, 100);
            //mex.setMessage(128,n,volume);
            me = new MidiEvent(mex, fine);
            track.add(me);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inizioTraccia() {
        mex = new ShortMessage();
        try {
            mex.setMessage(192, instr, 100);
            //mex.setMessage(192, chan, 1, 100);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        MidiEvent me = new MidiEvent(mex, 0);
        track.add(me);
    }

    private void fineTraccia() {
        mex = new ShortMessage();
        try {
            mex.setMessage(192, instr, 100);
            //mex.setMessage(192, 8, 1, 1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        MidiEvent me = new MidiEvent(mex, 20);
        track.add(me);
    }

    public void creaNota(int chan, int n, int inizio, int fine) {
        mex = new ShortMessage();
        try {
            mex = new ShortMessage();
            //mex.setMessage(144+5, chan, n, 100);
            mex.setMessage(144, n, volume);
            MidiEvent me = new MidiEvent(mex, inizio);
            track.add(me);

            mex = new ShortMessage();
            //mex.setMessage(128+5, chan, n, 100);
            mex.setMessage(128, n, volume);
            me = new MidiEvent(mex, fine);
            track.add(me);

        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void start() {
        Sequence s = sequencer.getSequence();

        //inizializza();
        //costruisciTraccia(new Accordo("A","m"));
        sequencer.setTempoInBPM(bpm);
        //System.out.println(sequencer.getTempoInBPM());
        sequencer.start();
        //sequencer.startRecording();
    }

    public void stop() {
        if (sequencer.isRunning()) {
            sequencer.stop();
            inizializza();
        } else System.out.println("sono gi� fermo");
    }

    public void save() {
        File f = null;
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

    public void setChan(int chan) {
        this.chan = chan;
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

    public void costruisciMelodia(ArrayList<ArrayList<Integer>> distanze2,
                                  ArrayList<ArrayList<Integer>> durate, String tonica,
                                  int oct, int inizio, int numAccordo) {
        int in;
        int fine;

        try {
            inizioTraccia();

            int l = notapartenza + oct + n.getIndex(tonica) + 12;
            in = inizio;
            for (int i = 0; i < distanze2.get(numAccordo).size(); i++) {
                fine = in + 3 * durate.get(numAccordo).get(i);
                //l=l+distanze2.get(i);
                //creaNota(chan, notapartenza+intervals[i],4*i,4*(i+1));
                creaNota(chan, l + distanze2.get(numAccordo).get(i), in, fine);
                in = fine;
            }

            //fine
            fineTraccia();

            sequencer.setSequence(sequence);

            /*
            //sequencer.setTempoInBPM(60);
            System.out.println(sequencer.getTempoInBPM());
            System.out.println(sequence.getResolution());
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Sequencer getSequencer() {
        return sequencer;
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Prova");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Player p = new Player();
        Sequencer s = p.getSequencer();
        s.addMetaEventListener(new MetaEventListener() {

            public void meta(MetaMessage m) {
                System.out.println(m.getType());

            }

        });
        p.costruisciBatteria(2);
        f.setVisible(true);
        p.start();


    }

    public void meta(MetaMessage m) {
        if (m.getType() == 57) {
            System.out.println("ciao");
        }
    }

    public void setBPM(int bpm) {
        this.bpm = bpm;
    }

    public int getBPM() {
        return bpm;
    }
}
