package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ModeGui extends JPanel implements ActionListener, Playable {
    private Note[] notes = {Note.C, Note.G, Note.D, Note.A, Note.E, Note.B, Note.FSharp, Note.CSharp, Note.F, Note.BFlat, Note.EFlat, Note.AFlat,
            Note.DFlat, Note.GFlat, Note.CFlat};
    private String[] scale = {"Scala Maggiore", "Scala Minore Armonica",
            "Scala Minore Melodica"};
    private String[] modiMaggiori = {"Ionica", "Dorica", "Frigia", "Lidia", "Misolidia",
            "Eolia", "Locria"};
    private String[] modiMinArm = {"Min Armonica", "Locria #6", "Ionica #5", "Dorica #4",
            "Frigia beq3", "Lidia #2", "Superlocria b7"};
    private String[] modiMinMel = {"Jazz Minor", "Dorica b2", "Lidia aumentata", "Lidia di dominante",
            "Misolidia b6", "Locria beq2", "SuperLocria"};

    private JLabel l = new JLabel("Inserisci tonica, scala e modo: ");
    private JComboBox<Note> noteBox = new JComboBox<>();
    private JComboBox<String> gen = new JComboBox<>();
    private JComboBox<String> modo = new JComboBox<>();
    private JButton ok = new JButton("Ok");

    private JTextArea ta = new JTextArea();

    private Mode m = null;
    private int[] generatrice;
    private Player p = null;

    private int oct = 0;
    private MusicExpert me;

    public ModeGui(MusicExpert me) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        this.me = me;

        for (int i = 0; i < notes.length; i++) {
            noteBox.addItem(notes[i]);
        }
        for (int i = 0; i < scale.length; i++) {
            gen.addItem(scale[i]);
        }
        for (int i = 0; i < modiMaggiori.length; i++) {
            modo.addItem(modiMaggiori[i]);
        }
        gen.addActionListener(this);

        JPanel north = new JPanel(new FlowLayout());
        north.add(l);
        north.add(noteBox);
        north.add(gen);
        north.add(modo);
        ok.addActionListener(this);
        north.add(ok);

        add(north, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(ta);
        add(sp, BorderLayout.CENTER);

        PlayerGui p = new PlayerGui(this);
        add(p, BorderLayout.SOUTH);

        generatrice = Scales.scalaMaggiore;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            Note tonica = ((Note) noteBox.getSelectedItem());
            String nome = ((String) modo.getSelectedItem());
            Integer partenza = modo.getSelectedIndex() + 1;
            m = new Mode(tonica, nome, generatrice, partenza);
            Note[] temp = m.getNotes();
            ta.append(nome + " di " + tonica + ":\n");
            for (int i = 0; i < temp.length; i++)
                ta.append(temp[i] + "  ");
            ta.append("\n\n");
        }

        if (e.getSource() == gen) {
            String sel = ((String) gen.getSelectedItem());
            //System.out.println(sel);
            modo.removeAllItems();
            if (sel.equals("Scala Maggiore"))
                for (int i = 0; i < modiMaggiori.length; i++) {
                    modo.addItem(modiMaggiori[i]);
                    generatrice = Scales.scalaMaggiore;
                }
            else if (sel.equals("Scala Minore Armonica"))
                for (int i = 0; i < modiMinArm.length; i++) {
                    modo.addItem(modiMinArm[i]);
                    generatrice = Scales.scalaMinArm;
                }

            else if (sel.equals("Scala Minore Melodica"))
                for (int i = 0; i < modiMinMel.length; i++) {
                    modo.addItem(modiMinMel[i]);
                    generatrice = Scales.scalaMinMel;
                }

        }

    }

    public void ottava(boolean isSelected) {
        if (isSelected)
            oct = 12;
        else oct = 0;
    }

    public void play() {
        Note[] modeNotes = null;
        if (m != null) {
            modeNotes = m.getNotes();
        }
        if (modeNotes == null) {
            System.out.println("no input");
        } else {
            int[] distanze = new int[8];
            distanze[0] = 0;
            for (int i = 1; i < modeNotes.length; i++) {
                distanze[i] = Notes.distance(modeNotes[i - 1], modeNotes[i]);
            }
            //risuona la tonica alla fine
            distanze[7] = Notes.distance(modeNotes[modeNotes.length - 1], modeNotes[0]);
            if (p == null) {
                p = new Player();
            }
            p.inizializza();
            p.setInstrument(me.getInstrument());
            p.costruisciMelodia(distanze, 45 + Notes.getIndex(modeNotes[0]), oct);
            p.start();
        }

    }

    public void save() {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }


    public void generaPentagramma() {
        if (m == null) JOptionPane.showMessageDialog(this, "Nessun modo selezionato");
        else {
            List<Note> notes = new ArrayList<>();
            List<Integer> altezze = new ArrayList<Integer>();
            notes.addAll(Arrays.asList(m.getNotes()));
            notes.add(m.getNotes()[0]);
            altezze.add(57 + Notes.getIndex(m.getTonic()));
            for (int i = 1; i < notes.size(); i++)
                altezze.add(altezze.get(i - 1) + Notes.distance(notes.get(i - 1), notes.get(i)));

            for (int i = 0; i < notes.size(); i++)
                System.out.print(notes.get(i) + "  ");
            System.out.println();
            for (int i = 0; i < notes.size(); i++)
                System.out.print(notes.get(i) + "  ");
            System.out.println();

            me.disegnaScala(notes, altezze);
        }
    }

    public void setInstrument(int instr) {
        p.setInstrument(instr);
    }

    public void loop(boolean isSelected) {
        // TODO Auto-generated method stub

    }

    public void ChangeBPM(int bpm) {
        // TODO Auto-generated method stub

    }
}
