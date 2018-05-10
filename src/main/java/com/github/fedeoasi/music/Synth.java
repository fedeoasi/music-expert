package com.github.fedeoasi.music;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Synth extends JPanel implements ActionListener {
    final int PROGRAM = 192;
    final int NOTEON = 144;
    final int NOTEOFF = 128;

    private Sequencer sequencer;
    private Sequence sequence;
    private Synthesizer synth = null;
    private Instrument[] instruments;
    private MidiChannel[] channels;
    private MidiChannel channel;

    private Track track;

    private JButton start = new JButton("Start");
    private JButton stop = new JButton("Stop");

    public Synth() {
        super(new FlowLayout());
        setPreferredSize(new Dimension(400, 300));
    }

    public void open() {
        try {
            if (synth == null)
                synth = MidiSystem.getSynthesizer();
            if (synth == null) JOptionPane.showMessageDialog(this, "Synth non disponibile");
            else {
                synth.open();
                sequencer = MidiSystem.getSequencer();
                sequencer.open();
                sequence = new Sequence(Sequence.PPQ, 10);
                //sequencer.setSequence(sequence);
                Soundbank sb = synth.getDefaultSoundbank();
                if (sb != null) instruments = sb.getInstruments();
                for (int i = 0; i < instruments.length; i++)
                    System.out.println(instruments[i]);
                synth.loadInstrument(instruments[0]);
                channels = synth.getChannels();
                channel = channels[0];
                System.out.println(channel);
                track = sequence.createTrack();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEvent(int tipo, int nota, int tick) {
        ShortMessage m = new ShortMessage();
        try {
            m.setMessage(tipo, nota, 100);
            MidiEvent e = new MidiEvent(m, tick);
            track.add(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent arg0) { }

    public void creaNota(int instr, int altezza, int inizio, int fine) {
        createEvent(NOTEON + instr, altezza, inizio);
        createEvent(NOTEOFF + instr, altezza, fine);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("Pentagramma");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Synth s = new Synth();
        s.open();
        f.add(s);
        f.pack();
        f.setVisible(true);
        s.createEvent(s.PROGRAM, 0, 0);
        //s.createEvent(s.NOTEON+17, 50, 0);
        //s.createEvent(s.NOTEOFF+17, 50, 30);

        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 50; j++)
                s.creaNota(j, 60 + i, (100 * j) + i * 5, (j * 100) + (i + 1) * 5);
        s.createEvent(s.PROGRAM, 0, 30);
        try {
            s.sequencer.setSequence(s.sequence);
        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        s.sequencer.start();
        /*s.channel.noteOn(64,100);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        s.channel.noteOff(64, 100);
        */
    }

}
