package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.Chord;
import com.github.fedeoasi.music.Chords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChordsDialog extends JDialog implements ActionListener, KeyListener {
    private JComboBox<String> noteBox = new JComboBox<String>();
    private JComboBox<String> chordBox = new JComboBox<String>();
    private JComboBox nBox = new JComboBox();
    private JButton ok = new JButton("Ok");

    private MusicExpert me;

    public ChordsDialog(MusicExpert me) {
        super();
        this.me = me;
        System.out.println(isFocusable());
        setTitle("Seleziona accordo");
        setLayout(new FlowLayout());
        for (String aNote : Chords.note) {
            noteBox.addItem(aNote);
        }
        for (String chord : Chords.chords) {
            chordBox.addItem(chord);
        }
        for (int i = 0; i < 16; i++) {
            nBox.addItem(i + 1);
        }
        add(noteBox);
        add(chordBox);
        add(nBox);
        ok.addKeyListener(this);
        ok.addActionListener(this);
        add(ok);
        setSize(200, 100);
        Point p = me.getLocation();
        setLocation(p.x + me.getWidth() / 2 - 100, p.y + me.getHeight() / 2 - 50);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok)
            for (int i = 0; i < nBox.getSelectedIndex() + 1; i++) {
                String chosenNote = (String) noteBox.getSelectedItem();
                String chosenChord = (String) chordBox.getSelectedItem();
                me.getGiroAccordi().addAccordo(new Chord(chosenNote, chosenChord));
            }
        setVisible(false);
    }

    public void keyPressed(KeyEvent arg0) { }

    public void keyReleased(KeyEvent arg0) { }

    public void keyTyped(KeyEvent arg0) { }
}
