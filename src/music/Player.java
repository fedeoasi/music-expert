package music;

import javax.sound.midi.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Player {
    Sequencer sequencer = null;
    Track track;
    Sequence sequence = null;
    ShortMessage mex;

    Synthesizer synth;
    Instrument[] instruments;
    MidiChannel[] channels;
    MidiChannel channel;

    JPanel buttons = new JPanel();
    JPanel center = new JPanel();

    JTextField tf = new JTextField(10);
    JLabel label = new JLabel();
    JButton changeInstr = new JButton("Change");

    JButton b = new JButton("Start");
    JButton stop = new JButton("Stop");
    JButton save = new JButton("Save");

    int notapartenza = 45;
    static int[] distanze = {0, 2, 4, 0, 0, 2, 4, 0, 4, 5, 7};

    int chan = 10;
    int instr = 3;

    Notes n = new Notes();

    int volume = 100;
    int bpm = 120;

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
                Soundbank sb = synth.getDefaultSoundbank();
                if (sb != null) instruments = sb.getInstruments();
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
            sequence = new Sequence(Sequence.PPQ, 3);
            //System.out.println(sequence.getResolution());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        track = sequence.createTrack();
        //System.out.println("crea traccia :" + track.size());
    }

    public void costruisciTraccia(Chord a) {
        costruisciAccordo(a.getDistanze(), a.tonica, 0, 15);
    }

    public void costruisciGiro(ArrayList<Chord> a) {

        for (int i = 0; i < a.size(); i++)
            for (int j = 0; j < 4; j++)
                costruisciAccordo(a.get(i).getDistanze(), a.get(i).tonica, 16 * (i) + 4 * j, 16 * i + 4 * (j + 1));

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
                //creaNota(chan, notapartenza+distanze[i],4*i,4*(i+1));
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

            //System.out.println(tonica);
            int l = notapartenza + n.getIndice(tonica);


            for (int i = 0; i < distanze.size(); i++) {
                l = l + distanze.get(i);
                //creaNota(chan, notapartenza+distanze[i],4*i,4*(i+1));
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

    public void costruisciBatteria(int numBattute) {
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


        for (int i = 0; i < numBattute; i++) {
            //charleston
            for (int j = 0; j < 8; j++)
                creaNota2(9, 42, 16 * i + 2 * j, 16 * i + 2 * (j + 1));
            //cassa
            creaNota2(9, 35, 16 * i + 0, 16 * i + 2);
            creaNota2(9, 35, 16 * i + 8, 16 * i + 10);
            creaNota2(9, 35, 16 * i + 10, 16 * i + 12);
            //rullante
            creaNota2(9, 38, 16 * i + 4, 16 * i + 6);
            creaNota2(9, 38, 16 * i + 12, 16 * i + 14);
        }


        mex = new ShortMessage();
        try {
            mex.setMessage(192, 9, 1, numBattute * 16);
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

    private void creaNota2(int canale, int tipo, int inizio, int fine) {
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
            //inizio
            inizioTraccia();

            int l = notapartenza + oct + n.getIndice(tonica) + 12;
            in = inizio;
            for (int i = 0; i < distanze2.get(numAccordo).size(); i++) {
                fine = in + durate.get(numAccordo).get(i);
                //l=l+distanze2.get(i);
                //creaNota(chan, notapartenza+distanze[i],4*i,4*(i+1));
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

    protected void setBPM(int bpm) {
        this.bpm = bpm;
    }


}
