package com.github.fedeoasi.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MusicExpert extends JFrame implements ActionListener{
    JMenuBar mb = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu options = new JMenu("Opzioni");
    JMenuItem generaPentagramma = new JMenuItem("Genera Pentagramma");
    JMenuItem cambiaStrumento = new JMenuItem("Cambia Strumento");
    JMenuItem cambiaStile = new JMenuItem("Cambia stile");
    JMenuItem exit = new JMenuItem("exit");
    JTabbedPane tp = new JTabbedPane();
    int indice;
    ChordsPanel accordi = new ChordsPanel(this);
    ScaleGui scale = new ScaleGui(this);
    ModeGui modi = new ModeGui(this);
    ChordProgressionPanel giroAccordi = new ChordProgressionPanel(this);
    PentagramPanel pentagramma = new PentagramPanel();
    JPanel pentaPanel = new JPanel(new BorderLayout());
    int instr = 0;

    public MusicExpert(){
        super("Music Expert");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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


    public void disegnaAccordo(ArrayList<String> notes, ArrayList<Integer> altezze) {
        tp.remove(4);
        pentagramma = new PentagramPanel(notes, altezze,true);
        pentaPanel.removeAll();
        pentaPanel.add(pentagramma,BorderLayout.CENTER);
        tp.add("AccordoPentagramma",pentagramma);
        tp.setSelectedComponent(pentagramma);

    }

    public void disegnaScala(ArrayList<String> notes, ArrayList<Integer> altezze){
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
