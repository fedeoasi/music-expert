package com.github.fedeoasi.music;

import java.util.ArrayList;

public class Chord {
    String sigla;
    String name;
    ArrayList<Integer> distanze = new ArrayList<Integer>();
    ArrayList<String> note = new ArrayList<String>();
    ArrayList<Integer> altezze = new ArrayList<Integer>();
    ArrayList<int[]> scale = new ArrayList<int[]>();
    String tonica;

    public Chord(String tonica, String name){
        this.tonica = tonica;
        this.name = name;
        boolean esiste = false;
        Scales s = new Scales();
        Notes n = new Notes();
        String[] scala = null;

        if (isMajor(name) || isDominant(name)){
            //scale.add()
            esiste = true;
            scala = s.scalaMaggiore(tonica);
            //aggiunge la tonica
            note.add(scala[0]);
            //aggiunge la terza maggiore
            note.add(scala[2]);
            //aggiunge la quinta giusta
            if(isNotAugmented(name))
            note.add(scala[4]);
            else {
                Mode m = new Mode(tonica,name,s.scalaMinMel,3);
                note.add(m.getNote()[4]);
                m = new Mode(tonica,name,s.scalaMinMel,7);
                scale.add(m.getDistanze());
            }
            //aggiunge la settima minore
            if(isDominant(name)){
                Mode m = new Mode(tonica,name,s.scalaMaggiore,5);
                note.add(m.getNote()[6]);
                scale.add(m.getDistanze());
                //note.add(n.noteb[n.getIndice(scala[6])-1]);
            }
            else scale.add(s.scalaMaggiore);
            //aggiunge la settima maggiore
            if(hasMajorSeventh(name))
                note.add(scala[6]);
            //aggiunge la nona maggiore
            if(hasMajorNinth(name))
                note.add(scala[8%7]);
            //aggiunge la sesta o tredicesima maggiore
            if(hasThirteenth(name))
                note.add(scala[12%7]);
            sigla = tonica + name;
            //printAccordo();
        }

        //Accordi minori e semidiminuiti 
        else if (isMinor(name) || isSemiDiminished(name)){
                 esiste = true;
                 scala = s.scalaMinNat(tonica);
                //aggiunge la tonica
                 note.add(scala[0]);
                note.add(scala[2]);
                if(name!="m7b5" && name!="m9b5"){
                    Mode m = new Mode(tonica,name,s.scalaMaggiore,2);
                    scale.add(m.getDistanze());
                    scale.add(s.scalaMinNat);
                    note.add(scala[4]);
                }

                else {
                    Mode m = new Mode(tonica, name,s.scalaMaggiore,7);
                    note.add(m.getNote()[4]);
                    scale.add(m.getDistanze());
                    //note.add(n.noteb[(n.getIndice(scala[4])-1)%12]);
                }
                if(name.equals("m6"))
                    note.add(n.noted[(n.getIndice(scala[5])+1)%12]);
                if(name.equals("m7") || name.equals("m9") || name.equals("m13") || name.equals("m7b5") || name.equals("m9b5"))
                    note.add(scala[6]);
                if(name.equals("m9") || name.equals("m13") || name.equals("m9b5"))
                    note.add(scala[8%7]);
                if(name.equals("m13"))
                    note.add(n.noted[(n.getIndice(scala[5])+1)%12]);
                sigla = tonica + name;
                //printAccordo();
        }

        if(!esiste) System.out.println("Errore accordo inesistente");
        else{
            distanze.add(0);
            for(int i=1; i<note.size(); i++)
                distanze.add(n.distanza(note.get(i-1),note.get(i)));

            altezze.add(57 + n.getIndice(tonica));
            for(int i=1; i<distanze.size();i++)
                altezze.add(altezze.get(i-1)+ distanze.get(i));
        }
        //System.out.println(distanze.size());
        //for(int i=0; i<distanze.size(); i++)
        //    System.out.println(distanze.get(i));

        }

    private boolean hasThirteenth(String name) {
        return name.equals("M13") || name.equals("13");
    }

    private boolean hasMajorNinth(String name) {
        return name.equals("M9") || name.equals("M13") || name.equals("9") || name.equals("13");
    }

    private boolean hasMajorSeventh(String name) {
        return name.equals("M7") || name.equals("M9") || name.equals("M13");
    }

    private boolean isSemiDiminished(String name) {
        return name.equals("m7b5") || name.equals("m9b5");
    }

    private boolean isMinor(String name) {
        return name.equals("m") || name.equals("m7") || name.equals("m9") || name.equals("m13") || name.equals("m6");
    }

    private boolean isNotAugmented(String name) {
        return !name.equals("7#5");
    }

    private boolean isDominant(String nome) {
        return nome.equals("7") || nome.equals("9") || nome.equals("13")
                || nome.equals("7#5") || nome.equals("7b9") || nome.equals("7#5");
    }

    private boolean isMajor(String nome) {
        return nome.equals("") || nome.equals("M7") || nome.equals("M9") || nome.equals("M13");
    }

    public ArrayList<Integer> getDistanze(){
        return distanze;
    }

    public ArrayList<String> getNote(){
        return note;
    }

    public ArrayList<Integer> getAltezze() {
        return altezze;
    }

    public ArrayList<int[]> getScale() {
        return scale;
    }

    public String getSigla() {
        return sigla;
    }

    public String getName() {
        return name;
    }

    public String getTonica() {
        return tonica;
    }
}
