package com.github.fedeoasi.gui;

import com.github.fedeoasi.music.Note;
import com.github.fedeoasi.music.NoteVisualization;
import com.github.fedeoasi.music.Notes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PentagramPanel extends JPanel implements MouseListener {
    private Graphics2D g2 = null;
    private Notes note = new Notes();

    private boolean accordo = false;
    //TODO revisit these static fields
    private static List<Note> notes;
    private static List<Integer> altezze = new ArrayList<Integer>();

    public PentagramPanel() {
        super();
        setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(300, 200));
        setBackground(Color.WHITE);
    }

    public PentagramPanel(List<Note> notes, List<Integer> altezze, boolean isAccordo) {
        super();
        setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        accordo = isAccordo;
        setNotes(notes);
        this.altezze = altezze;
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(300, 200));
        setBackground(Color.WHITE);
    }


    public void paint(Graphics g) {
        g2 = (Graphics2D) g;
        for (int i = 0; i < 5; i++)
            g.drawLine(0, 70 + (20 * i), this.getWidth(), 70 + (20 * i));

        if (accordo) {
            if (notes != null && altezze != null)
                disegnaAccordo();
        } else {
            if (notes != null && altezze != null)
                disegnaMelodia();
        }


    }

    public void disegnaMelodia() {

        for (int i = 0; i < notes.size(); i++) {
            Note n = notes.get(i);
            if (!note.isNatural(n)) {
                n = n.getNaturalNoteAsNote();
            }
            int indice = note.getIndexInNaturalScale(n);
            int ottava = note.ottava(notes.get(i), altezze.get(i));
            //((altezze.get(i)-45)/12);
            System.out.println(altezze.get(i) + " " + ottava);
            NoteVisualization nv = new NoteVisualization(notes.get(i), altezze.get(i), 20 + (60 * i), 250 - (indice * 10) - (ottava * 70));
            //System.out.println(g2);
            g2.fill(nv.getE());
            g2.draw(nv.getL());
            for (int j = 0; j < nv.getOpt().size(); j++)
                g2.draw(nv.getOpt().get(j));
            if (!nv.getAccidental().equals(""))
                g2.drawString(nv.getAccidental(), (float) nv.getE().getMinX() - 20,
                        (float) nv.getE().getMaxY() - 3);
        }
    }

    public void disegnaAccordo() {
        for (int i = 0; i < notes.size(); i++) {
            Note n = notes.get(i);
            if (!note.isNatural(n)) {
                n = n.getNaturalNoteAsNote();
            }
            int indice = note.getIndexInNaturalScale(n);
            int ottava = ((altezze.get(i) - 45) / 12);
            NoteVisualization nv = new NoteVisualization(notes.get(i), altezze.get(i), 20, 250 - (indice * 10) - (ottava * 70));
            //System.out.println(g2);
            g2.fill(nv.getE());
            //g2.draw(n.getL());
            for (int j = 0; j < nv.getOpt().size(); j++)
                g2.draw(nv.getOpt().get(j));
            if (!nv.getAccidental().equals(""))
                g2.drawString(nv.getAccidental(), (float) nv.getE().getMinX() - 20,
                        (float) nv.getE().getMaxY());
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("Pentagramma");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PentagramPanel p = new PentagramPanel();
        f.add(p);
        f.pack();
        f.setVisible(true);

        notes.add(Note.E);
        notes.add(Note.F);
        notes.add(Note.G);
        notes.add(Note.A);
        altezze.add(64);
        altezze.add(65);
        altezze.add(67);
        altezze.add(69);


        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notes.remove(0);
        notes.remove(2);
        altezze.remove(0);
        altezze.remove(2);

        f.remove(p);
        p = new PentagramPanel();

        f.add(p);
        f.pack();
        f.repaint();

    }


    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }


    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    public boolean isAccordo() {
        return accordo;
    }

    public void setAccordo(boolean accordo) {
        this.accordo = accordo;
    }

    public static void setNotes(List<Note> notes) {
        PentagramPanel.notes = notes;
    }

    public static void setAltezze(List<Integer> altezze) {
        PentagramPanel.altezze = altezze;
    }

}
