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
import javax.swing.border.EmptyBorder;

public class ModiGui extends JPanel implements ActionListener, Playable {
    String[] note = {"C","G","D","A","E","B","F#","C#","F","Bb","Eb","Ab",
            "Db","Gb","Cb"};
    String[] scale = {"Scala Maggiore","Scala Minore Armonica",
            "Scala Minore Melodica"};
    String[] modiMaggiori = {"Ionica","Dorica","Frigia","Lidia","Misolidia",
            "Eolia","Locria"};
    String[] modiMinArm = {"Min Armonica","Locria #6","Ionica #5","Dorica #4",
            "Frigia beq3","Lidia #2","Superlocria b7"};
    String[] modiMinMel = {"Jazz Minor","Dorica b2","Lidia aumentata","Lidia di dominante",
            "Misolidia b6","Locria beq2","SuperLocria"};

    JLabel l = new JLabel("Inserisci tonica, scala e modo: ");
    JComboBox nota = new JComboBox();
    JComboBox gen = new JComboBox();
    JComboBox modo = new JComboBox();
    JButton ok = new JButton("Ok");

    JTextArea ta = new JTextArea();

    String tonica;

    Modo m = null;
    int[] generatrice;
    Scale s = new Scale();
    Note n = new Note();
    Player p = null;

    int oct=0;
    MusicExpert me = null;

    public ModiGui(MusicExpert me){
        super(new BorderLayout());
        setBorder(new EmptyBorder(5,5,5,5));
        this.me=me;

        for(int i=0; i<note.length; i++){
            nota.addItem(note[i]);
        }
        for(int i=0; i<scale.length; i++){
            gen.addItem(scale[i]);
        }
        for(int i=0; i<modiMaggiori.length; i++){
            modo.addItem(modiMaggiori[i]);
        }
        gen.addActionListener(this);

        JPanel north = new JPanel(new FlowLayout());
        north.add(l);
        north.add(nota);
        north.add(gen);
        north.add(modo);
        ok.addActionListener(this);
        north.add(ok);

        add(north, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(ta);
        add(sp,BorderLayout.CENTER);

        PlayerGui p = new PlayerGui(this);
        add(p,BorderLayout.SOUTH);

        generatrice = s.scalaMaggiore;
    }



    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok){
            String tonica = ((String)nota.getSelectedItem());
            String nome = ((String)modo.getSelectedItem());
            Integer partenza = modo.getSelectedIndex()+1;
            m = new Modo(tonica,nome,generatrice,partenza);
            String[] temp = m.getNote();
            ta.append(nome + " di " + tonica + ":\n");
            for(int i=0; i<temp.length; i++)
                ta.append(temp[i]+"  ");
            ta.append("\n\n");
        }

        if(e.getSource()==gen){
            String sel = ((String)gen.getSelectedItem());
            //System.out.println(sel);
            modo.removeAllItems();
            if(sel=="Scala Maggiore")
                for(int i=0; i<modiMaggiori.length; i++){
                    modo.addItem(modiMaggiori[i]);
                    generatrice = s.scalaMaggiore;
                }
            else if(sel=="Scala Minore Armonica")
                for(int i=0; i<modiMinArm.length; i++){
                    modo.addItem(modiMinArm[i]);
                    generatrice = s.scalaMinArm;
                }

            else if(sel=="Scala Minore Melodica")
                for(int i=0; i<modiMinMel.length; i++){
                    modo.addItem(modiMinMel[i]);
                    generatrice = s.scalaMinMel;
                }

        }

    }

    public void ottava(boolean isSelected) {
        if(isSelected)
            oct=12;
        else oct=0;
    }

    public void play() {
        String[] ris = null;
        if(m!=null){
            ris = m.getNote();
        }
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
        p.setInstrument(me.instr);
        p.costruisciMelodia(distanze,45+n.getIndice(ris[0]),oct);
        p.start();
        }

    }

    public void save() {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }



    public void generaPentagramma() {
        if(m==null) JOptionPane.showMessageDialog(this, "Nessun modo selezionato");
        else{
            ArrayList<String> notes = new ArrayList<String>();
            ArrayList<Integer> altezze = new ArrayList<Integer>();
            for(int i=0; i<m.getNote().length; i++)
                notes.add(m.getNote()[i]);
            notes.add(m.getNote()[0]);
            altezze.add(57 + n.getIndice(m.getTonica()));
            for(int i=1; i<notes.size(); i++)
                altezze.add(altezze.get(i-1)+n.distanza(notes.get(i-1),notes.get(i)));

            for(int i=0; i<notes.size(); i++)
                System.out.print(notes.get(i)+ "  ");
            System.out.println();
            for(int i=0; i<notes.size(); i++)
                System.out.print(notes.get(i)+ "  ");
            System.out.println();

            me.disegnaScala(notes, altezze);
        }
    }



    public void setInstrument(int instr) {
        p.setInstrument(instr);
    }



    public void loop(boolean isSelected) {
        // TODO Auto-generated method stub

    }



    public void ChangeBPM(int bpm) {
        // TODO Auto-generated method stub

    }

}
