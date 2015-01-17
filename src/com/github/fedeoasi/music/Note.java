package com.github.fedeoasi.music;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Note {
    private Ellipse2D e;
    private Line2D l;
    private String nome;
    private int altezza;
    private ArrayList<Line2D> opt = new ArrayList<Line2D>();
    private String alterazione = "";
    private Notes n = new Notes();

    public Note(String nome, int altezza, float x, float y) {
        e = new Ellipse2D.Float(x, y, 25, 20);
        this.nome = nome;
        this.altezza = altezza;

        if (!n.isNaturale(nome)) {
            char[] nomeNota = nome.toCharArray();
            if (nomeNota.length == 2) {
                char[] alt = new char[1];
                alt[0] = nomeNota[1];
                alterazione = new String(alt);
            } else if (nomeNota.length == 3) {
                char[] alt = new char[2];
                alt[0] = nomeNota[1];
                alt[1] = nomeNota[2];
                alterazione = new String(alt);
            }

        }

        //aggiunge la linea verticale della nota
        if (altezza < 71)
            l = new Line2D.Float((float) e.getMaxX(), (float) e.getCenterY(),
                    (float) e.getMaxX(), (float) e.getCenterY() - 60);
        else l = new Line2D.Float((float) e.getMinX(), (float) e.getCenterY(),
                (float) e.getMinX(), (float) e.getCenterY() + 60);

        if (y >= 160) {
            {
                if (y / 10 % 2 == 0)
                    opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) e.getCenterY(),
                            (float) e.getCenterX() + 17, (float) e.getCenterY()));
                else opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) e.getMinY(),
                        (float) e.getCenterX() + 17, (float) e.getMinY()));
            }
            if (y > 170)
                for (int i = 0; y - (10 * i) >= 170; i++) {
                    if ((y - (10 * i)) / 10 % 2 != 0)
                        opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) y - (10 * i),
                                (float) e.getCenterX() + 17, (float) y - (10 * i)));
                }

        } else if (y < 60) {
            {
                if (y / 10 % 2 == 0)
                    opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) e.getCenterY(),
                            (float) e.getCenterX() + 17, (float) e.getCenterY()));
                else opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) e.getMaxY(),
                        (float) e.getCenterX() + 17, (float) e.getMaxY()));
            }
            if (y < 50)
                for (int i = 1; y + (10 * i) <= 50; i++) {
                    if ((y + (10 * i)) / 10 % 2 != 0)
                        opt.add(new Line2D.Float((float) e.getCenterX() - 17, (float) y + (10 * i),
                                (float) e.getCenterX() + 17, (float) y + (10 * i)));
                }

        }


    }

    public Line2D getL() {
        return l;
    }

    public Ellipse2D getE() {
        return e;
    }

    public void setE(Ellipse2D e) {
        this.e = e;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public ArrayList<Line2D> getOpt() {
        return opt;
    }

    public String getAlterazione() {
        return alterazione;
    }
}

