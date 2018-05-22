package com.github.fedeoasi.gui;

import com.github.fedeoasi.lang.Strings;
import com.github.fedeoasi.music.*;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class ChordProgressionPanel extends JPanel implements Playable, ActionListener {
    private Random random = new Random();
    private List<Chord> accordi = new ArrayList<>();
    private List<JLabel> labels = new ArrayList<>();
    private List<JPanel> panels = new ArrayList<>();
    private int bpm;

    private JButton ins = new JButton(Strings.INSERT_CHORD);
    private JButton gen = new JButton(Strings.GENERATE_SOLO);
    private JButton clear = new JButton("Nuovo");
    private JButton carica = new JButton("Carica");
    private PlayerGui p = new PlayerGui(this);
    private JPanel centro = new JPanel();
    private GridBagConstraints c = new GridBagConstraints();

    private MusicExpert me;

    private Player player = null;

    private boolean hasSolo = false;
    private boolean loop = false;
    private Sequencer s;

    private List<List<Integer>> soloIntervals = new ArrayList<>();
    private List<List<Integer>> soloDurations = new ArrayList<>();

    private int style = 1;

    private Thread t = null;
    private Illumina l = null;

    private ChordScaleFinder scaleFinder = new ChordScaleFinder();

    public ChordProgressionPanel(MusicExpert me) {
        super(new BorderLayout());
        this.me = me;

        centro.setLayout(new GridBagLayout());
        JPanel north = new JPanel(new FlowLayout());
        ins.addActionListener(this);
        north.add(ins);
        gen.addActionListener(this);
        north.add(gen);
        clear.addActionListener(this);
        north.add(clear);
        carica.addActionListener(this);
        north.add(carica);

        centro.setBackground(Color.WHITE);

        JPanel south = new JPanel(new FlowLayout());
        south.add(p);

        add(north, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        inizializzaGriglia();
    }

    private void inizializzaGriglia() {
        for (int i = 0; i < 20; i++) {
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = i % 4;
            c.ipady = 150;
            c.gridy = i / 4;
            c.weightx = 1;
            c.weighty = 1;
            JPanel p = new JPanel(new BorderLayout());
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            p.setLayout(new FlowLayout(FlowLayout.CENTER));
            p.setBackground(Color.WHITE);
            JLabel l = new JLabel("" + (i + 1));
            labels.add(l);
            l.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
            p.add(l, BorderLayout.SOUTH);
            l.setVisible(false);
            centro.add(p, c);
            panels.add(p);
        }
    }

    public void ottava(boolean isSelected) { }

    public void play() {
        if (accordi.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nessun accordo selezionato");
        } else {
            if (player == null) {
                player = new Player();
                s = player.getSequencer();
                s.addMetaEventListener(new MetaEventListener() {

                    public void meta(MetaMessage message) {
                        if (message.getType() == 57 || message.getType() == 47) {
                            if (s != null && s.isOpen() && loop) {
                                play();
                            }
                        }
                    }
                });
            }

            player.inizializza();
            player.setVolume(50);
            player.costruisciGiro(accordi);
            player.costruisciBatteria(accordi.size(), style);
            if (hasSolo) {
                player.setVolume(100);
                for (int i = 0; i < accordi.size(); i++)
                    player.costruisciMelodia(soloIntervals, soloDurations, accordi.get(i).getTonic(), 0, 48 * i, i);
            }
            player.start();
            if (l != null) {
                l.setPlaying(false);
                spegniAccordi();
            }
            l = new Illumina(accordi.size(), getBPM(), this);
            t = new Thread(l);
            t.start();
        }
    }


    public void save() {
        if (player == null) {
            JOptionPane.showMessageDialog(this, "Devi prima fare play almeno una volta");
        }
        else {
            player.save();
        }
    }


    public void stop() {
        if (s.isRunning()) {
            s.stop();
            l.setPlaying(false);
            spegniAccordi();
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ins)
            new ChordsDialog(me);
        else if (e.getSource() == gen) {
            generaSolista();
        } else if (e.getSource() == clear) {
            clear();
        } else if (e.getSource() == carica) {
            new LoadDialog(me, this);
        }
    }

    public void clear() {
        accordi = new ArrayList<>();
        soloIntervals = new ArrayList<>();
        soloDurations = new ArrayList<>();
        labels = new ArrayList<>();
        panels = new ArrayList<>();
        centro.removeAll();
        inizializzaGriglia();
        setVisible(false);
        setVisible(true);
    }

    private void generaSolista() {
        Patterns p = new Patterns();
        hasSolo = true;

        for (int i = 0; i < accordi.size(); i++) {
            List<Integer> a = new ArrayList<>();
            //TODO choose scale based on prior and next chords

            Chord currentChord = accordi.get(i);

            Optional<Chord> priorChord = Optional.empty();
            boolean foundPriorChord = false;
            int t = i - 1;
            while (!foundPriorChord && t >= 0) {
                if (!accordi.get(t).getSigla().equals(currentChord.getSigla())) {
                    priorChord = Optional.of(accordi.get(t));
                    foundPriorChord = true;
                }
                t--;
            }

            Optional<Chord> nextChord = Optional.empty();
            int u = i + 1;
            boolean foundNextChord = false;
            while (!foundNextChord && u <= accordi.size() - 1) {
                if (!accordi.get(u).getSigla().equals(currentChord.getSigla())) {
                    nextChord = Optional.of(accordi.get(u));
                    foundNextChord = true;
                }
                u++;
            }

            System.out.println(priorChord + " " + currentChord + " " + nextChord);
            List<Scale> scales = scaleFinder.findScales(priorChord, currentChord, nextChord);
            Scale firstScale = scales.get(0);
            System.out.println(firstScale);
            int[] s = firstScale.getIntervals();

            for (int k = 1; k < s.length; k++) {
                s[k] = s[k] + s[k - 1];
            }
            int rand = random.nextInt(p.getGradi().size());
            //These should be a single object
            List<Integer> patternGradi = p.getGradi().get(rand);
            List<Integer> patternDurations = p.getDurate().get(rand);
            for (int j = 0; j < patternGradi.size(); j++) {
                a.add(s[patternGradi.get(j) - 1]);
            }
            soloIntervals.add(a);
            soloDurations.add(patternDurations);
        }
    }


    public List<Chord> getAccordi() {
        return accordi;

    }

    public void addAccordo(Chord accordo) {
        accordi.add(accordo);
        labels.get(accordi.size() - 1).setVisible(true);
        labels.get(accordi.size() - 1).setText(accordo.getSigla());
        panels.get(accordi.size() - 1).setBackground(Color.RED);
    }

    public void loop(boolean isSelected) {
        loop = isSelected;
        //System.out.println(loop);
    }

    public int getBPM() {
        if (player != null)
            return player.getBPM();
        return 120;
    }

    public void ChangeBPM(int bpm) {
        if (player != null)
            player.setBPM(bpm);
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void spegniAccordi() {
        for (int i = 0; i < accordi.size(); i++)
            panels.get(i).setBackground(Color.RED);
    }

    public void illuminaAccordo(int index) {
        spegniAccordi();
        panels.get(index).setBackground(Color.BLUE);
    }

    public void caricaBrano(String brano) {
        File f = new File("Canzoni/" + brano);
        Scanner scanner = null;
        try {
            scanner = new Scanner(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String nome = "";

        if (scanner != null) {
            scanner.useDelimiter(",");
            System.out.println(f);
            int stile = scanner.nextInt();
            bpm = scanner.nextInt();
            ChangeBPM(bpm);
            setStyle(stile);
            while (scanner.hasNext()) {
                Note tonica = Note.fromName(scanner.next());
                if (scanner.hasNext()) {
                    nome = scanner.next();
                } else break;
                int n = 1;
                if (scanner.hasNextInt())
                    n = scanner.nextInt();
                else break;
                for (int i = 0; i < n; i++)
                    addAccordo(new Chord(tonica, ChordType.fromName(nome)));
            }
        }
    }
}
