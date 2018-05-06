package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.Playable;
import com.github.fedeoasi.music.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerGui extends JPanel implements ActionListener, ChangeListener {
    private JButton play = new JButton("Play");
    private JButton stop = new JButton("Stop");
    private JButton save = new JButton("Save");
    private JCheckBox ottava = new JCheckBox("+1 ottava");
    private JCheckBox loop = new JCheckBox("Loop");
    private JSpinner bpm = new JSpinner();
    private Player p = null;
    private Playable playable = null;

    public PlayerGui(Playable playable) {
        super(new FlowLayout());
        this.playable = playable;
        init();
    }

    private void init() {
        bpm.setModel(new SpinnerNumberModel(120, 20, 300, 1));
        bpm.addChangeListener(this);
        add(play);
        add(stop);
        add(save);
        add(bpm);
        play.addActionListener(this);
        stop.addActionListener(this);
        save.addActionListener(this);
        add(ottava);
        add(loop);
        ottava.addActionListener(this);
        loop.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            playable.play();
        } else if (e.getSource() == stop) {
            playable.stop();
        } else if (e.getSource() == save) {
            playable.save();
        } else if (e.getSource() == ottava) {
            playable.ottava(ottava.isSelected());
        } else if (e.getSource() == loop) {
            playable.loop(loop.isSelected());
        }
    }

    public void stateChanged(ChangeEvent e) {
        playable.ChangeBPM(((Integer) bpm.getValue()));
    }
}
