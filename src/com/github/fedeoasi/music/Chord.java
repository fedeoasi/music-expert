package com.github.fedeoasi.music;

import java.util.ArrayList;

public class Chord {
    String sigla;
    String nome;
    ArrayList<Integer> distanze = new ArrayList<Integer>();
    ArrayList<String> note = new ArrayList<String>();
    ArrayList<Integer> altezze = new ArrayList<Integer>();
    ArrayList<int[]> scale = new ArrayList<int[]>();
    String tonica;
    //int[] altezze;

    public Chord(String tonica, String nome){
        this.tonica = tonica;
        this.nome = nome;
        boolean esiste = false;
        Scales s = new Scales();
        Notes n = new Notes();
        String[] scala = null;

        //Accordi maggiori e dominanti
        if (nome.equals("") || nome.equals("M7") || nome.equals("M9") || nome.equals("M13") || nome.equals("7")
            || nome.equals("9") || nome.equals("13") || nome.equals("7#5") || nome.equals("7b9")){

            //scale.add()
            esiste = true;
            scala = s.scalaMaggiore(tonica);
            //aggiunge la tonica
            note.add(scala[0]);
            //aggiunge la terza maggiore
            note.add(scala[2]);
            //aggiunge la quinta giusta
            if(nome!="7#5")
            note.add(scala[4]);
            else {
                Mode m = new Mode(tonica,nome,s.scalaMinMel,3);
                note.add(m.getNote()[4]);
                m = new Mode(tonica,nome,s.scalaMinMel,7);
                scale.add(m.getDistanze());
            }
            //aggiunge la settima minore
            if(nome=="7" || nome=="9" || nome=="13" || nome=="7#5"){
                Mode m = new Mode(tonica,nome,s.scalaMaggiore,5);
                note.add(m.getNote()[6]);
                scale.add(m.getDistanze());
                //note.add(n.noteb[n.getIndice(scala[6])-1]);
            }
            else scale.add(s.scalaMaggiore);
            //aggiunge la settima maggiore
            if(nome=="M7" || nome=="M9" || nome=="M13")
                note.add(scala[6]);
            //aggiunge la nona maggiore
            if(nome=="M9" || nome=="M13" || nome=="9" || nome=="13")
                note.add(scala[8%7]);
            //aggiunge la sesta o tredicesima maggiore
            if(nome=="M13" || nome=="13")
                note.add(scala[12%7]);
            sigla = tonica + nome;
            //printAccordo();
        }

        //Accordi minori e semidiminuiti 
        else if (nome=="m" || nome=="m7" || nome=="m9" || nome=="m13" || nome =="m6"
             || nome=="m7b5" || nome=="m9b5"){
                 esiste = true;
                 scala = s.scalaMinNat(tonica);
                //aggiunge la tonica
                 note.add(scala[0]);
                note.add(scala[2]);
                if(nome!="m7b5" && nome!="m9b5"){
                    Mode m = new Mode(tonica,nome,s.scalaMaggiore,2);
                    scale.add(m.getDistanze());
                    scale.add(s.scalaMinNat);
                    note.add(scala[4]);
                }

                else {
                    Mode m = new Mode(tonica, nome,s.scalaMaggiore,7);
                    note.add(m.getNote()[4]);
                    scale.add(m.getDistanze());
                    //note.add(n.noteb[(n.getIndice(scala[4])-1)%12]);
                }
                if(nome=="m6")
                    note.add(n.noted[(n.getIndice(scala[5])+1)%12]);
                if(nome=="m7" || nome=="m9" || nome=="m13"|| nome=="m7b5" || nome=="m9b5")
                    note.add(scala[6]);
                if(nome=="m9" || nome=="m13" || nome=="m9b5")
                    note.add(scala[8%7]);
                if(nome=="m13")
                    note.add(n.noted[(n.getIndice(scala[5])+1)%12]);
                sigla = tonica + nome;
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

    public ArrayList<Integer> getDistanze(){
        return distanze;
    }

    public ArrayList<String> getNote(){
        return note;
    }

    public void printAccordo(){
        System.out.print(sigla + ":  ");
        for(int i=0; i<note.size();i++)
            System.out.print(note.get(i));
        System.out.println();
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

    public String getNome() {
        return nome;
    }

    public String getTonica() {
        return tonica;
    }
}
