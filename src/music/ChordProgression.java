package music;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ChordProgression extends JPanel implements Playable, ActionListener {
    ArrayList<Chord> accordi = new ArrayList<Chord>();
    ArrayList<JLabel> labels = new ArrayList<JLabel>();
    int bpm;

    JButton ins = new JButton(Strings.INSERT_CHORD);
    JButton gen = new JButton(Strings.GENERATE_SOLO);
    JButton clear = new JButton("Nuovo");
    PlayerGui p = new PlayerGui(this);
    JPanel centro = new JPanel(new FlowLayout());

    MusicExpert me;

    Player player = null;

    boolean solista = false;
    boolean loop = false;
    Sequencer s;

    ArrayList<ArrayList<Integer>> aa = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> ad = new ArrayList<ArrayList<Integer>>();

    public ChordProgression(MusicExpert me) {
        super(new BorderLayout());
        this.me = me;
        JPanel north = new JPanel(new FlowLayout());
        ins.addActionListener(this);
        north.add(ins);
        gen.addActionListener(this);
        north.add(gen);
        clear.addActionListener(this);
        north.add(clear);

        centro.setBackground(Color.WHITE);

        JPanel south = new JPanel(new FlowLayout());
        south.add(p);

        add(north, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }


    public void ottava(boolean isSelected) {
        // TODO Auto-generated method stub

    }

    public void play() {
        if (player == null) {
            player = new Player(accordi.get(0));
            s = player.getSequencer();
            s.addMetaEventListener(new MetaEventListener() {

                public void meta(MetaMessage message) {
                    if (message.getType() == 57 || message.getType() == 47)
                        //System.out.println(s);
                        //System.out.println(s.isOpen());
                        if (s != null && s.isOpen() && loop)
                            play();

                }

            });
        }

        player.inizializza();
        player.setVolume(50);
        player.costruisciGiro(accordi);
        player.costruisciBatteria(accordi.size());
        if (solista == true) {
            player.setVolume(100);
            for (int i = 0; i < accordi.size(); i++)
                player.costruisciMelodia(aa, ad, accordi.get(i).tonica, 0, 16 * i, i);
        }
        player.start();


    }


    public void save() {
        player.save();

    }


    public void stop() {
        if (s.isRunning())
            s.stop();

    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ins)
            new ChordsDialog(me);
        else if (e.getSource() == gen) {
            generaSolista();
        } else if (e.getSource() == clear) {
            accordi = new ArrayList<Chord>();
            centro.removeAll();
            setVisible(false);
            setVisible(true);
        }


    }


    private void generaSolista() {
        Patterns p = new Patterns();
        //aa = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //if(player==null) JOptionPane.showMessageDialog(this,"Nessun giro selezionato");
        //else{
        int rand = new Random().nextInt(p.gradi.size());
        //System.out.println("rand: "+ rand);

        solista = true;
        for (int i = 0; i < accordi.size(); i++) {
            a = new ArrayList<Integer>();
            int[] s = accordi.get(i).getScale().get(0);
            for (int k = 1; k < s.length; k++)
                s[k] = s[k] + s[k - 1];
            rand = new Random().nextInt(p.gradi.size());
            //System.out.println("rand: "+ rand);
            for (int j = 0; j < p.gradi.get(rand).size(); j++) {
                //System.out.println(p.gradi.get(rand).get(j)-1);
                a.add(s[p.gradi.get(rand).get(j) - 1]);
                //System.out.println(a.get(j));
                //a.add();
            }
            aa.add(a);
            ad.add(p.durate.get(rand));
        }
    }


    public ArrayList<Chord> getAccordi() {
        return accordi;

    }


    public void addAccordo(Chord accordo) {

        accordi.add(accordo);
        //System.out.println(accordo.sigla);
        JLabel l = new JLabel(accordo.sigla);
        labels.add(l);
        centro.add(l);
        setVisible(false);
        setVisible(true);

    }


    public void loop(boolean isSelected) {
        loop = isSelected;
        //System.out.println(loop);
    }


    public void ChangeBPM(int bpm) {
        if (player != null)
            player.setBPM(bpm);

    }
}
