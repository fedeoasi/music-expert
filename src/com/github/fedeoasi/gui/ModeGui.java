package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ModeGui extends JPanel implements ActionListener, Playable {
    private String[] note = {"C", "G", "D", "A", "E", "B", "F#", "C#", "F", "Bb", "Eb", "Ab",
            "Db", "Gb", "Cb"};
    private String[] scale = {"Scala Maggiore", "Scala Minore Armonica",
            "Scala Minore Melodica"};
    private String[] modiMaggiori = {"Ionica", "Dorica", "Frigia", "Lidia", "Misolidia",
            "Eolia", "Locria"};
    private String[] modiMinArm = {"Min Armonica", "Locria #6", "Ionica #5", "Dorica #4",
            "Frigia beq3", "Lidia #2", "Superlocria b7"};
    private String[] modiMinMel = {"Jazz Minor", "Dorica b2", "Lidia aumentata", "Lidia di dominante",
            "Misolidia b6", "Locria beq2", "SuperLocria"};

    private JLabel l = new JLabel("Inserisci tonica, scala e modo: ");
    private JComboBox nota = new JComboBox();
    private JComboBox gen = new JComboBox();
    private JComboBox modo = new JComboBox();
    private JButton ok = new JButton("Ok");

    private JTextArea ta = new JTextArea();

    private String tonica;

    private Mode m = null;
    private int[] generatrice;
    private Scales s = new Scales();
    private Notes n = new Notes();
    private Player p = null;

    private int oct = 0;
    private MusicExpert me = null;

    public ModeGui(MusicExpert me) {
        super(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        this.me = me;

        for (int i = 0; i < note.length; i++) {
            nota.addItem(note[i]);
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
        north.add(nota);
        north.add(gen);
        north.add(modo);
        ok.addActionListener(this);
        north.add(ok);

        add(north, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(ta);
        add(sp, BorderLayout.CENTER);

        PlayerGui p = new PlayerGui(this);
        add(p, BorderLayout.SOUTH);

        generatrice = s.scalaMaggiore;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            String tonica = ((String) nota.getSelectedItem());
            String nome = ((String) modo.getSelectedItem());
            Integer partenza = modo.getSelectedIndex() + 1;
            m = new Mode(tonica, nome, generatrice, partenza);
            String[] temp = m.getNote();
            ta.append(nome + " di " + tonica + ":\n");
            for (int i = 0; i < temp.length; i++)
                ta.append(temp[i] + "  ");
            ta.append("\n\n");
        }

        if (e.getSource() == gen) {
            String sel = ((String) gen.getSelectedItem());
            //System.out.println(sel);
            modo.removeAllItems();
            if (sel == "Scala Maggiore")
                for (int i = 0; i < modiMaggiori.length; i++) {
                    modo.addItem(modiMaggiori[i]);
                    generatrice = s.scalaMaggiore;
                }
            else if (sel == "Scala Minore Armonica")
                for (int i = 0; i < modiMinArm.length; i++) {
                    modo.addItem(modiMinArm[i]);
                    generatrice = s.scalaMinArm;
                }

            else if (sel == "Scala Minore Melodica")
                for (int i = 0; i < modiMinMel.length; i++) {
                    modo.addItem(modiMinMel[i]);
                    generatrice = s.scalaMinMel;
                }

        }

    }

    public void ottava(boolean isSelected) {
        if (isSelected)
            oct = 12;
        else oct = 0;
    }

    public void play() {
        String[] ris = null;
        if (m != null) {
            ris = m.getNote();
        }
        if (ris == null) {
            System.out.println("no input");
        } else {
            int[] distanze = new int[8];
            distanze[0] = 0;
            for (int i = 1; i < ris.length; i++)
                distanze[i] = n.distance(ris[i - 1], ris[i]);
            //risuona la tonica alla fine
            distanze[7] = n.distance(ris[ris.length - 1], ris[0]);
            if (p == null)
                p = new Player(distanze);
            p.inizializza();
            p.setInstrument(me.getInstrument());
            p.costruisciMelodia(distanze, 45 + n.getIndex(ris[0]), oct);
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
            ArrayList<String> notes = new ArrayList<String>();
            ArrayList<Integer> altezze = new ArrayList<Integer>();
            for (int i = 0; i < m.getNote().length; i++)
                notes.add(m.getNote()[i]);
            notes.add(m.getNote()[0]);
            altezze.add(57 + n.getIndex(m.getTonica()));
            for (int i = 1; i < notes.size(); i++)
                altezze.add(altezze.get(i - 1) + n.distance(notes.get(i - 1), notes.get(i)));

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
