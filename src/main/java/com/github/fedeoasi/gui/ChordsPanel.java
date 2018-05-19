package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class ChordsPanel extends JPanel implements ActionListener {
    private JComboBox<Note> noteBox = new JComboBox<>();
    private JComboBox<ChordType> chordBox = new JComboBox<>();
    private JButton okButton = new JButton("Ok");
    private JTextArea textArea = new JTextArea();
    private Player player = null;

    private Chord selectedChord = null;
    private int oct = 0;

    private JButton playButton = new JButton("Play");
    private JButton stopButton = new JButton("Stop");
    private JButton saveButton = new JButton("Save");
    private JCheckBox ottava = new JCheckBox("+1 ottava");

    private MusicExpert me;

    public ChordsPanel(MusicExpert me) {
        super();
        this.me = me;
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());
        for (Note note: Chords.notes) {
            noteBox.addItem(note);
        }
        for (ChordType chordType: ChordType.values()) {
            chordBox.addItem(chordType);
        }
        okButton.addActionListener(this);

        JPanel north = new JPanel(new FlowLayout());
        north.add(new JLabel("Scegli l'accordo:"));
        north.add(noteBox);
        north.add(chordBox);
        north.add(okButton);

        textArea.setEditable(false);
        JScrollPane sp = new JScrollPane(textArea);
        EmptyBorder eb = new EmptyBorder(5, 5, 2, 5);
        sp.setBorder(new CompoundBorder(eb, new EtchedBorder()));

        JPanel south = new JPanel(new FlowLayout());
        south.add(playButton);
        south.add(stopButton);
        south.add(saveButton);
        playButton.addActionListener(this);
        stopButton.addActionListener(this);
        saveButton.addActionListener(this);
        south.add(ottava);
        ottava.addActionListener(this);

        add(north, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            selectedChord = selectedChord();
            ArrayList<Note> temp = selectedChord.getNotes();
            textArea.append(selectedChord.getSigla() + "\n");
            for (int i = 0; i < temp.size(); i++) {
                textArea.append(temp.get(i) + "  ");
                if (i == temp.size() - 1) {
                    textArea.append("\n\n");
                }
            }
        } else if (e.getSource() == playButton) {
            if (player == null) {
                player = new Player();
            }

            player.inizializza();
            player.setInstrument(me.getInstrument());
            player.setNotapartenza(player.getNotapartenza() + oct);
            Chord chord = selectedChord();
            player.costruisciTraccia(chord);
            player.start();
        } else if (e.getSource() == stopButton) {
            if (player != null) {
                player.stop();
            }
        } else if (e.getSource() == saveButton) {
            if (player != null) {
                player.save();
            }
        } else if (e.getSource() == ottava) {
            if (ottava.isSelected())
                oct = 12;
            else oct = 0;
        }
    }

    private Chord selectedChord() {
        ChordType chordType = (ChordType) Objects.requireNonNull(chordBox.getSelectedItem());
        return new Chord((Note) noteBox.getSelectedItem(), chordType);
    }

    public void generaPentagramma() {
        if (selectedChord == null) {
            JOptionPane.showMessageDialog(this, "Nessun Accordo selezionato");
        } else {
            me.disegnaAccordo(selectedChord.getNotes(), selectedChord.getPitches());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
