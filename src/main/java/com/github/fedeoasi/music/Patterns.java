package com.github.fedeoasi.music;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Patterns {
    private File file;
    private FileReader fr;
    private BufferedReader br;
    private List<List<Integer>> gradi = new ArrayList<>();
    private List<List<Integer>> durate = new ArrayList<>();
    private Pattern pat1 = null;
    private Scanner s;

    private List<Integer> g = new ArrayList<>();
    private List<Integer> d = new ArrayList<>();

    public Patterns() {
        initialize();
    }

    private void initialize() {
        file = new File("Patterns/pattern.txt");
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            s = new Scanner(br);

            while (s.hasNextInt()) {
                g = new ArrayList<Integer>();
                int n = 0;
                while (s.hasNextInt() && n != 100) {
                    n = s.nextInt();
                    g.add(n);
                }
                g.remove(g.size() - 1);
                gradi.add(g);

                n = 0;
                d = new ArrayList<Integer>();
                while (s.hasNextInt() && n != 100) {
                    n = s.nextInt();
                    d.add(n);
                }
                d.remove(d.size() - 1);
                durate.add(d);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<List<Integer>> getGradi() {
        return gradi;
    }

    public List<List<Integer>> getDurate() {
        return durate;
    }

    public void setDurate(List<List<Integer>> durate) {
        this.durate = durate;
    }

    public static void main(String[] args) {
        new Patterns();
    }
}
