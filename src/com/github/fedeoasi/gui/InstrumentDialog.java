package com.github.fedeoasi.gui;

import com.github.fedeoasi.gui.MusicExpert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentDialog extends JDialog implements ActionListener {
    String[] instruments = {"Piano", "Bright Piano", "Electric Grand", "Honky Tonk Piano",
            "Electric Piano 1", "Electric Piano 2", "Harpsichord", "Clavinet", "Celesta",
            "Glockenspiel", "Music Box", "Vibraphone", "Marimba", "Xylophone", "Tubular Bell"
            , "Dulcimer", "Hammond Organ", "Perc Organ", "Rock Organ", "Church Organ", "Reed Organ"
            , "Accordion", "Harmonica", "Tango Accordion", "Nylon Str Guitar", "Steel String Guitar"
            , "Jazz Electric Gtr", "Clean Guitar", "Muted Guitar", "Overdrive Guitar", "Distortion Guitar",
            "Guitar Harmonics"};
    JComboBox instr = new JComboBox();
    JButton ok = new JButton("ok");
    MusicExpert me;

    public InstrumentDialog(MusicExpert me) {
        super();
        setLayout(new FlowLayout());
        setTitle("Scegli lo strumento");
        ok.addActionListener(this);
        for (int i = 0; i < instruments.length; i++)
            instr.addItem(instruments[i]);
        add(instr);
        add(ok);
        this.me = me;
        setSize(200, 100);
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(d.width/2-100,d.height/2-50);
        Point p = me.getLocation();
        setLocation(p.x + me.getWidth() / 2 - 100, p.y + me.getHeight() / 2 - 50);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            me.setInstrument(instr.getSelectedIndex());
            setVisible(false);
        }
    }

}
