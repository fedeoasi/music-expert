package com.github.fedeoasi.music;

import java.util.ArrayList;

public class Pattern {
    private ArrayList<Integer> gradi = new ArrayList<Integer>();
    private ArrayList<Integer> durate = new ArrayList<Integer>();

    public Pattern(ArrayList<Integer> gradi, ArrayList<Integer> durate) {
        this.gradi = gradi;
        this.durate = durate;
    }

    public ArrayList<Integer> getGradi() {
        return gradi;
    }

    public ArrayList<Integer> getDurate() {
        return durate;
    }
}
