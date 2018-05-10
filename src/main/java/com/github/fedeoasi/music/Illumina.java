package com.github.fedeoasi.music;

import com.github.fedeoasi.gui.ChordProgressionPanel;

import java.util.concurrent.TimeUnit;

public class Illumina implements Runnable {
    private int n, bpm;
    private ChordProgressionPanel g = null;
    private boolean playing = true;

    public Illumina(int numAccordi, int bpm, ChordProgressionPanel g) {
        this.n = numAccordi;
        this.bpm = bpm;
        this.g = g;
    }

    public void run() {
        System.out.println(bpm);
        for (int i = 0; i < n && playing; i++) {
            g.illuminaAccordo(i);
            try {
                float f = (240) / (float) bpm * 1000;
                //Integer.
                TimeUnit.MILLISECONDS.sleep((int) f);
                System.out.println((int) f);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        g.spegniAccordi();
    }

    public void setPlaying(boolean p) {
        playing = p;
    }
}
