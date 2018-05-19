package com.github.fedeoasi.music;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
    private List<Integer> gradi = new ArrayList<Integer>();
    private List<Integer> durate = new ArrayList<Integer>();

    public Pattern(List<Integer> gradi, List<Integer> durate) {
        this.gradi = gradi;
        this.durate = durate;
    }

    public List<Integer> getGradi() {
        return gradi;
    }

    public List<Integer> getDurate() {
        return durate;
    }
}
