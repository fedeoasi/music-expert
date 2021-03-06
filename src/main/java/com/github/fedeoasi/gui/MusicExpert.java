package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MusicExpert extends JFrame implements ActionListener{
    private JMenuBar mb = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenu options = new JMenu("Opzioni");
    private JMenuItem generaPentagramma = new JMenuItem("Genera Pentagramma");
    private JMenuItem cambiaStrumento = new JMenuItem("Cambia Strumento");
    private JMenuItem cambiaStile = new JMenuItem("Cambia stile");
    private JMenuItem exit = new JMenuItem("exit");
    private JTabbedPane tp = new JTabbedPane();
    private ChordsPanel accordi = new ChordsPanel(this);
    private ScaleGui scale = new ScaleGui(this);
    private ModeGui modi = new ModeGui(this);
    private ChordProgressionPanel giroAccordi = new ChordProgressionPanel(this);
    private PentagramPanel pentagramma = new PentagramPanel();
    private JPanel pentaPanel = new JPanel(new BorderLayout());
    private int instr = 0;

    public MusicExpert(){
        super("Music Expert");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        exit.addActionListener(this);
        file.add(exit);
        mb.add(file);
        generaPentagramma.addActionListener(this);
        cambiaStrumento.addActionListener(this);
        cambiaStile.addActionListener(this);
        options.add(generaPentagramma);
        options.add(cambiaStrumento);
        options.add(cambiaStile);
        mb.add(options);
        add(mb, BorderLayout.NORTH);

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        tp.add("Accordi",accordi);
        tp.add("Scale", scale);
        tp.add("Modi",modi);
        tp.add("Giro Accordi", giroAccordi);
        tp.add("Pentagramma",pentagramma);
        tp.add("Pentagramma",pentaPanel);

        add(tp,BorderLayout.CENTER);

        setSize(800,600);
        Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
        //pack();
        setVisible(true);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        new MusicExpert();
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==exit)
            System.exit(0);
        else if(e.getSource()==generaPentagramma){
            if(tp.getSelectedComponent()==accordi){
                accordi.generaPentagramma();
            }
            else if(tp.getSelectedComponent()==scale){
                scale.generaPentagramma();
            }
            else if(tp.getSelectedComponent()==modi){
                modi.generaPentagramma();
            }
        }
        else if(e.getSource()==cambiaStrumento){
            new InstrumentDialog(this);
        }
        else if(e.getSource()==cambiaStile){
            new StileDialog(this);
        }
    }


    public void disegnaAccordo(List<Note> notes, List<Integer> altezze) {
        tp.remove(4);
        pentagramma = new PentagramPanel(notes, altezze,true);
        pentaPanel.removeAll();
        pentaPanel.add(pentagramma,BorderLayout.CENTER);
        tp.add("AccordoPentagramma",pentagramma);
        tp.setSelectedComponent(pentagramma);

    }

    public void disegnaScala(List<Note> notes, List<Integer> altezze){
        tp.remove(4);
        pentagramma = new PentagramPanel(notes,altezze,false);
        tp.add("ScalaPentagramma",pentagramma);
        tp.setSelectedComponent(pentagramma);
    }

    public int getInstrument(){
        return instr;
    }


    public void setInstrument(int instr) {
        this.instr = instr;

    }


    public ChordProgressionPanel getGiroAccordi() {
        return giroAccordi;

    }

    public void cambiaStile(int stile) {
        giroAccordi.setStyle(stile);
    }
}
