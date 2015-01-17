package com.github.fedeoasi.music;

import java.util.HashMap;
import java.util.Map;

public class Notes {
    public String[] noteNaturali = {"A", "B", "C", "D", "E", "F", "G"};
    public String[] noted = {"A", "A#", "B", "B#", "C#", "D", "D#", "E", "E#", "F#", "G", "G#"};
    public String[] noteb = {"A", "Bb", "Cb", "C", "Db", "D", "Eb", "Fb", "F", "Gb", "G", "Ab"};
    public String[] noted1 = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
    public String[] noteb1 = {"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"};
    public String[] noted2 = {"G##", "A#", "A##", "B#", "C#", "C##", "D#", "D##", "E#", "F#", "F##", "G#"};
    public String[] noteb2 = {"Bbb", "Bb", "Cb", "Dbb", "Db", "Ebb", "Eb", "Fb", "Gbb", "Gb", "Abb", "Ab"};

    private Map<String, Integer> indexByNote;

    public boolean isNatural(String nota) {
        for (int i = 0; i < noteNaturali.length; i++)
            if (noteNaturali[i].equals(nota))
                return true;
        return false;
    }

    public String nextNatural(String nota) {
        for (int i = 0; i < noteNaturali.length; i++) {
            if (noteNaturali[i].equals(nota)) {
                if (i + 1 < 7) {
                    return noteNaturali[i + 1];
                } else {
                    return noteNaturali[(i + 1) % 7];
                }
            }
        }
        char[] c = { nota.charAt(0) };
        return nextNatural(new String(c));
    }

    public int indexOf(String nota, String[] note) {
        for (int i = 0; i < note.length; i++)
            if (note[i].equals(nota))
                return i;
        return -1;
    }

    public int distance(String nota1, String nota2) {
        int n1 = getIndex(nota1);
        int n2 = getIndex(nota2);
        int diff = n2 - n1;
        if (diff >= 0) {
            return diff;
        }
        return 12 + diff;
    }

    public boolean exists(String nota) {
        return true;
    }

    public int getIndexInNaturalScale(String nota) {
        int indice = -1;
        indice = indexOf(nota, noteNaturali);
        if (indice == -1) {
            System.out.println("bu!Errore nota " + nota);
            return -1;
        }
        return indice;
    }

    public int getIndex(String nota) {
        ensureIndexByNote();
        Integer index = indexByNote.get(nota);
        if(index == null) {
            index = -1;
        }
        return index;
    }

    public String noteb(int i) {
        if (i < 12) return noteb[i];
        else return noteb[i % 12];
    }

    public String noted(int i) {
        if (i < 12) return noted[i];
        else return noted[i % 12];
    }

    public String noteb2(int i) {
        if (i < 12) return noteb2[i];
        else return noteb2[i % 12];
    }

    public String noted2(int i) {
        if (i < 12) return noted2[i];
        else return noted2[i % 12];
    }

    public String radice(String nota) {
        char[] c = {nota.charAt(0)};
        return new String(c);
    }

    public static void main(String[] args) {
        Notes n = new Notes();
        Scales s = new Scales();

        System.out.println("Giro delle Quinte");
        s.printScala(s.scalaMaggiore("C"));
        s.printScala(s.scalaMaggiore("G"));
        s.printScala(s.scalaMaggiore("D"));
        s.printScala(s.scalaMaggiore("A"));
        s.printScala(s.scalaMaggiore("E"));
        s.printScala(s.scalaMaggiore("B"));
        s.printScala(s.scalaMaggiore("F#"));
        s.printScala(s.scalaMaggiore("C#"));

        System.out.println("Giro delle quarte");
        s.printScala(s.scalaMaggiore("C"));
        s.printScala(s.scalaMaggiore("F"));
        s.printScala(s.scalaMaggiore("Bb"));
        s.printScala(s.scalaMaggiore("Eb"));
        s.printScala(s.scalaMaggiore("Ab"));
        s.printScala(s.scalaMaggiore("Db"));
        s.printScala(s.scalaMaggiore("Gb"));
        s.printScala(s.scalaMaggiore("Cb"));

        System.out.println("Scale minori di la");
        s.printScala(s.scalaMinNat("A"));
        s.printScala(s.scalaMinArm("A"));
        s.printScala(s.scalaMinMel("A"));

        System.out.println("Scale minori di sol");
        s.printScala(s.scalaMinNat("G"));
        s.printScala(s.scalaMinArm("G"));
        s.printScala(s.scalaMinMel("G"));

        System.out.println("Scale minori di re");
        s.printScala(s.scalaMinNat("D"));
        s.printScala(s.scalaMinArm("D"));
        s.printScala(s.scalaMinMel("D"));

        new Player(s.scalaMaggiore);
        new Player(s.scalaMinNat);
        new Player(s.scalaMinArm);
        new Player(s.scalaMinMel);

        new Chord("C", "");
        new Chord("C", "M7");
        new Chord("D", "M13");
        new Chord("G", "M9");
        new Chord("A", "");
        new Chord("A", "m");
        new Chord("A", "m6");
        new Chord("A", "m13");
    }

    public int ottava(String nome, int altezza) {
        System.out.println(nome + " " + alterazione(nome) + " " + altezza);
        if (isNatural(nome))
            return ((altezza - 45) / 12);
        if (alterazione(nome).equals("b"))
            return ((altezza - 44) / 12);
        if (alterazione(nome).equals("#"))
            return ((altezza - 46) / 12);
        if (alterazione(nome).equals("bb"))
            return ((altezza - 43) / 12);
        if (alterazione(nome).equals("##"))
            return ((altezza - 47) / 12);
        return 0;

    }

    public String alterazione(String nome) {
        String alterazione = "";
        if (!isNatural(nome)) {
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
        return alterazione;
    }

    private void ensureIndexByNote() {
        if(indexByNote == null) {
            indexByNote = new HashMap<String, Integer>();
            indexNotes(noteb);
            indexNotes(noted);
            indexNotes(noteb1);
            indexNotes(noted1);
            indexNotes(noteb2);
            indexNotes(noted2);
        }
    }

    private void indexNotes(String[] notes) {
        for (int i = 0; i < notes.length; i++) {
            indexByNote.put(notes[i], i);
        }
    }
}