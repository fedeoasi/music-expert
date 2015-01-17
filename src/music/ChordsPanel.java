package music;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ChordsPanel extends JPanel implements ActionListener{
    static final String[] note = {"A","Bb","B","C","C#","D","Eb","E","F","F#","G","Ab"};
    static final String[] chords ={"","M7","M9","M13","m","m7","m9","m6","m13",
            "m7b5","m9b5","7","9","13","7#5","7b9","7b5"};
    JComboBox noteBox = new JComboBox();
    JComboBox chordBox = new JComboBox();
    JButton ok = new JButton("Ok");
    JTextArea ta = new JTextArea();
    Player p = null;

    Chord a = null;
    int oct=0;

    JButton play = new JButton("Play");
    JButton stop = new JButton("Stop");
    JButton save = new JButton("Save");
    JCheckBox ottava = new JCheckBox("+1 ottava");

    Notes n = new Notes();
    MusicExpert me = null;

    public ChordsPanel(MusicExpert me){
        super();
        this.me = me;
        setBorder(new EmptyBorder(5,5,5,5));
        setLayout(new BorderLayout());
        for(int i=0; i<note.length; i++)
            noteBox.addItem(note[i]);
        for(int i=0; i<chords.length; i++)
            chordBox.addItem(chords[i]);
        ok.addActionListener(this);

        JPanel north = new JPanel(new FlowLayout());
        north.add(new JLabel("Scegli l'accordo:"));
        north.add(noteBox);
        north.add(chordBox);
        north.add(ok);

        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        EmptyBorder eb = new EmptyBorder(5,5,2,5);
        sp.setBorder(new CompoundBorder(eb,new EtchedBorder()));

        JPanel south = new JPanel(new FlowLayout());
        south.add(play);
        south.add(stop);
        south.add(save);
        play.addActionListener(this);
        stop.addActionListener(this);
        save.addActionListener(this);
        south.add(ottava);
        ottava.addActionListener(this);

        add(north,BorderLayout.NORTH);
        add(sp,BorderLayout.CENTER);
        add(south,BorderLayout.SOUTH);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ok){
            a = new Chord(((String)noteBox.getSelectedItem()),
                    ((String)chordBox.getSelectedItem()));
            ArrayList<String> temp = a.getNote();
            ta.append(a.sigla + "\n");
            for(int i=0;i<temp.size();i++){
                ta.append(temp.get(i) + "  ");
                if(i==temp.size()-1) ta.append("\n\n");
            }
        }

        else if(e.getSource()==play){
            if(p==null)
            p = new Player(new Chord(((String)noteBox.getSelectedItem()),
                        ((String)chordBox.getSelectedItem())),oct,me.getInstrument());
            p.inizializza();
            p.setInstrument(me.getInstrument());
            p.setNotapartenza(p.getNotapartenza() + oct);
            p.costruisciTraccia(new Chord(((String)noteBox.getSelectedItem()),
                    ((String)chordBox.getSelectedItem())));
            p.start();
        }
        else if(e.getSource()==stop){
            if(p!=null)
                p.stop();
        }
        else if(e.getSource()==save){
            if(p!=null)
                p.save();
        }


        else if(e.getSource()==ottava){
            if(ottava.isSelected())
                oct=12;
            else oct=0;
        }
    }

    public static void main(String[] args){
        new MusicExpert();
    }


    public void generaPentagramma() {
        if (a==null) JOptionPane.showMessageDialog(this,"Nessun Accordo selezionato");
        else{
            //ArrayList<Integer> altezze = new ArrayList<Integer>();

            //for(int i=0; i<a.note.size(); i++)
                //altezze.add(57 + n.getIndice(a.tonica));

            me.disegnaAccordo(a.note, a.getAltezze());

        }

    }


    public Player getP() {
        return p;
    }


    public void setP(Player p) {
        this.p = p;
    }


    public String[] getNote() {
        return note;
    }


    public String[] getChords() {
        return chords;
    }
}
