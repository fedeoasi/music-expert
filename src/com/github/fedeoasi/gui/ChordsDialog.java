package com.github.fedeoasi.gui;

import com.github.fedeoasi.gui.ChordsPanel;
import com.github.fedeoasi.gui.MusicExpert;
import com.github.fedeoasi.music.Chord;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class ChordsDialog extends JDialog implements ActionListener, KeyListener {
    String[] note;
    String[] chords;

    JComboBox noteBox = new JComboBox();
    JComboBox chordBox = new JComboBox();
    JComboBox nBox = new JComboBox();
    JButton ok = new JButton("Ok");

    MusicExpert me = null;

    public ChordsDialog(MusicExpert me) {
        super();
        this.me = me;
        System.out.println(isFocusable());
        setTitle("Seleziona accordo");
        setLayout(new FlowLayout());
        note = ChordsPanel.note;
        chords = ChordsPanel.chords;
        for(int i=0; i<note.length; i++)
            noteBox.addItem(note[i]);
        for(int i=0; i<chords.length; i++)
            chordBox.addItem(chords[i]);
        for(int i=0; i<16; i++)
            nBox.addItem(i+1);
        add(noteBox);
        add(chordBox);
        add(nBox);
        ok.addKeyListener(this);
        ok.addActionListener(this);
        add(ok);
        setSize(200,100);
        Point p = me.getLocation();
        setLocation(p.x+me.getWidth()/2-100,p.y+me.getHeight()/2-50);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==ok)
            //me.getGiroAccordi().getAccordi().add(
                //new Accordo((String)noteBox.getSelectedItem(),(String)chordBox.getSelectedItem()));
            for(int i=0; i<nBox.getSelectedIndex()+1; i++)
            me.getGiroAccordi().addAccordo(new Chord((String)noteBox.getSelectedItem(),
                    (String)chordBox.getSelectedItem()));
            setVisible(false);
    }

    public void keyPressed(KeyEvent arg0) {
        System.out.println("ciao");

    }

    public void keyReleased(KeyEvent arg0) {
        System.out.println("ciao1");

    }

    public void keyTyped(KeyEvent arg0) {
        System.out.println("ciao2");

    }

}
