package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.Chord;
import com.github.fedeoasi.music.Chords;
import com.github.fedeoasi.music.Notes;
import com.github.fedeoasi.music.Player;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChordsPanel extends JPanel implements ActionListener {
    private JComboBox noteBox = new JComboBox();
    private JComboBox chordBox = new JComboBox();
    private JButton ok = new JButton("Ok");
    private JTextArea ta = new JTextArea();
    private Player p = null;

    private Chord a = null;
    private int oct = 0;

    private JButton play = new JButton("Play");
    private JButton stop = new JButton("Stop");
    private JButton save = new JButton("Save");
    private JCheckBox ottava = new JCheckBox("+1 ottava");

    private Notes n = new Notes();
    private MusicExpert me = null;

    public ChordsPanel(MusicExpert me) {
        super();
        this.me = me;
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());
        for (int i = 0; i < Chords.note.length; i++)
            noteBox.addItem(Chords.note[i]);
        for (int i = 0; i < Chords.chords.length; i++)
            chordBox.addItem(Chords.chords[i]);
        ok.addActionListener(this);

        JPanel north = new JPanel(new FlowLayout());
        north.add(new JLabel("Scegli l'accordo:"));
        north.add(noteBox);
        north.add(chordBox);
        north.add(ok);

        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        EmptyBorder eb = new EmptyBorder(5, 5, 2, 5);
        sp.setBorder(new CompoundBorder(eb, new EtchedBorder()));

        JPanel south = new JPanel(new FlowLayout());
        south.add(play);
        south.add(stop);
        south.add(save);
        play.addActionListener(this);
        stop.addActionListener(this);
        save.addActionListener(this);
        south.add(ottava);
        ottava.addActionListener(this);

        add(north, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            a = new Chord(((String) noteBox.getSelectedItem()),
                    ((String) chordBox.getSelectedItem()));
            ArrayList<String> temp = a.getNotes();
            ta.append(a.getSigla() + "\n");
            for (int i = 0; i < temp.size(); i++) {
                ta.append(temp.get(i) + "  ");
                if (i == temp.size() - 1) ta.append("\n\n");
            }
        } else if (e.getSource() == play) {
            if (p == null)
                p = new Player(new Chord(((String) noteBox.getSelectedItem()),
                        ((String) chordBox.getSelectedItem())), oct, me.getInstrument());
            p.inizializza();
            p.setInstrument(me.getInstrument());
            p.setNotapartenza(p.getNotapartenza() + oct);
            p.costruisciTraccia(new Chord(((String) noteBox.getSelectedItem()),
                    ((String) chordBox.getSelectedItem())));
            p.start();
        } else if (e.getSource() == stop) {
            if (p != null)
                p.stop();
        } else if (e.getSource() == save) {
            if (p != null)
                p.save();
        } else if (e.getSource() == ottava) {
            if (ottava.isSelected())
                oct = 12;
            else oct = 0;
        }
    }

    public static void main(String[] args) {
        new MusicExpert();
    }


    public void generaPentagramma() {
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Nessun Accordo selezionato");
        } else {
            me.disegnaAccordo(a.getNotes(), a.getPitches());
        }
    }

    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }
}
