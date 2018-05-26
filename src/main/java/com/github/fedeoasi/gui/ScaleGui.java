package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ScaleGui extends JPanel implements ActionListener, Playable {
    private Note[] ris;
    private Note[] notes = {Note.G, Note.D, Note.A, Note.E, Note.B, Note.FSharp, Note.CSharp, Note.F, Note.BFlat, Note.EFlat, Note.AFlat,
            Note.DFlat , Note.GFlat , Note.CFlat };
    private String[] scale = {"Scala Maggiore", "Scala Minore Naturale",
            "Scala Minore Armonica", "Scala Minore Melodica"};

    private JLabel l = new JLabel("Inserisci tonic e scala:");
    private JComboBox<Note> nota = new JComboBox<>();
    private JComboBox<String> scala = new JComboBox<>();
    private JButton ok = new JButton("Ok");

    private JTextArea ta = new JTextArea();

    private Player p = null;
    private int oct = 0;

    private Note tonica = null;
    private MusicExpert me;

    public ScaleGui(MusicExpert me) {
        super(new BorderLayout());
        this.me = me;
        setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel north = new JPanel(new FlowLayout());
        north.add(l);
        for (Note note : notes) {
            nota.addItem(note);
        }
        for (String aScale : scale) {
            scala.addItem(aScale);
        }
        north.add(nota);
        north.add(scala);
        ok.addActionListener(this);
        north.add(ok);
        add(north, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(ta);
        EmptyBorder eb = new EmptyBorder(5, 5, 2, 5);
        sp.setBorder(new CompoundBorder(eb, new EtchedBorder()));
        add(sp, BorderLayout.CENTER);

        add(new PlayerGui(this), BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            String sel = ((String) scala.getSelectedItem());
            tonica = (Note) nota.getSelectedItem();
            ta.append(sel + " di " + tonica + ":\n");
            if (sel.equals(scale[0])) ris = Scales.scalaMaggiore(tonica);
            if (sel.equals(scale[1])) ris = Scales.scalaMinNat(tonica);
            if (sel.equals(scale[2])) ris = Scales.scalaMinArm(tonica);
            if (sel.equals(scale[3])) ris = Scales.scalaMinMel(tonica);
            for (int i = 0; i < ris.length; i++) {
                ta.append(ris[i] + "  ");
                if (i == ris.length - 1) ta.append("\n\n");
            }

        }
    }

    public void play() {
        if (ris == null) {
            System.out.println("no input");
        } else {
            int[] distanze = new int[8];
            distanze[0] = 0;
            for (int i = 1; i < ris.length; i++)
                distanze[i] = Notes.distance(ris[i - 1], ris[i]);
            //risuona la tonic alla fine
            distanze[7] = Notes.distance(ris[ris.length - 1], ris[0]);
            if (p == null) {
                p = new Player();
            }
            p.inizializza();
            p.setInstrument(me.getInstrument());
            p.costruisciMelodia(distanze, 45 + Notes.getIndex(ris[0]), oct);
            p.start();
        }
    }

    public void stop() {
        if (p != null)
            p.stop();
    }

    public void save() {
        if (p != null)
            p.save();
    }

    public void ottava(boolean isSelected) {
        if (isSelected)
            oct = 12;
        else oct = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new MusicExpert();

    }

    public void generaPentagramma() {
        if (ris == null) JOptionPane.showMessageDialog(this, "Nessuna scala selezionata");
        else {
            List<Note> notes = new ArrayList<>();
            List<Integer> altezze = new ArrayList<>();
            Collections.addAll(notes, ris);
            notes.add(ris[0]);
            altezze.add(57 + Notes.getIndex(tonica));
            for (int i = 1; i < notes.size(); i++)
                altezze.add(altezze.get(i - 1) + Notes.distance(notes.get(i - 1), notes.get(i)));

            for (Note note : notes) {
                System.out.print(note + "  ");
            }
            System.out.println();
            for (Integer anAltezze : altezze) {
                System.out.print(anAltezze + "  ");
            }
            System.out.println();

            me.disegnaScala(notes, altezze);
        }


    }

    public void loop(boolean isSelected) {
        // TODO Auto-generated method stub

    }

    public void ChangeBPM(int bpm) {
        // TODO Auto-generated method stub

    }

}
