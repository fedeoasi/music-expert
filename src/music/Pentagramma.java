package music;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Pentagramma extends JPanel implements MouseListener{
    Graphics2D g2 = null;
    Note note = new Note();

    boolean accordo = false;
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayList<Integer> altezze = new ArrayList<Integer>();


    public Pentagramma() {
        super();
        setFont(new Font(Font.DIALOG,Font.BOLD,20));
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(300,200));
        setBackground(Color.WHITE);
    }

    public Pentagramma(ArrayList<String> notes,ArrayList<Integer> altezze,boolean isAccordo) {
        super();
        setFont(new Font(Font.DIALOG,Font.BOLD,18));
        accordo = isAccordo;
        this.notes = notes;
        this.altezze = altezze;
        setBorder(new EmptyBorder(5,5,5,5));
        setPreferredSize(new Dimension(300,200));
        setBackground(Color.WHITE);
    }


    public void paint(Graphics g){
        g2 = (Graphics2D) g;
        for(int i=0; i<5; i++)
            g.drawLine(0, 70+(20*i), this.getWidth(), 70+(20*i));

        if(accordo == true){
            if(notes!= null && altezze!=null)
                disegnaAccordo();
        }

        else {
            if(notes!= null && altezze!=null)
                disegnaMelodia();
        }



    }

    public void disegnaMelodia(){

      for(int i=0; i<notes.size(); i++){
          String nome = notes.get(i);
          if(note.isNaturale(notes.get(i))==false){
                  char[] c = {notes.get(i).charAt(0)};
                  String s = new String(c);
                  nome = new String(c);
              }
              int indice = note.getIndiceNaturale(nome);
            int ottava = note.ottava(notes.get(i),altezze.get(i));
                //((altezze.get(i)-45)/12);
            System.out.println(altezze.get(i)+" "+ottava);
            Nota n = new Nota(notes.get(i),altezze.get(i),20+(60*i),250-(indice*10)-(ottava*70));
            //System.out.println(g2);
            g2.fill(n.getE());
            g2.draw(n.getL());
            for(int j=0; j<n.getOpt().size(); j++)
                g2.draw(n.getOpt().get(j));
            if(n.getAlterazione()!="")
                g2.drawString(n.getAlterazione(),(float)n.getE().getMinX()- 20,
                        (float)n.getE().getMaxY()-3);
      }
    }

    public void disegnaAccordo(){
        for(int i=0; i<notes.size(); i++)
        {
            String nome = notes.get(i);
            if(note.isNaturale(notes.get(i))==false){
                      char[] c = {notes.get(i).charAt(0)};
                      //notes.set(i,new String(c));
                      nome = new String(c);
                      //System.out.println(notes.get(i)+ "  "+ s);
                  }
            int indice = note.getIndiceNaturale(nome);
            int ottava = ((altezze.get(i)-45)/12);
            Nota n = new Nota(notes.get(i),altezze.get(i),20,250-(indice*10)-(ottava*70));
            //System.out.println(g2);
            g2.fill(n.getE());
            //g2.draw(n.getL());
            for(int j=0; j<n.getOpt().size(); j++)
            g2.draw(n.getOpt().get(j));
            if(n.getAlterazione()!="")
                g2.drawString(n.getAlterazione(),(float)n.getE().getMinX()- 20,
                        (float)n.getE().getMaxY());
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame("Pentagramma");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pentagramma p = new Pentagramma();
        f.add(p);
        f.pack();
        f.setVisible(true);

        notes.add("E");  notes.add("F");  notes.add("G");  notes.add("A");
        altezze.add(64); altezze.add(65); altezze.add(67); altezze.add(69);


        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notes.remove(0); notes.remove(2);
        altezze.remove(0); altezze.remove(2);

        f.remove(p);
        p = new Pentagramma();

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

    public static void setNotes(ArrayList<String> notes) {
        Pentagramma.notes = notes;
    }

    public static void setAltezze(ArrayList<Integer> altezze) {
        Pentagramma.altezze = altezze;
    }

}
