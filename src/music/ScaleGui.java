package music;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ScaleGui extends JPanel implements ActionListener, Playable{
    Scale s = new Scale();
    Notes n = new Notes();
    String[] ris;
    String[] note = {"G","D","A","E","B","F#","C#","F","Bb","Eb","Ab",
            "Db","Gb","Cb"};
    String[] scale = {"Scala Maggiore","Scala Minore Naturale",
            "Scala Minore Armonica","Scala Minore Melodica"};

    JLabel l = new JLabel("Inserisci tonica e scala:");
    JComboBox nota = new JComboBox();
    JComboBox scala = new JComboBox();
    JButton ok = new JButton("Ok");

    JTextArea ta = new JTextArea();

    Player p = null;
    int oct=0;

    String tonica = null;
    MusicExpert me;

    public ScaleGui(MusicExpert me) {
        super(new BorderLayout());
        this.me = me;
        setBorder(new EmptyBorder(5,5,5,5));
        JPanel north = new JPanel(new FlowLayout());
        north.add(l);
        for(int i=0; i<note.length; i++)
            nota.addItem(note[i]);
        for(int i=0; i<scale.length; i++)
            scala.addItem(scale[i]);
        north.add(nota);
        north.add(scala);
        ok.addActionListener(this);
        north.add(ok);
        add(north,BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(ta);
        EmptyBorder eb = new EmptyBorder(5,5,2,5);
        sp.setBorder(new CompoundBorder(eb,new EtchedBorder()));
        add(sp,BorderLayout.CENTER);

        add(new PlayerGui(this),BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok){
            String sel = ((String)scala.getSelectedItem());
            tonica = ((String)nota.getSelectedItem());
            ta.append(sel + " di " + tonica + ":\n");
            if(sel == scale[0]) ris = s.scalaMaggiore(tonica);
            if(sel == scale[1]) ris = s.scalaMinNat(tonica);
            if(sel == scale[2]) ris = s.scalaMinArm(tonica);
            if(sel == scale[3]) ris = s.scalaMinMel(tonica);
            for(int i=0; i<ris.length; i++){
                ta.append(ris[i]+"  ");
                if(i==ris.length-1)  ta.append("\n\n");
            }

        }
    }

    public void play(){
        if(ris==null) {
            System.out.println("no input");
        }
        else{
                int[] distanze = new int[8];
        distanze[0]=0;
        for(int i=1; i<ris.length; i++)
            distanze[i]=n.distanza(ris[i-1],ris[i]);
        //risuona la tonica alla fine
        distanze[7]=n.distanza(ris[ris.length -1], ris[0]);
        if(p==null)
        p = new Player(distanze);
        p.inizializza();
        p.setInstrument(me.getInstrument());
        p.costruisciMelodia(distanze,45+n.getIndice(ris[0]),oct);
        p.start();
        }
    }

    public void stop(){
        if(p!=null)
            p.stop();
    }

    public void save(){
        if(p!=null)
            p.save();
    }

    public void ottava(boolean isSelected){
        if(isSelected)
            oct=12;
        else oct=0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new MusicExpert();

    }

    public void generaPentagramma() {
        if(ris==null) JOptionPane.showMessageDialog(this,"Nessuna scala selezionata");
        else{
            ArrayList<String> notes = new ArrayList<String>();
            ArrayList<Integer> altezze = new ArrayList<Integer>();
            for(int i=0; i<ris.length; i++)
                notes.add(ris[i]);
            notes.add(ris[0]);
            altezze.add(57 + n.getIndice(tonica));
            for(int i=1; i<notes.size(); i++)
                altezze.add(altezze.get(i-1)+n.distanza(notes.get(i-1),notes.get(i)));

            for(int i=0; i<notes.size(); i++)
                System.out.print(notes.get(i)+ "  ");
            System.out.println();
            for(int i=0; i<altezze.size(); i++)
                System.out.print(altezze.get(i)+ "  ");
            System.out.println();

            me.disegnaScala(notes, altezze);
        }




    }

    public void loop(boolean isSelected) {
        // TODO Auto-generated method stub

    }

    public void ChangeBPM(int bpm) {
        // TODO Auto-generated method stub

    }

}
